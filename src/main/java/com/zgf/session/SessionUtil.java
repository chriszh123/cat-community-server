package com.zgf.session;

import com.zgf.model.User;

/**
 * Created by zgf
 * Date 2018/12/8 15:37
 * Description
 */
public class SessionUtil {
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}
