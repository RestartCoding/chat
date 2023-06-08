package com.genepoint.chat.core;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
public class SessionManager {

    private static final Map<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>();

    private SessionManager() {
    }

    public static void put(String key, WebSocketSession session) {
        if (!activeSessions.containsKey(session.getId())) {
            activeSessions.put(key, session);
        }
    }

    public static WebSocketSession remove(String key) {
        return activeSessions.remove(key);
    }

    public static Set<Map.Entry<String, WebSocketSession>> entries() {
        return new HashMap<>(activeSessions).entrySet();
    }

    public static WebSocketSession get(String k) {
        return activeSessions.get(k);
    }

    public static void foreach(Consumer<WebSocketSession> consumer) {
        for (Map.Entry<String, WebSocketSession> entry : activeSessions.entrySet()) {
            consumer.accept(entry.getValue());
        }
    }
}
