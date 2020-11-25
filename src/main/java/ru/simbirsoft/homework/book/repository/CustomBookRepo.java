package ru.simbirsoft.homework.book.repository;

import ru.simbirsoft.homework.book.model.BookEntity;

import java.util.List;


public interface CustomBookRepo {

  List<BookEntity> findByGenreAndDate(String genre, Integer year, Attribute attribute);
}
