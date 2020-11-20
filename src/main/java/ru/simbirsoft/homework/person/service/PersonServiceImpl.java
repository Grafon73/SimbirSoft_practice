package ru.simbirsoft.homework.person.service;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.exception.DataNotFound;
import ru.simbirsoft.homework.exception.MyCustomException;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.librarycard.repository.LibraryRepo;
import ru.simbirsoft.homework.mapper.MyCustomMapperForPerson;
import ru.simbirsoft.homework.person.model.PersonEntity;
import ru.simbirsoft.homework.person.repository.PersonRepo;
import ru.simbirsoft.homework.person.view.PersonView;
import ru.simbirsoft.homework.person.view.PersonViewWithoutBooks;
import ru.simbirsoft.homework.person.view.PersonViewWithoutDateAndBooks;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{

    private final PersonRepo personRepo;
    private final MapperFactory mapperFactory;
    private final BookRepo bookRepo;
    private final LibraryRepo libraryRepo;


    @Override
    public List<PersonView> getAllPersons() {
        mapperFactory.classMap(PersonEntity.class, PersonView.class)
                .customize(new MyCustomMapperForPerson(mapperFactory))
                .register();
        return mapperFactory.getMapperFacade()
                .mapAsList(personRepo.findAll(),PersonView.class);
    }

    @Override
    public List<BookView> getPersonsBooks(Integer id) {
        Optional<PersonEntity> personEntityOptional = personRepo.findById(id);
       if(!personEntityOptional.isPresent()){
           throw new DataNotFound(String.format("Человека с ID %d",id));
       }
        Set<LibraryCard> libraryCard = personEntityOptional.get().getBooks();
       Set<BookEntity> books = new HashSet<>();
        libraryCard.forEach(a->books.add(a.getBook()));
        return mapperFactory.getMapperFacade()
                .mapAsList(books,BookView.class);
    }

    @Override
    public PersonViewWithoutBooks addPerson(PersonViewWithoutBooks personView) {
        personRepo.save(mapperFactory
                .getMapperFacade().map(personView,PersonEntity.class));
        return personView;
    }

    @Override
    public PersonViewWithoutBooks editPerson(Integer id, PersonViewWithoutBooks personView) {
        Optional<PersonEntity> personEntityOptional = personRepo.findById(id);
        if(!personEntityOptional.isPresent()){
            throw new DataNotFound(String.format("Человека с ID %d",id));
        }
       PersonEntity personEntity = personEntityOptional.get();
        personEntity.setFirstName(personView.getFirstName());
        personEntity.setLastName(personView.getLastName());
        if(personView.getBirthDate()!=null) {
            personEntity.setBirthDate(personView.getBirthDate());
        }
        if(personView.getMiddleName()!=null) {
            personEntity.setMiddleName(personView.getMiddleName());
        }
        personRepo.save(personEntity);
        return mapperFactory.getMapperFacade()
                .map(personEntity,PersonViewWithoutBooks.class);
    }

    @Override
    public void removePerson(Integer id) {
        Optional<PersonEntity> personEntityOptional = personRepo.findById(id);
        if(!personEntityOptional.isPresent()){
            throw new DataNotFound(String.format("Человека с ID %d",id));
        }
        PersonEntity personEntity = personEntityOptional.get();
        if(!personEntity.getBooks().isEmpty()){
            throw new MyCustomException("Пользователь не может быть удален, " +
                    "пока не вернет книги");
        }
        personRepo.delete(personEntity);
    }

    @Override
    public void removePerson(PersonViewWithoutDateAndBooks personView) {
        List<PersonEntity> personEntityOptional =
                personRepo.findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
                        personView.getFirstName(),
                        personView.getLastName(),
                        personView.getMiddleName());
    if(personEntityOptional.isEmpty()){
    throw new DataNotFound(String.format("ФИО %s %s %s",
            personView.getFirstName(),
            personView.getLastName(),
            personView.getMiddleName()));
    }
    personEntityOptional.forEach(a-> {
                if (!a.getBooks().isEmpty()) {
                    throw new MyCustomException(String.format("Пользователь " +
                            "c ID %s не может быть удален, " +
                            "пока не вернет книги", a.getPersonId()));
                }
                personRepo.delete(a);
            }
            );
    }

    @Override
    public PersonView borrowBook(Integer id, String name) {
        Optional<PersonEntity> personEntityOptional = personRepo.findById(id);
        BookEntity bookEntity = bookRepo.getByName(name);
        if(!personEntityOptional.isPresent()){
            throw new DataNotFound(String.format("Человека с ID %d",id));
        }
        if(bookEntity==null){
            throw new DataNotFound(
                    String.format("Книги с названием %s. Книги нет в библиотеке",
                            name));
        }
        if(!bookEntity.getPersons().isEmpty()){
            throw new MyCustomException("Книга уже взята из библиотеки");
        }
        PersonEntity personEntity = personEntityOptional.get();
        if(personEntity.getBooks().stream().anyMatch(a->
                LocalDateTime.now().isAfter(a.getReturned()))){
            throw new MyCustomException(String.format(
                    "У Человека с ID %d есть просроченная книга",id));
        }
        personEntity.addBook(bookEntity);
        personRepo.flush();
        personRepo.save(personEntity);
        mapperFactory.classMap(PersonEntity.class, PersonView.class)
                .customize(new MyCustomMapperForPerson(mapperFactory))
                .register();
        return mapperFactory.getMapperFacade().map(personEntity, PersonView.class);
    }

    @Override
    public PersonView returnBook(Integer id, String name) {
        Optional<PersonEntity> personEntityOptional = personRepo.findById(id);
        BookEntity bookEntity = bookRepo.getByName(name);
        if(!personEntityOptional.isPresent()){
            throw new DataNotFound(String.format("Человека с ID %d",id));
        }
        if(bookEntity==null){
            throw new DataNotFound(
                    String.format("Книги с названием %s. Книги нет в библиотеке",
                            name));
        }
        if(bookEntity.getPersons().isEmpty()){
            throw new MyCustomException("Книга находится в библиотеке");
        }

        PersonEntity personEntity = personEntityOptional.get();
        if(personEntity.getBooks().stream().noneMatch(a->a.getBook().getName().equals(name))){
            throw new MyCustomException(
                    String.format("Книга '%s' не у человека c ID %d", name, id));
        }
        personEntity.removeBook(bookEntity);
        personRepo.save(personEntity);
        mapperFactory.classMap(PersonEntity.class, PersonView.class)
                .customize(new MyCustomMapperForPerson(mapperFactory))
                .register();
        return mapperFactory.getMapperFacade().map(personEntity, PersonView.class);
    }

}
