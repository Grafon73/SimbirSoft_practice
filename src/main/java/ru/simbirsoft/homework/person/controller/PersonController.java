package ru.simbirsoft.homework.person.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.person.dto.Person;
import ru.simbirsoft.homework.person.service.PersonService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "UserController", description = "Управление информацией о людях")
@RestController
@RequestMapping(value = "/person", produces = APPLICATION_JSON_VALUE)
public class PersonController {

 private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Получить список всех людей", httpMethod = "GET")
    public List<Person> getAllPersons(){
        return personService.getAllPersons();
    }

    @GetMapping("/")
    @ApiOperation(value = "Получить список людй по имени", httpMethod = "GET")
    public List<Person> getPerson(@RequestParam String name){

       return personService.getPerson(name);
    }
    @PostMapping("/")
    @ApiOperation(value = "Добавить человека в список", httpMethod = "POST")
    @JsonView(Person.class)
    public List<Person> addPerson(@Valid @RequestBody Person person){

    return personService.addPerson(person);
    }

    @DeleteMapping("/")
    @ApiOperation(value = "Удалить человека из списка", httpMethod = "DELETE")
    public ResponseEntity<Person> removePerson(@RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam String middleName) {

    return personService.removePerson(firstName, lastName, middleName);
    }

}
