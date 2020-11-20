package ru.simbirsoft.homework.librarycard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;

import java.util.List;

public interface LibraryRepo  extends JpaRepository<LibraryCard,Integer> {
    @Query("SELECT l FROM LibraryCard l WHERE current_date>l.returned")
    List<LibraryCard> getAllDebt();
    LibraryCard findByBook_BookIdAndPerson_PersonId(Integer bookId,Integer personId);
}
