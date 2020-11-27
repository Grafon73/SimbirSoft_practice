package ru.simbirsoft.homework.book.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.book.repository.Attribute;
import ru.simbirsoft.homework.book.service.BookService;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "BookController", description = "Управление информацией о книгах")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books", produces = APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    @PostMapping("/")
    @Secured(value = {"ROLE_ADMIN"})
    @ApiOperation(value = "Добавить книгу в список", httpMethod = "POST")
    public ResponseEntity<BookView> addBook(@Valid @RequestBody BookView bookView) {
        return ResponseEntity.ok(bookService.addBook(bookView));
    }

    @DeleteMapping("/")
    @Secured(value = {"ROLE_ADMIN"})
    @ApiOperation(value = "Удалить книгу из списка", httpMethod = "DELETE")
    public ResponseEntity<String> removeBook(@RequestParam Integer id) {
        bookService.removeBook(id);
        return  ResponseEntity.ok("OK");
    }

    @GetMapping("/")
    @ApiOperation(value = "Получить список всех книг по автору", httpMethod = "GET")
    public ResponseEntity<List<BookView>> getBooksByAuthor(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(firstName,lastName,middleName));
    }

    @GetMapping("/{name}")
    @ApiOperation(value = "Получить список всех книг по жанру", httpMethod = "GET")
    public ResponseEntity<List<BookView>> getBooksByGenre(@PathVariable String name) {
        return ResponseEntity.ok(bookService.getBooksByGenre(name));
    }


    @PutMapping("/")
    @Secured(value = {"ROLE_ADMIN"})
    @ApiOperation(value = "Изменить жанр книги", httpMethod = "PUT")
    public ResponseEntity<BookView> editGenre(@Valid @RequestBody BookViewWithoutAuthor bookView) {
        return ResponseEntity.ok(bookService.editGenre(bookView));
    }

    @GetMapping("/filter")
    @ApiOperation(value = "Получить список всех книг по жанру и дате публикации",
            httpMethod = "GET")
    public ResponseEntity<List<BookView>> getBooksByGenreAndDate(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Attribute attribute) {
        if(year!=null && attribute==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(bookService
                .getBooksByGenreAndDate(genre,year,attribute));
    }

}
