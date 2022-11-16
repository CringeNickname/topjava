package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.web.SecurityUtil;

public class LoggedUser {
    private static int id = SecurityUtil.authUserId();

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        LoggedUser.id = id;
    }
}
