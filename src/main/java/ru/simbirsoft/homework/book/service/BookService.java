package ru.simbirsoft.homework.book.service;

import org.springframework.http.ResponseEntity;
import ru.simbirsoft.homework.book.dto.Book;

import java.util.List;


public interface BookService {
    List<Book> getAllBooks();
    List<Book> getBook(String author);
    List<Book> addBook(Book book);
    ResponseEntity<Book> removeBook(String author,String name);
}
