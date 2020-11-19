package ru.simbirsoft.homework.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.homework.book.model.BookEntity;

import java.util.List;

public interface BookRepo extends JpaRepository<BookEntity,Integer> {
    List<BookEntity> getAllByAuthor_FirstNameOrAuthor_LastNameOrAuthor_MiddleName(String firstName, String lastName, String middleName);
    List<BookEntity> getAllByGenres_Name(String genre);
    BookEntity getByName(String name);
    boolean existsByAuthor_FirstNameAndAuthor_LastNameAndAuthor_MiddleName(String firstName, String lastName, String middleName);

}
