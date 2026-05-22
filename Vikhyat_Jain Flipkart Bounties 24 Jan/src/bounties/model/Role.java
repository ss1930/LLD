package bounties.model;

import bounties.exception.ValidationException;

public enum Role {
    ADMIN,
    AGENT;

    public static Role fromString(String raw) {
        if (raw == null) throw new ValidationException("Role cannot be null");
        String v = raw.trim().toUpperCase();
        if (v.equals("ADMIN")) return ADMIN;
        if (v.equals("AGENT")) return AGENT;
        throw new ValidationException("Unknown role: " + raw);
    }
}

