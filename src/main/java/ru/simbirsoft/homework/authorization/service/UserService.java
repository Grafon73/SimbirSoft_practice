package ru.simbirsoft.homework.authorization.service;

import ru.simbirsoft.homework.authorization.model.User;

public interface UserService {
    boolean saveUser(User user);
    User loadUserByUsername(String name);
}
