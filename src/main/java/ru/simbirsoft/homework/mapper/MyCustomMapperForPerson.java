package ru.simbirsoft.homework.mapper;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.person.model.PersonEntity;
import ru.simbirsoft.homework.person.view.PersonView;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MyCustomMapperForPerson extends CustomMapper<PersonEntity, PersonView> {

    private final MapperFactory mapperFactory;

    @Override
    public void mapAtoB(PersonEntity personEntity,
                        PersonView personView,
                        MappingContext context) {
        personView.setBirthDate(personEntity.getBirthDate());
        personView.setFirstName(personEntity.getFirstName());
        personView.setLastName(personEntity.getLastName());
        personView.setMiddleName(personEntity.getMiddleName());
        Set<LibraryCard> libraryCard = personEntity.getBooks()
                .stream()
                .filter(a->!a.isInLibrary())
                .collect(Collectors.toSet());
        Set<BookEntity> books = new HashSet<>();
        libraryCard.forEach(a->books.add(a.getBook()));
        personView.setBooks(mapperFactory.getMapperFacade().mapAsList(books, BookView.class));
    }
}

