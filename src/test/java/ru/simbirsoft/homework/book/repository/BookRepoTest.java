package ru.simbirsoft.homework.book.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.book.model.BookEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class BookRepoTest {

    @Autowired
    BookRepo bookRepo;

    @Test
    void getAllByAuthor_FirstNameOrAuthor_LastNameOrAuthor_MiddleName() {
        String firstName = "Илья";
       List<BookEntity> bookEntityList = bookRepo.getAllByAuthor_FirstNameOrAuthor_LastNameOrAuthor_MiddleName(
                firstName,
                null,
                null
        );
       assertThat(bookEntityList).isNotEmpty();
       assertThat(bookEntityList.stream().allMatch(b->
               b.getAuthor().getFirstName().equals(firstName))).isTrue();
    }

    @Test
    void getAllByGenres_Name() {
        String genre = "Ужасы";
        List<BookEntity> bookEntities = bookRepo.getAllByGenres_Name(genre);
        assertThat(bookEntities).isNotEmpty();
        assertThat(bookEntities.stream().allMatch(b->
                b.getGenres().stream().anyMatch(g->
                        g.getName().equals(genre)
                ))).isTrue();
    }

    @Test
    void findByName() {
        String name = "Битва";
        Optional<BookEntity> bookEntity = bookRepo.findByName(name);
        assertThat(bookEntity).isPresent();
        assertThat(bookEntity.get().getName()).isEqualTo(name);
    }
}