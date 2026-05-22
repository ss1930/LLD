package bounties.model;

import bounties.exception.ValidationException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BugReport {
    private final long id;
    private final Instant createdTimeStamp;

    private final String title;
    private String description;
    private BugStatus status;
    private Severity severity;
    private Long bountyAmount;
    private String reporterEmail;
    private String assignedUser;
    private Instant closedTimeStamp;

    private final List<BugComment> comments = new ArrayList<>();
    private final List<StatusChange> statusHistory = new ArrayList<>();
    private final List<TimeLog> timeLogs = new ArrayList<>();

    public BugReport(long id,
                     String title,
                     String description,
                     Severity severity,
                     String reporterEmail,
                     Instant createdTimeStamp) {
        this.id = id;
        this.title = requireNonBlank(title, "title");
        this.description = requireNonBlank(description, "description");
        this.severity = requireNonNull(severity, "severity");
        this.reporterEmail = requireNonBlank(reporterEmail, "reporterEmail");
        this.createdTimeStamp = requireNonNull(createdTimeStamp, "createdTimeStamp");
        this.status = BugStatus.OPEN;
    }

    public long getId() {
        return id;
    }

    public Instant getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BugStatus getStatus() {
        return status;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Long getBountyAmount() {
        return bountyAmount;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public Instant getClosedTimeStamp() {
        return closedTimeStamp;
    }

    public List<BugComment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public List<StatusChange> getStatusHistory() {
        return Collections.unmodifiableList(statusHistory);
    }

    public List<TimeLog> getTimeLogs() {
        return Collections.unmodifiableList(timeLogs);
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = requireNonBlank(assignedUser, "assignedUser");
    }

    public void setDescription(String description) {
        this.description = requireNonBlank(description, "description");
    }

    public void setSeverity(Severity severity) {
        this.severity = requireNonNull(severity, "severity");
    }

    public void setBountyAmount(Long bountyAmount) {
        if (bountyAmount != null && bountyAmount < 0) throw new ValidationException("bountyAmount cannot be negative");
        this.bountyAmount = bountyAmount;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = requireNonBlank(reporterEmail, "reporterEmail");
    }

    public void addComment(BugComment comment) {
        comments.add(requireNonNull(comment, "comment"));
    }

    public void addStatusChange(StatusChange change) {
        statusHistory.add(requireNonNull(change, "statusChange"));
    }

    public void addTimeLog(TimeLog log) {
        timeLogs.add(requireNonNull(log, "timeLog"));
    }

    public void setStatus(BugStatus status) {
        this.status = requireNonNull(status, "status");
    }

    public void setClosedTimeStamp(Instant closedTimeStamp) {
        this.closedTimeStamp = closedTimeStamp;
    }

    @Override
    public String toString() {
        return "BugReport{id=" + id
                + ", title='" + title + '\''
                + ", status=" + status
                + ", severity=" + severity
                + ", bountyAmount=" + bountyAmount
                + ", reporterEmail='" + reporterEmail + '\''
                + ", assignedUser='" + assignedUser + '\''
                + ", createdTimeStamp=" + createdTimeStamp
                + ", closedTimeStamp=" + closedTimeStamp
                + '}';
    }

    private static String requireNonBlank(String v, String field) {
        if (v == null || v.trim().isEmpty()) throw new ValidationException(field + " is required");
        return v.trim();
    }

    private static <T> T requireNonNull(T v, String field) {
        if (v == null) throw new ValidationException(field + " is required");
        return v;
    }
}

