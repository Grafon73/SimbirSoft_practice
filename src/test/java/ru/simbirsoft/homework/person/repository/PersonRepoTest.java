package ru.simbirsoft.homework.person.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.person.model.PersonEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class PersonRepoTest {

    @Autowired
    PersonRepo personRepo;

    @Test
    void findAllPersonEntityByFirstNameAndLastNameAndMiddleName() {
        String firstName = "Олег";
        String lastName = "Резин";
        String middleName = "Степанович";
        List<PersonEntity> personEntities = personRepo.findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
                firstName,
                lastName,
                middleName
        );
        assertThat(personEntities).isNotEmpty();
        assertThat(personEntities.stream().allMatch(personEntity ->
                personEntity.getFirstName().equals(firstName) &&
                personEntity.getLastName().equals(lastName) &&
                personEntity.getMiddleName().equals(middleName))).isTrue();
    }
}