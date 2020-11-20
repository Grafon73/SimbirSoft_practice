package ru.simbirsoft.homework.person.service;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.exception.CustomRuntimeException;
import ru.simbirsoft.homework.exception.DataNotFoundException;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.mapper.MyCustomMapperForPerson;
import ru.simbirsoft.homework.person.model.PersonEntity;
import ru.simbirsoft.homework.person.repository.PersonRepo;
import ru.simbirsoft.homework.person.view.PersonView;
import ru.simbirsoft.homework.person.view.PersonViewWithoutBooks;
import ru.simbirsoft.homework.person.view.PersonViewWithoutDateAndBooks;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{

    private final PersonRepo personRepo;
    private final MapperFactory mapperFactory;
    private final BookRepo bookRepo;


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
        PersonEntity personEntity = personRepo
                .findById(id)
                .orElseThrow(()->
                        new DataNotFoundException("Человека с ID "+id));
        Set<LibraryCard> libraryCard = personEntity.getBooks();
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
        PersonEntity personEntity = personRepo
                .findById(id)
                .orElseThrow(()->
                        new DataNotFoundException(
                                "Не найдена информация по Человеку с ID "+id));

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
        PersonEntity personEntity = personRepo
                .findById(id)
                .orElseThrow(()->
                        new DataNotFoundException(
                                "Не найдена информация по Человеку с ID "+id));
        if(!personEntity.getBooks().isEmpty()){
            throw new CustomRuntimeException("Пользователь не может быть удален, " +
                    "пока не вернет книги");
        }
        personRepo.delete(personEntity);
    }

    @Override
    public void removePerson(PersonViewWithoutDateAndBooks personView) {
        List<PersonEntity> personEntity =
                personRepo.findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
                        personView.getFirstName(),
                        personView.getLastName(),
                        personView.getMiddleName());
    if(personEntity.isEmpty()){
    throw new DataNotFoundException(String.format("Человека %s %s %s нет в базе данных",
            personView.getFirstName(),
            personView.getLastName(),
            personView.getMiddleName()));
    }
    personEntity.forEach(a-> {
                if (!a.getBooks().isEmpty()) {
                    throw new CustomRuntimeException("Пользователь " +
                            "c ID" +a.getPersonId()+" не может быть удален, " +
                            "пока не вернет книги");
                }
                personRepo.delete(a);
            }
            );
    }

    @Override
    public PersonView borrowBook(Integer id, String name) {
        PersonEntity personEntity = personRepo.findById(id)
                .orElseThrow(()->
            new DataNotFoundException(
                "Не найдена информация по Человеку с ID "+id));
        BookEntity bookEntity = bookRepo.findByName(name)
                .orElseThrow(()->
                new DataNotFoundException(
                "Книги с названием "+name+" нет в библиотеке"));

        if(!bookEntity.getPersons().isEmpty()){
            throw new CustomRuntimeException("Книга уже взята из библиотеки");
        }
        if(personEntity.getBooks().stream().anyMatch(a->
                LocalDateTime.now().isAfter(a.getReturned()))){
            throw new CustomRuntimeException(
                    "У Человека с ID "+id+" есть просроченная книга");
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
        PersonEntity personEntity = personRepo.findById(id).orElseThrow(()->
                new DataNotFoundException(
                        "Не найдена информация по Человеку с ID "+id));
        BookEntity bookEntity = bookRepo.findByName(name).orElseThrow(()->
                new DataNotFoundException(
                        "Книги с названием "+name+" нет в библиотеке"));
        if(bookEntity.getPersons().isEmpty()){
            throw new CustomRuntimeException("Книга находится в библиотеке");
        }
        if(personEntity.getBooks().stream()
                .noneMatch(a->a.getBook().getName().equals(name))){
            throw new CustomRuntimeException(
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
