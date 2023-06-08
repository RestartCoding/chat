package com.genepoint.chat.controller;

import com.genepoint.chat.core.UserContext;
import com.genepoint.chat.domain.User;
import com.genepoint.chat.listener.UserLogOutListener;
import com.genepoint.chat.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final List<UserLogOutListener> userLogOutListeners;

    public UserController(UserService userService, List<UserLogOutListener> userLogOutListeners) {
        this.userService = userService;
        this.userLogOutListeners = userLogOutListeners;
    }

    @PostMapping("/login")
    public void login(@RequestBody User user, HttpSession session) {
        userService.login(user.getUsername(), user.getPassword());
        UserContext.setUsername(session, user.getUsername());
        session.setAttribute("httpSessionId", session.getId());
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
        if (userLogOutListeners != null) {
            userLogOutListeners.forEach(e -> e.onUserLogout(session));
        }
    }
}
