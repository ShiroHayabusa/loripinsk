package com.loripin.auto.model.util;

import com.loripin.auto.model.User;

public abstract class SpotHelper {
    public static String getUserName(User user) {
        return user != null ? user.getUsername() : "<none>";
    }
}
