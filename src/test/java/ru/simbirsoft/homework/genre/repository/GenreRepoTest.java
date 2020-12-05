package ru.simbirsoft.homework.genre.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.genre.model.GenreEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class GenreRepoTest {

    @Autowired
    GenreRepo genreRepo;

    @Test
    void countAllBooksByGenre() {
        String name = "Ужасы";
        int count = genreRepo.countAllBooksByGenre(name);
        assertThat(count).isEqualTo(6);
    }

    @Test
    void findByName() {
        Optional<GenreEntity> genreEntity = genreRepo.findByName("Ужасы");
        assertThat(genreEntity).isPresent();
        assertThat(genreEntity.get().getName()).isEqualTo("Ужасы");
    }

    @Test
    void removeAllByBooksIsNull() {
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName("Тест");
        genreRepo.save(genreEntity);
        genreRepo.removeAllByBooksIsNull();
        assertThat(genreRepo.findByName("Тест")).isNotPresent();
    }
}