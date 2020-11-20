package ru.simbirsoft.homework.book.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    @ApiOperation(value = "Добавить книгу в список", httpMethod = "POST")
    public ResponseEntity<BookView> addBook(@Valid @RequestBody BookView bookView) {
        return ResponseEntity.ok(bookService.addBook(bookView));
    }

    @DeleteMapping("/")
    @ApiOperation(value = "Удалить книгу из списка", httpMethod = "DELETE")
    public ResponseEntity<String> removeBook(@RequestParam Integer id) {
        bookService.removeBook(id);
        return  ResponseEntity.ok("OK");
    }

    @GetMapping("/author")
    @ApiOperation(value = "Получить список всех книг по автору", httpMethod = "GET")
    public ResponseEntity<List<BookView>> getBooksByAuthor(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(firstName,lastName,middleName));
    }

    @GetMapping("/genre")
    @ApiOperation(value = "Получить список всех книг по жанру", httpMethod = "GET")
    public ResponseEntity<List<BookView>> getBooksByGenre(@RequestParam String name) {
        return ResponseEntity.ok(bookService.getBooksByGenre(name));
    }


    @PutMapping("/")
    @ApiOperation(value = "Изменить жанр книги", httpMethod = "PUT")
    public ResponseEntity<BookView> editGenre(@Valid @RequestBody BookViewWithoutAuthor bookView) {
        return ResponseEntity.ok(bookService.editGenre(bookView));
    }

}
