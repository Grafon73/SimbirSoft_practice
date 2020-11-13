package ru.simbirsoft.homework.book.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.simbirsoft.homework.book.dto.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    static List<Book> books = new ArrayList<>(Arrays.asList(
            new Book("С того света", "Бернар Вербер", "Фантастика"),
            new Book("Черно-белая палитра", "Ольга Куно", "Драма"),
            new Book("Жизнь и судьба", "Василий Гроссман", "Детектив")
    ));

    @Override
    public List<Book> getAllBooks() {
        return books;
    }

    @Override
    public List<Book> getBook(String author) {
        return books.stream()
                .filter(p -> p.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> addBook(Book book) {
        books.add(book);
        return books;
    }

    @Override
    public ResponseEntity<Book> removeBook(String author, String name) {
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
