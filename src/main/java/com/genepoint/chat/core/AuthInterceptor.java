package com.genepoint.chat.core;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
@Component
public class AuthInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        super.beforeHandshake(request, response, wsHandler, attributes);
        return attributes.containsKey(UserContext.USERNAME_ATTR_KEY);
    }
}