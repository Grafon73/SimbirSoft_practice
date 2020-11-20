package ru.simbirsoft.homework.mapper;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.librarycard.view.LibraryView;
import ru.simbirsoft.homework.person.view.PersonViewWithoutBooks;

import java.time.LocalDateTime;
import java.time.Period;

public class MyCustomMapperForLibrary extends CustomMapper<LibraryCard, LibraryView> {

    private final MapperFactory mapperFactory;

    public MyCustomMapperForLibrary(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    public void mapAtoB(LibraryCard libraryCard,
                        LibraryView libraryView,
                        MappingContext context) {
        libraryView.setPersonViewWithoutBooks(
                mapperFactory.getMapperFacade()
                        .map(libraryCard.getPerson(), PersonViewWithoutBooks.class)
        );
        libraryView.setBookViewWithoutAuthor(
                mapperFactory.getMapperFacade()
                .map(libraryCard.getBook(), BookViewWithoutAuthor.class)
        );
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = libraryCard.getReturned();
        if(now.isAfter(expire)){
            libraryView.setExpired("Возврат книги просрочен на "
                    +Period.between(expire.toLocalDate(),now.toLocalDate()).getDays()
                    +" дней");
        }else {
            libraryView.setExpired("На возврат книги есть еще "
                    +Period.between(now.toLocalDate(),expire.toLocalDate()).getDays()
                    +" дней");
        }

    }
}