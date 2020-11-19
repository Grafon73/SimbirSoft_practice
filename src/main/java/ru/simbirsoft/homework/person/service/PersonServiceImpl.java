package ru.simbirsoft.homework.person.service;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.exception.DataNotFound;
import ru.simbirsoft.homework.exception.MyCustomException;
import ru.simbirsoft.homework.person.model.PersonEntity;
import ru.simbirsoft.homework.person.repository.PersonRepo;
import ru.simbirsoft.homework.person.view.PersonView;
import ru.simbirsoft.homework.person.view.PersonViewWithoutBooks;
import ru.simbirsoft.homework.person.view.PersonViewWithoutDateAndBooks;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{

    private final PersonRepo personRepo;
    private final MapperFactory mapperFactory;
    private final BookRepo bookRepo;

    @Autowired
    public PersonServiceImpl(PersonRepo personRepo, MapperFactory mapperFactory, BookRepo bookRepo) {
        this.personRepo = personRepo;
        this.mapperFactory = mapperFactory;
        this.bookRepo = bookRepo;
    }

    @Override
    public List<PersonView> getAllPersons() {

        return mapperFactory.getMapperFacade()
                .mapAsList(personRepo.findAll(),PersonView.class);
    }

    @Override
    public List<BookView> getPersonsBooks(Integer id) {
        Optional<PersonEntity> personEntityOptional = personRepo.findById(id);
       if(!personEntityOptional.isPresent()){
           throw new DataNotFound(String.format("Человека с ID %d",id));
       }
        return mapperFactory.getMapperFacade()
                .mapAsList(personEntityOptional.get().getBooks(),BookView.class);
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
        if(bookEntity.getPerson()!=null){
            throw new MyCustomException("Книга уже взята из библиотеки");
        }
        PersonEntity personEntity = personEntityOptional.get();
        bookEntity.setPerson(personEntity);
        personEntity.getBooks().add(bookEntity);
        personRepo.save(personEntity);
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
        if(bookEntity.getPerson()==null){
            throw new MyCustomException("Книга находится в библиотеке");
        }

        PersonEntity personEntity = personEntityOptional.get();
        if(personEntity.getBooks().stream().noneMatch(a->a.getName().equals(name))){
            throw new MyCustomException(
                    String.format("Книга '%s' не у человека c ID %d", name, id));
        }

        bookEntity.setPerson(null);
        personEntity.getBooks().remove(bookEntity);
        personRepo.save(personEntity);
        return mapperFactory.getMapperFacade().map(personEntity, PersonView.class);
    }
}
