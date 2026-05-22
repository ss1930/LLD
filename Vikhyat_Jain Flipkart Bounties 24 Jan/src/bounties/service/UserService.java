package bounties.service;

import bounties.exception.NotFoundException;
import bounties.exception.ValidationException;
import bounties.model.Role;
import bounties.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserService {
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private final Map<String, User> usersByName = new HashMap<>();

    public void preloadUsers(Collection<User> users) {
        rw.writeLock().lock();
        try {
            usersByName.clear();
            for (User u : users) {
                usersByName.put(u.getName(), u);
            }
        } finally {
            rw.writeLock().unlock();
        }
    }

    public void addUser(User user) {
        rw.writeLock().lock();
        try {
            if (usersByName.containsKey(user.getName())) {
                throw new ValidationException("User already exists: " + user.getName());
            }
            usersByName.put(user.getName(), user);
        } finally {
            rw.writeLock().unlock();
        }
    }

    public void updateUser(String userName, String email, Role role) {
        rw.writeLock().lock();
        try {
            User u = usersByName.get(userName);
            if (u == null) throw new NotFoundException("User not found: " + userName);
            if (email != null) u.setEmail(email);
            if (role != null) u.setRole(role);
        } finally {
            rw.writeLock().unlock();
        }
    }

    public User getByName(String userName) {
        rw.readLock().lock();
        try {
            User u = usersByName.get(userName);
            if (u == null) throw new NotFoundException("User not found: " + userName);
            return u;
        } finally {
            rw.readLock().unlock();
        }
    }

    public boolean exists(String userName) {
        rw.readLock().lock();
        try {
            return usersByName.containsKey(userName);
        } finally {
            rw.readLock().unlock();
        }
    }

    public Collection<User> listAll() {
        rw.readLock().lock();
        try {
            return Collections.unmodifiableList(new ArrayList<>(usersByName.values()));
        } finally {
            rw.readLock().unlock();
        }
    }
}

