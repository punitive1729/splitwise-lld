package org.example.services;

import org.example.exceptions.SplitWiseServiceException;
import org.example.models.User;

public interface UserService {
    User saveUser(User user);
    User getUserById(String id) throws SplitWiseServiceException;
}
