package ru.simbirsoft.homework.librarycard.service;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.exception.CustomRuntimeException;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.librarycard.repository.LibraryRepo;
import ru.simbirsoft.homework.librarycard.view.LibraryView;
import ru.simbirsoft.homework.mapper.MyCustomMapperForLibrary;
import ru.simbirsoft.homework.person.repository.PersonRepo;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepo libraryRepo;
    private final MapperFactory mapperFactory;
    private final PersonRepo personRepo;
    private final BookRepo bookRepo;



    @Override
    public List<LibraryView> getDebtors() {
        mapperFactory.classMap(LibraryCard.class, LibraryView.class)
                .customize(new MyCustomMapperForLibrary(mapperFactory))
                .register();
        return mapperFactory.getMapperFacade().mapAsList(libraryRepo.getAllDebt(), LibraryView.class);
    }

    @Override
    public LibraryView addDays(Integer bookId, Integer personId, Integer days) {
       if(!personRepo.findById(personId).isPresent()){
           throw new CustomRuntimeException("Человек с ID "+personId+" не найден");
       }
        if(!bookRepo.findById(bookId).isPresent()){
            throw new CustomRuntimeException("Книга с ID "+bookId+" не найдена");
        }
        LibraryCard libraryCard =
                libraryRepo.findByBook_BookIdAndPerson_PersonId(bookId,personId)
                        .orElseThrow(()->
                        new CustomRuntimeException(
                        "У человека с ID "+personId+" нет книги c ID "+bookId));
        libraryCard.addDays(days);
        libraryRepo.save(libraryCard);
        mapperFactory.classMap(LibraryCard.class, LibraryView.class)
                .customize(new MyCustomMapperForLibrary(mapperFactory))
                .register();
        return mapperFactory.getMapperFacade().map(libraryCard,LibraryView.class);
    }
}
