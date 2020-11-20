package ru.simbirsoft.homework.author.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.author.service.AuthorService;
import ru.simbirsoft.homework.author.view.AuthorView;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "AuthorController", description = "Управление авторами")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/authors", produces = APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/author")
    @ApiOperation(value = "Добавить автора в список", httpMethod = "POST")
    public ResponseEntity<AuthorView> addAuthor(@Valid  @RequestBody AuthorView authorView){
          return ResponseEntity.ok(authorService.addAuthor(authorView));
    }
    @DeleteMapping("/author")
    @ApiOperation(value = "Удалить автора из списка", httpMethod = "DELETE")
    public ResponseEntity<String> removeAuthor(@Valid  @RequestBody AuthorWithoutBooks authorView){
        authorService.removeAuthor(authorView);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/")
    @ApiOperation(value = "Получить список авторов", httpMethod = "GET")
    public ResponseEntity<List<AuthorWithoutBooks>> getAllAuthors(){
        return ResponseEntity.ok(authorService.all());
    }

    @PostMapping("/")
    @ApiOperation(value = "Получить список книг автора", httpMethod = "POST")
    public ResponseEntity<AuthorView> getAuthorsBook(@Valid  @RequestBody AuthorWithoutBooks authorView){
        return ResponseEntity.ok(authorService.listOfBooks(authorView));
    }

}
