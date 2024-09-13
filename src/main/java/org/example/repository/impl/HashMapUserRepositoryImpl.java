package org.example.repository.impl;

import org.example.models.User;
import org.example.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class HashMapUserRepositoryImpl implements UserRepository {
    private final Map<String, User> userMap = new HashMap<>();
    @Override
    public User saveUser(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(String id) {
        if(userMap.containsKey(id))
            return userMap.get(id);
        return null;
    }
}
