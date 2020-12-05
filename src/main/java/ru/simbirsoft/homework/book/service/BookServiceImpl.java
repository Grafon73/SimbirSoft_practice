package ru.simbirsoft.homework.book.service;



import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.author.model.AuthorEntity;
import ru.simbirsoft.homework.author.repository.AuthorRepo;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.book.repository.Attribute;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.book.repository.CustomBookRepo;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;
import ru.simbirsoft.homework.exception.CustomRuntimeException;
import ru.simbirsoft.homework.exception.DataNotFoundException;
import ru.simbirsoft.homework.genre.model.GenreEntity;
import ru.simbirsoft.homework.genre.repository.GenreRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final MapperFactory mapperFactory;
    private final GenreRepo genreRepo;
    private final AuthorRepo authorRepo;
    private final CustomBookRepo customBookRepo;


    @Override
    public BookView addBook(BookView bookView) {
        BookEntity bookEntity = mapperFactory
                .getMapperFacade()
                .map(bookView,BookEntity.class);
        authorRepo.findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                        bookView.getAuthor().getFirstName(),
                        bookView.getAuthor().getLastName(),
                        bookView.getAuthor().getMiddleName())
                .ifPresent(bookEntity::setAuthor);

        bookRepo.save(bookEntity);
        return bookView;
    }

    @Override
    public void removeBook(Integer id) {
        BookEntity bookEntity= bookRepo.findById(id).orElseThrow(()->
              new DataNotFoundException(
              "Книги с ID "+id+" нет в базе данных"));
        if(bookEntity.getPersons()!= null && !bookEntity.getPersons().isEmpty()){
            throw new CustomRuntimeException("Книгу нельзя удалить, пока она у человека");
        }
        bookRepo.deleteById(id);
    }

    @Override
    public BookView editGenre(BookViewWithoutAuthor bookView) {
      BookEntity bookEntity =  bookRepo.findByName(bookView.getName())
              .orElseThrow(() ->
                      new DataNotFoundException("Книги c названием "
                              +bookView.getName()+" нет в базе"));

      Set<GenreEntity> newGenres = new HashSet<>(mapperFactory
              .getMapperFacade().mapAsList(bookView.getGenres(),GenreEntity.class));
      bookEntity.setGenres(newGenres);
      genreRepo.removeAllByBooksIsNull();
      bookRepo.save(bookEntity);
        return mapperFactory.getMapperFacade().map(bookEntity,BookView.class);
    }

    @Override
    public List<BookView> getBooksByAuthor (String firstName, String lastName, String middleName) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setFirstName(firstName);
        authorEntity.setLastName(lastName);
        authorEntity.setMiddleName(middleName);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths("authorId")
                .withIgnorePaths("version")
                .withIgnorePaths("created")
                .withIgnorePaths("updated")
                .withIgnoreNullValues();
        Example<AuthorEntity> example = Example.of(authorEntity,exampleMatcher);
        List<AuthorEntity> authorEntityList = authorRepo.findAll(example);
        if(authorEntityList.isEmpty()){
            throw new DataNotFoundException("Не найдена информация по данному Автору");
        }
        List <BookEntity> bookEntities = new ArrayList<>();
        authorEntityList.forEach(a-> bookEntities.addAll(a.getBooks()));
        return mapperFactory.getMapperFacade().mapAsList(bookEntities,BookView.class);
    }

    @Override
    public List<BookView> getBooksByGenre(String name) {
        List <BookEntity> bookEntities = bookRepo.getAllByGenres_Name(name);
        return mapperFactory.getMapperFacade().mapAsList(bookEntities,BookView.class);
    }

    @Override
    public List<BookView> getBooksByGenreAndDate(String genre,
                                                 Integer year,
                                                 Attribute attribute) {
        return mapperFactory.getMapperFacade()
                .mapAsList(customBookRepo
                                .findByGenreAndDate(genre, year, attribute),
                        BookView.class);
    }
}
