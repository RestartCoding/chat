package com.genepoint.chat.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genepoint.chat.domain.Message;
import com.genepoint.chat.listener.UserLogOutListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
@Component
public class ChatHandler extends TextWebSocketHandler implements UserLogOutListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);

    private static final int HOLD_RECENT_MESSAGE_NUMBER = 100;

    private final List<String> recentMessages = new ArrayList<>();

    private final ObjectMapper om = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("connection established [{}]", session.getRemoteAddress());
        String httpSessionId = (String) session.getAttributes().get("httpSessionId");
        if (recentMessages.size() > 0) {
            for (String message : recentMessages) {
                session.sendMessage(new TextMessage(message));
            }
        }
        SessionManager.put(httpSessionId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("connection closed [{}]", session.getRemoteAddress());
        String httpSessionId = (String) session.getAttributes().get("httpSessionId");
        SessionManager.remove(httpSessionId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = UserContext.getUsername(session);
        if (!StringUtils.hasText(username)) {
            session.sendMessage(new TextMessage("403"));
            return;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("received message: {}", message.getPayload());
        }
        Message msg = new Message();
        msg.setSender(username);
        msg.setPayload(message.getPayload());
        msg.setTime(System.currentTimeMillis());


        String messageStr = om.writeValueAsString(msg);

        holdMessage(messageStr);

        SessionManager.foreach(s -> {
            try {
                s.sendMessage(new TextMessage(messageStr));
            } catch (IOException e) {
                LOGGER.error("Failed to send message [{}] to [{}]", messageStr, session.getRemoteAddress());
            }
        });
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void onUserLogout(HttpSession session) {
        WebSocketSession webSocketSession = SessionManager.get(session.getId());
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                webSocketSession.close();
            } catch (IOException e) {
                LOGGER.error("failed to close connection: [{}]", webSocketSession.getRemoteAddress());
            }
        }
    }

    private synchronized void holdMessage(String message) {
        if (recentMessages.size() == HOLD_RECENT_MESSAGE_NUMBER) {
            recentMessages.remove(0);
        }
        recentMessages.add(message);
    }
}