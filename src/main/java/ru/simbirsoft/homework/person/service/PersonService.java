package ru.simbirsoft.homework.person.service;

import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.person.view.PersonView;
import ru.simbirsoft.homework.person.view.PersonViewWithoutBooks;
import ru.simbirsoft.homework.person.view.PersonViewWithoutDateAndBooks;

import java.util.List;


public interface PersonService {
    List<PersonView> getAllPersons();
    List<BookView> getPersonsBooks(Integer id);
    PersonViewWithoutBooks addPerson(PersonViewWithoutBooks personView);
    PersonViewWithoutBooks editPerson(Integer id, PersonViewWithoutBooks personView);
    void removePerson(Integer id);
    void removePerson(PersonViewWithoutDateAndBooks personView);
    PersonView borrowBook(Integer id, String name);
    PersonView returnBook(Integer id, String name);
}
