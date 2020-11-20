package ru.simbirsoft.homework.book.service;


import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.author.model.AuthorEntity;
import ru.simbirsoft.homework.author.repository.AuthorRepo;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;
import ru.simbirsoft.homework.exception.DataNotFound;
import ru.simbirsoft.homework.exception.MyCustomException;
import ru.simbirsoft.homework.genre.model.GenreEntity;
import ru.simbirsoft.homework.genre.repository.GenreRepo;
import ru.simbirsoft.homework.genre.view.GenreView;

import java.util.ArrayList;
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


    @Override
    public BookView addBook(BookView bookView) {
        AuthorEntity authorEntity =
                authorRepo.findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                        bookView.getAuthor().getFirstName(),
                        bookView.getAuthor().getLastName(),
                        bookView.getAuthor().getMiddleName()
                );
        BookEntity bookEntity = mapperFactory
                .getMapperFacade()
                .map(bookView,BookEntity.class);
        if(authorEntity!=null){
            bookEntity.setAuthor(authorEntity);
        }
        bookRepo.save(bookEntity);
        return bookView;
    }

    @Override
    public void removeBook(Integer id) {
        if(!bookRepo.findById(id).isPresent()){
            throw new DataNotFound(String.format("Книги с ID %s", id));
        }
        if(bookRepo.findById(id).get().getPersons().isEmpty()){
            bookRepo.deleteById(id);
        }else{
            throw new MyCustomException("Книгу нельзя удалить, пока она у человека");
        }
    }

    @Override
    public BookView editGenre(BookViewWithoutAuthor bookView) {
      BookEntity bookEntity =  bookRepo.getByName(bookView.getName());
      if(bookEntity==null){
          throw new DataNotFound(String.format("Книги c названием %s", bookView.getName()));
      }
      Set<GenreEntity> newGenres = Sets.newHashSet(mapperFactory.getMapperFacade().mapAsList(bookView.getGenres(),GenreEntity.class));
      bookEntity.setGenres(newGenres);
      genreRepo.removeAllByBooksIsNull();
      bookRepo.save(bookEntity);
        return mapperFactory.getMapperFacade().map(bookEntity,BookView.class);
    }

    @Override
    public List<BookView> getBooks(AuthorWithoutBooks authorView) {
        AuthorEntity authorEntity = mapperFactory
                .getMapperFacade()
                .map(authorView,AuthorEntity.class);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths("authorId")
                .withIgnorePaths("version")
                .withIgnorePaths("created")
                .withIgnorePaths("updated")
                .withIgnoreNullValues();
        Example<AuthorEntity> example = Example.of(authorEntity,exampleMatcher);
        List<AuthorEntity> authorEntityList = authorRepo.findAll(example);
        if(authorEntityList.isEmpty()){
            throw new DataNotFound("Автора");
        }
        List <BookEntity> bookEntities = new ArrayList<>();
        authorEntityList.forEach(a-> bookEntities.addAll(a.getBooks()));
        return mapperFactory.getMapperFacade().mapAsList(bookEntities,BookView.class);
    }

    @Override
    public List<BookView> getBooks(GenreView genreView) {
        GenreEntity genreEntity = mapperFactory
                .getMapperFacade()
                .map(genreView,GenreEntity.class);
        List <BookEntity> bookEntities = bookRepo.getAllByGenres_Name(genreEntity.getName());
        return mapperFactory.getMapperFacade().mapAsList(bookEntities,BookView.class);
    }
}
