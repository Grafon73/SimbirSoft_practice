package ru.simbirsoft.homework.author.service;

import ru.simbirsoft.homework.author.view.AuthorView;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;

import java.util.List;


public interface AuthorService {
    List<AuthorWithoutBooks> all();
    AuthorView listOfBooks(AuthorWithoutBooks authorView);
    AuthorView addAuthor(AuthorView authorView);
    void removeAuthor(AuthorWithoutBooks authorView);
}
