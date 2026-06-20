package com.ebouprime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {
    private static final Map<Integer, User> STORE = new ConcurrentHashMap<>();
    private static final AtomicInteger ID_GEN = new AtomicInteger(0);

    static {
        // Beispielnutzer
        create(new User(null, "Alice", "alice@example.com"));
        create(new User(null, "Bob", "bob@example.com"));
    }

    public static List<User> getAll() {
        return new ArrayList<>(STORE.values());
    }

    public static User getById(int id) {
        return STORE.get(id);
    }

    public static User create(User user) {
        int id = ID_GEN.incrementAndGet();
        user.setId(id);
        STORE.put(id, user);
        return user;
    }

    public static boolean delete(int id) {
        return STORE.remove(id) != null;
    }
}

