package ru.simbirsoft.homework.librarycard.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class LibraryRepoTest {

    @Autowired
    LibraryRepo libraryRepo;

    @Test
    void getAllDebt() {
        List<LibraryCard> libraryCards = libraryRepo.getAllDebt();
        assertThat(libraryCards).isNotEmpty();
        assertThat(libraryCards.stream().allMatch(libraryCard ->
                libraryCard.getReturned().isBefore(LocalDateTime.now()))).isTrue();
    }

    @Test
    void findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse() {
        Integer bookId = 1;
        Integer personId = 1;
        Optional<LibraryCard> libraryCard =
                libraryRepo.findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse(bookId,personId);
        assertThat(libraryCard).isPresent();
        assertThat(libraryCard.get().getBook().getBookId()).isEqualTo(bookId);
        assertThat(libraryCard.get().getPerson().getPersonId()).isEqualTo(personId);
    }

    @Test
    void findByPerson_PersonIdAndBook_NameAndInLibraryFalse() {
        String name = "Удивительное приключение";
        Integer personId = 1;
        Optional<LibraryCard> libraryCard =
                libraryRepo.findByPerson_PersonIdAndBook_NameAndInLibraryFalse(personId,name);
        assertThat(libraryCard).isPresent();
        assertThat(libraryCard.get().getBook().getName()).isEqualTo(name);
        assertThat(libraryCard.get().getPerson().getPersonId()).isEqualTo(personId);
    }
}