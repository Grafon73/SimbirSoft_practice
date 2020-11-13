package ru.simbirsoft.homework.person.service;

import org.springframework.http.ResponseEntity;
import ru.simbirsoft.homework.person.dto.Person;

import java.util.List;


public interface PersonService {
    List<Person> getAllPersons();
    List<Person> getPerson(String name);
    List<Person> addPerson(Person person);
    ResponseEntity<Person> removePerson(String firstName,
                                        String lastName,
                                        String middleName);
}
