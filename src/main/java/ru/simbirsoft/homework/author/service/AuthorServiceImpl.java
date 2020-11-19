package ru.simbirsoft.homework.author.service;

import com.google.common.collect.Sets;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirsoft.homework.author.model.AuthorEntity;
import ru.simbirsoft.homework.author.repository.AuthorRepo;
import ru.simbirsoft.homework.author.view.AuthorView;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.exception.DataNotFound;
import ru.simbirsoft.homework.exception.MyCustomException;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;
    private final MapperFactory mapperFactory;

    @Autowired
    public AuthorServiceImpl(AuthorRepo authorRepo, MapperFactory mapperFactory) {
        this.authorRepo = authorRepo;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public List<AuthorWithoutBooks> all() {
        return mapperFactory.getMapperFacade().mapAsList(authorRepo.findAll(),AuthorWithoutBooks.class);
    }

    @Override
    public AuthorView listOfBooks(AuthorWithoutBooks authorView) {
        AuthorEntity authorEntity = authorRepo.findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                                                authorView.getFirstName(),
                                                authorView.getLastName(),
                                                authorView.getMiddleName());
        return mapperFactory.getMapperFacade().map(authorEntity, AuthorView.class);
    }

    @Override
    public AuthorView addAuthor(AuthorView authorView) {
        AuthorEntity authorEntity = authorRepo.findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                authorView.getFirstName(),
                authorView.getLastName(),
                authorView.getMiddleName());
        if (authorEntity == null) {
            authorEntity = mapperFactory.getMapperFacade().map(authorView, AuthorEntity.class);
        }
        authorEntity.setBooks(Sets.newHashSet(mapperFactory.getMapperFacade()
                .mapAsList(authorView.getBooks(), BookEntity.class)));
        AuthorEntity finalAuthorEntity = authorEntity;
        authorEntity.getBooks().forEach(a->a.setAuthor(finalAuthorEntity));
        authorRepo.save(authorEntity);
        return mapperFactory.getMapperFacade().map(authorView, AuthorView.class);
    }

    @Override
    public void removeAuthor(AuthorWithoutBooks authorView) {
        AuthorEntity authorEntity = authorRepo.findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                authorView.getFirstName(),
                authorView.getLastName(),
                authorView.getMiddleName());
        if(authorEntity==null){
            throw new DataNotFound("Автора");
        }
        if(!authorEntity.getBooks().isEmpty()){
            throw new MyCustomException("Нельзя удалить автора пока есть его книги");
        }
     authorRepo.delete(authorEntity);
    }

}
