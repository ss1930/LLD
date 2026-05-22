package bounties.model;

import java.time.Instant;

public class BugComment {
    private final String authorUserName;
    private final String text;
    private final Instant createdAt;

    public BugComment(String authorUserName, String text, Instant createdAt) {
        this.authorUserName = authorUserName;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public String getText() {
        return text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "[" + createdAt + "] " + authorUserName + ": " + text;
    }
}

