package ru.simbirsoft.homework.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.dto.Person;

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

    @GetMapping("/{name}")
    @ApiOperation(value = "Получить список людй по имени", httpMethod = "GET")
    public List<Person> getPerson(@PathVariable String name){
        return persons.stream().filter(p -> p.getFirstName().equals(name)).collect(Collectors.toList());

    }
    @PostMapping("/")
    @ApiOperation(value = "Добавить человека в список", httpMethod = "POST")
    public Object addPerson(@Valid @RequestBody Person person){

        persons.add(person);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("firstName","lastName","middleName");
        FilterProvider filters = new SimpleFilterProvider().addFilter("PersonFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(persons);
        mapping.setFilters(filters);

        return mapping;
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
