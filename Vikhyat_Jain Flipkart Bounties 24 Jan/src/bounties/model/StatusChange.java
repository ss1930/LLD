package bounties.model;

import java.time.Instant;

public class StatusChange {
    private final BugStatus from;
    private final BugStatus to;
    private final String changedByUserName;
    private final Instant changedAt;

    public StatusChange(BugStatus from, BugStatus to, String changedByUserName, Instant changedAt) {
        this.from = from;
        this.to = to;
        this.changedByUserName = changedByUserName;
        this.changedAt = changedAt;
    }

    public BugStatus getFrom() {
        return from;
    }

    public BugStatus getTo() {
        return to;
    }

    public String getChangedByUserName() {
        return changedByUserName;
    }

    public Instant getChangedAt() {
        return changedAt;
    }

    @Override
    public String toString() {
        return "[" + changedAt + "] " + changedByUserName + ": " + from + " -> " + to;
    }
}

