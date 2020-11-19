package ru.simbirsoft.homework.book.service;


import com.google.common.collect.Sets;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirsoft.homework.author.model.AuthorEntity;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;
import ru.simbirsoft.homework.exception.DataNotFound;
import ru.simbirsoft.homework.exception.MyCustomException;
import ru.simbirsoft.homework.genre.model.GenreEntity;
import ru.simbirsoft.homework.genre.view.GenreView;

import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final MapperFactory mapperFactory;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo, MapperFactory mapperFactory) {
        this.bookRepo = bookRepo;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public BookView addBook(BookView bookView) {
        BookEntity bookEntity = mapperFactory
                .getMapperFacade()
                .map(bookView,BookEntity.class);
        bookRepo.save(bookEntity);
        return bookView;
    }

    @Override
    public void removeBook(Integer id) {
        if(!bookRepo.findById(id).isPresent()){
            throw new DataNotFound(String.format("Книги с ID %s", id));
        }
        if(bookRepo.findById(id).get().getPerson()==null){
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
      bookRepo.save(bookEntity);
        return mapperFactory.getMapperFacade().map(bookEntity,BookView.class);
    }

    @Override
    public List<BookView> getBooks(AuthorWithoutBooks authorView) {
        AuthorEntity authorEntity = mapperFactory
                .getMapperFacade()
                .map(authorView,AuthorEntity.class);
        if(!bookRepo.existsByAuthor_FirstNameAndAuthor_LastNameAndAuthor_MiddleName(
                authorEntity.getFirstName(),
                authorEntity.getLastName(),
                authorEntity.getMiddleName())){
            throw new DataNotFound("Автора");
        }
        List <BookEntity> bookEntities = bookRepo.getAllByAuthor_FirstNameOrAuthor_LastNameOrAuthor_MiddleName(authorEntity.getFirstName(),authorEntity.getLastName(),authorEntity.getMiddleName());
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
