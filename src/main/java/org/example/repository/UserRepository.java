package org.example.repository;

import org.example.models.User;

public interface UserRepository {
    User saveUser(User user);
    User getUserById(String id);
}
