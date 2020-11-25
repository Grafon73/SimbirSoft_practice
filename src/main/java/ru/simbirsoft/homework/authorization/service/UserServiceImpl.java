package ru.simbirsoft.homework.authorization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.simbirsoft.homework.authorization.model.Role;
import ru.simbirsoft.homework.authorization.model.User;
import ru.simbirsoft.homework.authorization.repository.UserRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean saveUser(User user) {
        Optional<User> userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB.isPresent()) {
            return false;
        }
        user.setRole(new Role(user.getUsername(), "ROLE_USER"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepo.save(user);
        return true;
    }
}
