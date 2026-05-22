package bounties.service;

import bounties.exception.AuthorizationException;
import bounties.exception.NotFoundException;
import bounties.exception.ValidationException;
import bounties.model.BugComment;
import bounties.model.BugReport;
import bounties.model.BugStatus;
import bounties.model.Role;
import bounties.model.Severity;
import bounties.model.StatusChange;
import bounties.model.TimeLog;
import bounties.model.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BugReportService {
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private final AtomicLong idGen = new AtomicLong(1);
    private final Map<String, BugReport> reportsByTitle = new HashMap<>();

    private final UserService userService;
    private final CommunicationService communicationService;

    public BugReportService(UserService userService, CommunicationService communicationService) {
        this.userService = userService;
        this.communicationService = communicationService;
    }

    public BugReport reportBug(String title, String description, Severity severity, String reporterEmail) {
        rw.writeLock().lock();
        try {
            String key = normalizeTitle(title);
            if (reportsByTitle.containsKey(key)) throw new ValidationException("BugReport already exists with title: " + title);
            BugReport br = new BugReport(idGen.getAndIncrement(), title, description, severity, reporterEmail, Instant.now());
            reportsByTitle.put(key, br);
            return br;
        } finally {
            rw.writeLock().unlock();
        }
    }

    public void assign(String bugTitle, String assignedUserName) {
        if (!userService.exists(assignedUserName)) throw new NotFoundException("Cannot assign: user not found: " + assignedUserName);
        rw.writeLock().lock();
        try {
            BugReport br = getInternalByTitle(bugTitle);
            br.setAssignedUser(assignedUserName);
        } finally {
            rw.writeLock().unlock();
        }
    }

    public void updateStatus(User actor, String bugTitle, BugStatus nextStatus) {
        rw.writeLock().lock();
        try {
            BugReport br = getInternalByTitle(bugTitle);
            ensureAssignedToActor(actor, br);

            BugStatus current = br.getStatus();
            if (!current.canTransitionTo(nextStatus)) {
                throw new ValidationException("Invalid status transition: " + current + " -> " + nextStatus);
            }

            br.setStatus(nextStatus);
            br.addStatusChange(new StatusChange(current, nextStatus, actor.getName(), Instant.now()));

            if (nextStatus == BugStatus.REJECTED) {
                communicationService.notifyReporterRejected(br.getReporterEmail(), br.getTitle());
            }
            if (nextStatus == BugStatus.BOUNTY_PAID) {
                communicationService.notifyReporterBountyPaid(br.getReporterEmail(), br.getTitle(), br.getBountyAmount());
            }
            if (nextStatus == BugStatus.CLOSED) {
                br.setClosedTimeStamp(Instant.now());
            }
        } finally {
            rw.writeLock().unlock();
        }
    }

    public void updateBugReport(User actor,
                                String bugTitle,
                                String description,
                                Severity severity,
                                Long bountyAmount,
                                String reporterEmail) {
        rw.writeLock().lock();
        try {
            BugReport br = getInternalByTitle(bugTitle);
            ensureActorCanEdit(actor, br);

            if (description != null) br.setDescription(description);
            if (severity != null) br.setSeverity(severity);
            if (bountyAmount != null) br.setBountyAmount(bountyAmount);
            if (reporterEmail != null) br.setReporterEmail(reporterEmail);
        } finally {
            rw.writeLock().unlock();
        }
    }

    public void addComment(User actor, String bugTitle, String commentText) {
        if (commentText == null || commentText.trim().isEmpty()) throw new ValidationException("Comment cannot be empty");
        rw.writeLock().lock();
        try {
            BugReport br = getInternalByTitle(bugTitle);
            br.addComment(new BugComment(actor.getName(), commentText.trim(), Instant.now()));
        } finally {
            rw.writeLock().unlock();
        }
    }

    public void logTime(User actor, String bugTitle, int minutes, String note) {
        if (minutes <= 0) throw new ValidationException("minutes must be > 0");
        rw.writeLock().lock();
        try {
            BugReport br = getInternalByTitle(bugTitle);
            ensureAssignedToActor(actor, br);
            br.addTimeLog(new TimeLog(actor.getName(), minutes, note, Instant.now()));
        } finally {
            rw.writeLock().unlock();
        }
    }

    public void deleteBugReport(User actor, String bugTitle) {
        if (actor.getRole() != Role.ADMIN) throw new AuthorizationException("Only ADMIN can delete bug reports");
        rw.writeLock().lock();
        try {
            String key = normalizeTitle(bugTitle);
            if (!reportsByTitle.containsKey(key)) throw new NotFoundException("BugReport not found: " + bugTitle);
            reportsByTitle.remove(key);
        } finally {
            rw.writeLock().unlock();
        }
    }

    public List<BugReport> listAll() {
        rw.readLock().lock();
        try {
            return sortedCopy(new ArrayList<>(reportsByTitle.values()));
        } finally {
            rw.readLock().unlock();
        }
    }

    public List<BugReport> listAssignedTo(String userName) {
        rw.readLock().lock();
        try {
            List<BugReport> out = new ArrayList<>();
            for (BugReport br : reportsByTitle.values()) {
                if (userName.equals(br.getAssignedUser())) out.add(br);
            }
            return sortedCopy(out);
        } finally {
            rw.readLock().unlock();
        }
    }

    public List<BugReport> listAssignedToAndCompleted(String userName) {
        rw.readLock().lock();
        try {
            List<BugReport> out = new ArrayList<>();
            for (BugReport br : reportsByTitle.values()) {
                if (userName.equals(br.getAssignedUser()) && br.getStatus() == BugStatus.CLOSED) out.add(br);
            }
            return sortedCopy(out);
        } finally {
            rw.readLock().unlock();
        }
    }

    public List<BugReport> listAssignedToAndIncomplete(String userName) {
        rw.readLock().lock();
        try {
            List<BugReport> out = new ArrayList<>();
            for (BugReport br : reportsByTitle.values()) {
                if (userName.equals(br.getAssignedUser()) && br.getStatus() != BugStatus.CLOSED) out.add(br);
            }
            return sortedCopy(out);
        } finally {
            rw.readLock().unlock();
        }
    }

    public BugReport viewDetails(String bugTitle) {
        rw.readLock().lock();
        try {
            return getInternalByTitle(bugTitle);
        } finally {
            rw.readLock().unlock();
        }
    }

    private BugReport getInternalByTitle(String title) {
        String key = normalizeTitle(title);
        BugReport br = reportsByTitle.get(key);
        if (br == null) throw new NotFoundException("BugReport not found: " + title);
        return br;
    }

    private static String normalizeTitle(String title) {
        if (title == null || title.trim().isEmpty()) throw new ValidationException("Bug title is required");
        return title.trim().toLowerCase();
    }

    private static List<BugReport> sortedCopy(List<BugReport> list) {
        List<BugReport> copy = new ArrayList<>(list);
        copy.sort(Comparator.comparing(BugReport::getCreatedTimeStamp).thenComparing(BugReport::getId));
        return Collections.unmodifiableList(copy);
    }

    private static void ensureAssignedToActor(User actor, BugReport br) {
        if (br.getAssignedUser() == null) throw new ValidationException("BugReport is not assigned to any user yet");
        if (!actor.getName().equals(br.getAssignedUser())) {
            throw new AuthorizationException("BugReport is assigned to '" + br.getAssignedUser() + "', not '" + actor.getName() + "'");
        }
    }

    private static void ensureActorCanEdit(User actor, BugReport br) {
        if (actor.getRole() == Role.ADMIN) return;
        ensureAssignedToActor(actor, br);
    }
}

