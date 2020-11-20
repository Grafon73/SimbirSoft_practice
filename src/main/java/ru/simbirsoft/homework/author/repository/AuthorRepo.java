package ru.simbirsoft.homework.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.homework.author.model.AuthorEntity;

import java.util.Optional;

public interface AuthorRepo extends JpaRepository<AuthorEntity, Integer> {
    Optional<AuthorEntity> findAuthorEntityByFirstNameAndLastNameAndMiddleName(String firstName, String lastName, String middleName);
}
