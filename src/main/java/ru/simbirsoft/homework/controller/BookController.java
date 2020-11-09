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
import ru.simbirsoft.homework.dto.Book;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "BookController", description = "Управление информацией о книгах")
@RestController
@RequestMapping(value = "/book", produces = APPLICATION_JSON_VALUE)
public class BookController {

    static List<Book> books = new ArrayList<>(Arrays.asList(
            new Book("С того света", "Бернар Вербер", "Фантастика"),
            new Book("Черно-белая палитра", "Ольга Куно", "Драма"),
            new Book("Жизнь и судьба", "Василий Гроссман", "Детектив")
    ));

    @GetMapping("/all")
    @ApiOperation(value = "Получить список всех книг", httpMethod = "GET")
    public List<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/{author}")
    @ApiOperation(value = "Получить список книг по автору", httpMethod = "GET")
    public List<Book> getBook(@PathVariable String author) {
      return books.stream().filter(p -> p.getAuthor().equals(author)).collect(Collectors.toList());
    }

    @PostMapping("/")
    @ApiOperation(value = "Добавить книгу в список", httpMethod = "POST")
    public MappingJacksonValue addPerson(@Valid @RequestBody Book book) {

        books.add(book);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","author");
        FilterProvider filters = new SimpleFilterProvider().addFilter("BookFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(books);
        mapping.setFilters(filters);

        return mapping;
    }

    @DeleteMapping("/")
    @ApiOperation(value = "Удалить книгу из списка", httpMethod = "DELETE")
    public ResponseEntity<Book> removePerson(String author,
                                             String name) {
        if (books.stream().anyMatch(b ->
                        b.getAuthor().equals(author) &&
                        b.getName().equals(name))) {
            books.removeIf(b ->
                        b.getAuthor().equals(author) &&
                        b.getName().equals(name));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
