package org.example.services.impl;

import org.example.exceptions.SplitWiseServiceException;
import org.example.models.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.HashMapUserRepositoryImpl;
import org.example.services.UserService;

import java.util.Objects;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new HashMapUserRepositoryImpl();
    @Override
    public User saveUser(User user) {
        return userRepository.saveUser(user);
    }
    @Override
    public User getUserById(String id) throws SplitWiseServiceException {
        User user = userRepository.getUserById(id);
        if(Objects.isNull(user)) {
            System.out.println("user not found for userId: "+id);
            throw new SplitWiseServiceException("User not found", 404);
        }
        return user;
    }
}
