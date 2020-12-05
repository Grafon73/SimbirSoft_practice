package ru.simbirsoft.homework.book.repository;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.book.model.BookEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CustomBookRepoImplTest {

    @Autowired
    CustomBookRepo customBookRepo;

    @Test
    @SneakyThrows
    void findByGenreAndDate() {
        String genre = "Ужасы";
        Integer year = 2019;
        Attribute attribute = Attribute.BEFORE;
        List<BookEntity> bookEntities = customBookRepo.findByGenreAndDate(genre,year,attribute);
        assertThat(bookEntities).isNotEmpty();
        assertThat(bookEntities.get(0)
                .getGenres()
                .stream()
                .anyMatch(a->a.getName().equals(genre)))
                .isTrue();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        assertThat(bookEntities.get(0).getPublicated()).isBefore(format.parse(year.toString()));
    }
}