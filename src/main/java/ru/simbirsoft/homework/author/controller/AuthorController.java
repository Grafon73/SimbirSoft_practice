package ru.simbirsoft.homework.author.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.aop.annotations.LogHistory;
import ru.simbirsoft.homework.aop.annotations.LogTime;
import ru.simbirsoft.homework.author.service.AuthorService;
import ru.simbirsoft.homework.author.view.AuthorView;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "AuthorController", description = "Управление авторами")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/authors", produces = APPLICATION_JSON_VALUE)
@LogTime
public class AuthorController {

    private final AuthorService authorService;

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/")
    @ApiOperation(value = "Добавить автора в список", httpMethod = "POST")
    @LogHistory
    public ResponseEntity<AuthorView> addAuthor(@Valid  @RequestBody AuthorView authorView){
          return ResponseEntity.ok(authorService.addAuthor(authorView));
    }
    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удалить автора из списка", httpMethod = "DELETE")
    @LogHistory
    public ResponseEntity<String> removeAuthor(@PathVariable Integer id){
        authorService.removeAuthor(id);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/")
    @ApiOperation(value = "Получить список авторов", httpMethod = "GET")
    public ResponseEntity<List<AuthorWithoutBooks>> getAllAuthors(){
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить список книг автора", httpMethod = "GET")
    public ResponseEntity<AuthorView> getAuthorsBook(@PathVariable Integer id){
        return ResponseEntity.ok(authorService.listOfBooksByAuthor(id));
    }

    @GetMapping("/filter")
    @ApiOperation(value = "Получить список авторов по ФИО и дате рождения", httpMethod = "GET")
    public ResponseEntity<List<AuthorWithoutBooks>> getAuthorsByBirthDateAndFIO(
               @RequestParam(required = false) String firstName,
               @RequestParam(required = false) String lastName,
               @RequestParam(required = false) String middleName,
               @RequestParam(required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
               @RequestParam(required = false)  @DateTimeFormat (iso = DateTimeFormat.ISO.DATE)Date to
    ){
        return ResponseEntity.ok(authorService.getAuthorsByBirthDateAndFIO(
                                    firstName,lastName,middleName,from,to));
    }

}
