package ru.simbirsoft.homework.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.homework.author.model.AuthorEntity;

public interface AuthorRepo extends JpaRepository<AuthorEntity, Integer> {
    AuthorEntity findAuthorEntityByFirstNameAndLastNameAndMiddleName(String firstName,String lastName, String middleName);
}
