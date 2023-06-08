package com.genepoint.chat.config;

import com.genepoint.chat.core.AuthInterceptor;
import com.genepoint.chat.core.ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private ChatHandler chatHandler;

    @Resource
    private AuthInterceptor authInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/chat").addInterceptors(authInterceptor);
    }
}
