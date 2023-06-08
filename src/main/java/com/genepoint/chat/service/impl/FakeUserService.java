package com.genepoint.chat.service.impl;

import com.genepoint.chat.domain.User;
import com.genepoint.chat.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
@Service
public class FakeUserService implements UserService {
    @Override
    public User login(String username, String password) {
        boolean b = Objects.equals(username, password);
        if (b) {
            return new User(username, password);
        }
        throw new RuntimeException("Incorrect username or password");
    }
}
