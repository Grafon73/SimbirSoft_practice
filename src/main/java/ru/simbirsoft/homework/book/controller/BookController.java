package ru.simbirsoft.homework.book.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.book.service.BookService;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;
import ru.simbirsoft.homework.genre.view.GenreView;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "BookController", description = "Управление информацией о книгах")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/books", produces = APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    @ApiOperation(value = "Добавить книгу в список", httpMethod = "POST")
    public ResponseEntity<BookView> addBook(@Valid @RequestBody BookView bookView) {
        return ResponseEntity.ok(bookService.addBook(bookView));
    }

    @DeleteMapping("/book")
    @ApiOperation(value = "Удалить книгу из списка", httpMethod = "DELETE")
    public ResponseEntity<String> removeBook(@RequestParam Integer id) {
        bookService.removeBook(id);
        return  ResponseEntity.ok("OK");
    }

    @PostMapping("/get_by_author")
    @ApiOperation(value = "Получить список всех книг по автору", httpMethod = "POST")
    public ResponseEntity<List<BookView>> getBooksByAuthor(@RequestBody AuthorWithoutBooks authorView) {
        return ResponseEntity.ok(bookService.getBooks(authorView));
    }

    @PostMapping("/get_by_genre")
    @ApiOperation(value = "Получить список всех книг по жанру", httpMethod = "POST")
    public ResponseEntity<List<BookView>> getBooksByGenre(@RequestBody GenreView genreView) {
        return ResponseEntity.ok(bookService.getBooks(genreView));
    }


    @PostMapping("/edit")
    @ApiOperation(value = "Изменить жанр книги", httpMethod = "POST")
    public ResponseEntity<BookView> editGenre(@Valid @RequestBody BookViewWithoutAuthor bookView) {
        return ResponseEntity.ok(bookService.editGenre(bookView));
    }

}
