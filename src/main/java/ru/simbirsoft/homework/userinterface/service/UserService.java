package ru.simbirsoft.homework.userinterface.service;

import ru.simbirsoft.homework.person.view.PersonView;
import ru.simbirsoft.homework.userinterface.model.User;

public interface UserService {
    boolean saveUser(User user);
    PersonView loadByUsername(String username);
}
