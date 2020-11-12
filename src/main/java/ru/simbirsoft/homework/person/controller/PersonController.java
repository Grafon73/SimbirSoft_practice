package ru.simbirsoft.homework.person.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.person.dto.Person;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "UserController", description = "Управление информацией о людях")
@RestController
@RequestMapping(value = "/person", produces = APPLICATION_JSON_VALUE)
public class PersonController {

    static List<Person> persons = new ArrayList<>(Arrays.asList(
            new Person("Евгина","Жданова", "Николаевна", new Date(1212121212121L)),
            new Person("Виталий","Булгаков", "Закирович", new Date(1222222222222L)),
            new Person("Зорина","Воронин", "Богданович",new Date(1333333333333L))
    ));

    @GetMapping("/all")
    @ApiOperation(value = "Получить список всех людей", httpMethod = "GET")
    public List<Person> getAllPersons(){
        return persons;
    }

    @GetMapping("/")
    @ApiOperation(value = "Получить список людй по имени", httpMethod = "GET")
    public List<Person> getPerson(@RequestParam String name){
        List<Person> personList = persons.stream()
                .filter(p -> p.getFirstName().equals(name))
                .collect(Collectors.toList());
        if(personList.isEmpty()){
            throw new NullPointerException();
        }
        return personList;
    }
    @PostMapping("/")
    @ApiOperation(value = "Добавить человека в список", httpMethod = "POST")
    public List<Person> addPerson(@Valid @RequestBody Person person){

        persons.add(person);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("PersonFilter", SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","middleName"));
        ObjectMapper om = new ObjectMapper();
        om.setFilterProvider(filterProvider);
        List<Person> resultList =  null;
        try {
            String json = om.writeValueAsString(persons);
            resultList = om.readValue(json,new TypeReference<List<Person>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @DeleteMapping("/")
    @ApiOperation(value = "Удалить человека из списка", httpMethod = "DELETE")
    public ResponseEntity<Person> removePerson(String firstName,
                                               String lastName,
                                               String middleName) {
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
