package bounties.model;

import bounties.exception.ValidationException;

public class User {
    private final String name;
    private String email;
    private Role role;

    public User(String name, String email, Role role) {
        if (name == null || name.trim().isEmpty()) throw new ValidationException("User name is required");
        if (email == null || email.trim().isEmpty()) throw new ValidationException("User email is required");
        if (role == null) throw new ValidationException("User role is required");
        this.name = name.trim();
        this.email = email.trim();
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) throw new ValidationException("User email is required");
        this.email = email.trim();
    }

    public void setRole(Role role) {
        if (role == null) throw new ValidationException("User role is required");
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "', role=" + role + "}";
    }
}

