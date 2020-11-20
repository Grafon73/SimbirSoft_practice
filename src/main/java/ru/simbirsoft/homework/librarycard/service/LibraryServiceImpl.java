package ru.simbirsoft.homework.librarycard.service;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.exception.MyCustomException;
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
           throw new MyCustomException(String.format("Человек с ID %d не найден",personId));
       }
        if(!bookRepo.findById(bookId).isPresent()){
            throw new MyCustomException(String.format("Книга с ID %d не найдена",bookId));
        }
        LibraryCard libraryCard =
                libraryRepo.findByBook_BookIdAndPerson_PersonId(bookId,personId);
        if(libraryCard==null){
            throw new MyCustomException(
                    String.format("У человека с ID %d нет книги c ID %d",personId,bookId));
        }
        libraryCard.addDays(days);
        libraryRepo.save(libraryCard);
        mapperFactory.classMap(LibraryCard.class, LibraryView.class)
                .customize(new MyCustomMapperForLibrary(mapperFactory))
                .register();
        return mapperFactory.getMapperFacade().map(libraryCard,LibraryView.class);
    }
}
