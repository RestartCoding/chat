package com.genepoint.chat.core;

import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
public class UserContext {

    public static final String USERNAME_ATTR_KEY = "USERNAME";

    public static String getUsername(WebSocketSession session) {
        Object o = session.getAttributes().get(USERNAME_ATTR_KEY);
        return o == null ? "" : o.toString();
    }

    public static String getUsername(HttpSession session) {
        return session.getAttribute(USERNAME_ATTR_KEY).toString();
    }

    public static void setUsername(HttpSession session, String username) {
        session.setAttribute(USERNAME_ATTR_KEY, username);
    }

    public static void setUsername(WebSocketSession session, String username) {
        session.getAttributes().put(USERNAME_ATTR_KEY, username);
    }
}
