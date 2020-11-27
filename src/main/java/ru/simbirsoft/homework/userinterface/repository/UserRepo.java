package ru.simbirsoft.homework.userinterface.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.homework.userinterface.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String name);
}

