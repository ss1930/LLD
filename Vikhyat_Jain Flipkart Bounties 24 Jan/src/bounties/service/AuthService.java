package bounties.service;

import bounties.exception.AuthorizationException;
import bounties.exception.ValidationException;
import bounties.model.User;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class AuthService {
    private final ReentrantLock lock = new ReentrantLock();
    private User loggedInUser;

    public void login(User user) {
        lock.lock();
        try {
            if (loggedInUser != null) {
                throw new ValidationException("Another user is already logged in: " + loggedInUser.getName());
            }
            loggedInUser = user;
        } finally {
            lock.unlock();
        }
    }

    public void logout() {
        lock.lock();
        try {
            if (loggedInUser == null) throw new ValidationException("No user is logged in");
            loggedInUser = null;
        } finally {
            lock.unlock();
        }
    }

    public User requireLoggedInUser() {
        lock.lock();
        try {
            if (loggedInUser == null) throw new AuthorizationException("Please login first");
            return loggedInUser;
        } finally {
            lock.unlock();
        }
    }

    public Optional<User> getLoggedInUser() {
        lock.lock();
        try {
            return Optional.ofNullable(loggedInUser);
        } finally {
            lock.unlock();
        }
    }
}

