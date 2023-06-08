package com.genepoint.chat.listener;

import javax.servlet.http.HttpSession;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
public interface UserLogOutListener {

    void onUserLogout(HttpSession session);
}
