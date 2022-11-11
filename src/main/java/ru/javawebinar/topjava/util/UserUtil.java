package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtil {
    public static final List<User> users = Arrays.asList(
            new User(0, "Admin", "admin@gmail.com", "password", Role.ADMIN, Role.USER),
            new User(1, "User1", "user1@gmail.com", "password", Role.USER),
            new User(2, "User2", "user2@gmail.com", "password", Role.USER)
    );
}
