package ru.simbirsoft.homework.librarycard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;

import java.util.List;
import java.util.Optional;

public interface LibraryRepo  extends JpaRepository<LibraryCard,Integer> {
    @Query("SELECT l FROM LibraryCard l WHERE current_date>l.returned and l.inLibrary=false")
    List<LibraryCard> getAllDebt();
    Optional<LibraryCard> findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse(Integer bookId, Integer personId);
    Optional<LibraryCard> findByPerson_PersonIdAndBook_NameAndInLibraryFalse(Integer personId, String name);
}
