package ru.simbirsoft.homework.author.service;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.author.model.AuthorEntity;
import ru.simbirsoft.homework.author.repository.AuthorRepo;
import ru.simbirsoft.homework.author.view.AuthorView;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.exception.CustomRuntimeException;
import ru.simbirsoft.homework.exception.DataNotFoundException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;
    private final MapperFactory mapperFactory;

    @Override
    public List<AuthorWithoutBooks> getAllAuthors() {
        return mapperFactory.getMapperFacade().mapAsList(authorRepo.findAll(),AuthorWithoutBooks.class);
    }

    @Override
    public AuthorView listOfBooksByAuthor(Integer id) {
        AuthorEntity authorEntity = authorRepo.findById(id)
                .orElseThrow(()-> new CustomRuntimeException("Автора нет в базе данных"));
        return mapperFactory.getMapperFacade().map(authorEntity, AuthorView.class);
    }

    @Override
    public AuthorView addAuthor(AuthorView authorView) {
        AuthorEntity authorEntity = authorRepo
                .findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                                      authorView.getFirstName(),
                                      authorView.getLastName(),
                                      authorView.getMiddleName())
                        .orElseGet(() -> mapperFactory.getMapperFacade()
                        .map(authorView, AuthorEntity.class));
        authorEntity.setBooks(Sets.newHashSet(mapperFactory.getMapperFacade()
                .mapAsList(authorView.getBooks(), BookEntity.class)));
        authorEntity.getBooks().forEach(a->a.setAuthor(authorEntity));
        authorRepo.save(authorEntity);
        return mapperFactory.getMapperFacade().map(authorView, AuthorView.class);
    }

    @Override
    public void removeAuthor(Integer id) {
        AuthorEntity authorEntity = authorRepo
              .findById(id)
              .orElseThrow(()->
        new DataNotFoundException("Не найдена информация по данному Автору"));

        if(!authorEntity.getBooks().isEmpty()) {
            authorEntity.getBooks().forEach(a -> {
        if(!a.getPersons().isEmpty()) {
        throw new CustomRuntimeException(
               "Книга "+a.getName()+" данного автора находится у человека");
                }
            });

        }
     authorRepo.delete(authorEntity);

    }

}
