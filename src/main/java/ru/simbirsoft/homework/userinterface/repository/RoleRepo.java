package ru.simbirsoft.homework.userinterface.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.homework.userinterface.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
