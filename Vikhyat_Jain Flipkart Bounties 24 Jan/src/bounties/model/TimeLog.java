package bounties.model;

import java.time.Instant;

public class TimeLog {
    private final String userName;
    private final int minutes;
    private final String note;
    private final Instant loggedAt;

    public TimeLog(String userName, int minutes, String note, Instant loggedAt) {
        this.userName = userName;
        this.minutes = minutes;
        this.note = note;
        this.loggedAt = loggedAt;
    }

    public String getUserName() {
        return userName;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getNote() {
        return note;
    }

    public Instant getLoggedAt() {
        return loggedAt;
    }

    @Override
    public String toString() {
        String extra = (note == null || note.trim().isEmpty()) ? "" : (" (" + note + ")");
        return "[" + loggedAt + "] " + userName + " logged " + minutes + "m" + extra;
    }
}

