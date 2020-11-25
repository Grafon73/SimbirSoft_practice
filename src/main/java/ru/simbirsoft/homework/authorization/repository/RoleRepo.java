package ru.simbirsoft.homework.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.homework.authorization.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
