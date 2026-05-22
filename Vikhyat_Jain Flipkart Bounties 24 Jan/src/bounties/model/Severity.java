package bounties.model;

import bounties.exception.ValidationException;

public enum Severity {
    P0, P1, P2, P3;

    public static Severity fromString(String raw) {
        if (raw == null) throw new ValidationException("Severity cannot be null");
        String v = raw.trim().toUpperCase();
        if (v.equals("P0")) return P0;
        if (v.equals("P1")) return P1;
        if (v.equals("P2")) return P2;
        if (v.equals("P3")) return P3;
        throw new ValidationException("Unknown severity: " + raw);
    }
}

