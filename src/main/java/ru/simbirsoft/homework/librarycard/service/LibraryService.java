package ru.simbirsoft.homework.librarycard.service;

import ru.simbirsoft.homework.librarycard.view.LibraryView;

import java.util.List;

public interface LibraryService {

    /**
     * Вывод всех должников
     */
    List<LibraryView> getDebtors();
    /**
     * Добваить дней к возврату книги
     */
    LibraryView addDays(Integer bookId, Integer personId, Integer days);
}
