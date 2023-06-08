package com.genepoint.chat.service;

import com.genepoint.chat.domain.User;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
public interface UserService {
    User login(String username, String password);
}
