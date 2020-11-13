package ru.simbirsoft.homework.book.controller;


import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.book.dto.Book;
import ru.simbirsoft.homework.book.service.BookService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "BookController", description = "Управление информацией о книгах")
@RestController
@RequestMapping(value = "/book", produces = APPLICATION_JSON_VALUE)
public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Получить список всех книг", httpMethod = "GET")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/")
    @ApiOperation(value = "Получить список книг по автору", httpMethod = "GET")
    public List<Book> getBook(@RequestParam String author) {
        return bookService.getBook(author);
    }

    @PostMapping("/")
    @ApiOperation(value = "Добавить книгу в список", httpMethod = "POST")
    @JsonView(Book.class)
    public List<Book> addBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    @DeleteMapping("/")
    @ApiOperation(value = "Удалить книгу из списка", httpMethod = "DELETE")
    public ResponseEntity<Book> removeBook(@RequestParam String author,
                                           @RequestParam String name) {
        return bookService.removeBook(author, name);
    }

}
