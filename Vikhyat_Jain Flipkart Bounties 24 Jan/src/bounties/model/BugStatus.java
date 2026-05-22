package bounties.model;

import bounties.exception.ValidationException;

import java.util.EnumSet;
import java.util.Set;

public enum BugStatus {
    OPEN,
    REPORT_REVIEW,
    REJECTED,
    ACKNOWLEDGED,
    BOUNTY_REVIEW,
    BOUNTY_PAID,
    CLOSED;

    public static BugStatus fromString(String raw) {
        if (raw == null) throw new ValidationException("Status cannot be null");
        String v = raw.trim()
                .toUpperCase()
                .replace(" ", "_")
                .replace("-", "_");
        try {
            return BugStatus.valueOf(v);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown status: " + raw);
        }
    }

    public boolean canTransitionTo(BugStatus next) {
        if (next == null) return false;
        return allowedNextStates(this).contains(next);
    }

    private static Set<BugStatus> allowedNextStates(BugStatus current) {
        switch (current) {
            case OPEN:
                return EnumSet.of(REPORT_REVIEW);
            case REPORT_REVIEW:
                return EnumSet.of(REJECTED, ACKNOWLEDGED);
            case REJECTED:
                return EnumSet.of(CLOSED);
            case ACKNOWLEDGED:
                return EnumSet.of(BOUNTY_REVIEW);
            case BOUNTY_REVIEW:
                return EnumSet.of(BOUNTY_PAID);
            case BOUNTY_PAID:
                return EnumSet.of(CLOSED);
            case CLOSED:
            default:
                return EnumSet.noneOf(BugStatus.class);
        }
    }
}

