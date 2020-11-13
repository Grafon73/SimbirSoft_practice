package ru.simbirsoft.homework.person.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.simbirsoft.homework.person.dto.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService{

    static List<Person> persons = new ArrayList<>(Arrays.asList(
            new Person("Евгина","Жданова", "Николаевна", new Date(1212121212121L)),
            new Person("Виталий","Булгаков", "Закирович", new Date(1222222222222L)),
            new Person("Зорина","Воронин", "Богданович",new Date(1333333333333L))
    ));


    @Override
    public List<Person> getAllPersons() {
        return persons;
    }

    @Override
    public List<Person> getPerson(String name) {
        return persons.stream()
                .filter(p -> p.getFirstName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> addPerson(Person person) {
        persons.add(person);
        return persons;
    }

    @Override
    public ResponseEntity<Person> removePerson(String firstName, String lastName, String middleName) {
        if (persons.stream().anyMatch(p ->
                p.getFirstName().equals(firstName) &&
                        p.getLastName().equals(lastName) &&
                        p.getMiddleName().equals(middleName))) {
            persons.removeIf(p ->
                    p.getFirstName().equals(firstName) &&
                            p.getLastName().equals(lastName) &&
                            p.getMiddleName().equals(middleName));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
