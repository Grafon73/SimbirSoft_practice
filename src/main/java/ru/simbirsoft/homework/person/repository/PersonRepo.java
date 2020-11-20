package ru.simbirsoft.homework.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.homework.person.model.PersonEntity;

import java.util.List;

public interface PersonRepo extends JpaRepository<PersonEntity,Integer> {
    List<PersonEntity> findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
            String firstName,
            String lastName,
            String middleName);

}
