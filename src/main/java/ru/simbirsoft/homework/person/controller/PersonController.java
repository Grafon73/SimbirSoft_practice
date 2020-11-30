package ru.simbirsoft.homework.person.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.person.service.PersonService;
import ru.simbirsoft.homework.person.view.PersonView;
import ru.simbirsoft.homework.person.view.PersonViewWithoutBooks;
import ru.simbirsoft.homework.person.view.PersonViewWithoutDateAndBooks;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "PersonController", description = "Управление информацией о людях")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/person", produces = APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonService personService;

   @PostMapping("/")
   @Secured(value = {"ROLE_ADMIN"})
   @ApiOperation(value = "Добавить человека в список", httpMethod = "POST")
   public ResponseEntity<PersonViewWithoutBooks> addPerson(@Valid @RequestBody PersonViewWithoutBooks personView){
      personService.addPerson(personView);
      return ResponseEntity.ok(personView);
   }

   @PutMapping("/")
   @Secured(value = {"ROLE_ADMIN"})
   @ApiOperation(value = "Изменить ифнормацию о человеке", httpMethod = "PUT")
   public ResponseEntity<PersonViewWithoutBooks> editPerson(
           @RequestParam Integer id,
           @Valid @RequestBody PersonViewWithoutBooks personView){
      return ResponseEntity.ok(personService.editPerson(id,personView));
   }

   @DeleteMapping("/{id}")
   @Secured(value = {"ROLE_ADMIN"})
   @ApiOperation(value = "Удалить человека из списка по ID", httpMethod = "DELETE")
   public ResponseEntity<String> removePerson(@PathVariable Integer id) {
      personService.removePerson(id);
      return ResponseEntity.ok("OK");
   }

   @DeleteMapping("/")
   @Secured(value = {"ROLE_ADMIN"})
   @ApiOperation(value = "Удалить человека из списка по ФИО", httpMethod = "DELETE")
   public ResponseEntity<String> removePerson(
           @Valid @RequestBody PersonViewWithoutDateAndBooks personView) {
      personService.removePerson(personView);
      return ResponseEntity.ok("OK");
   }
   @GetMapping("/{id}")
   @ApiOperation(value = "Получить список книг человека", httpMethod = "GET")
   public ResponseEntity<List<BookView>> getById(@PathVariable Integer id){
      return ResponseEntity.ok(personService.getPersonsBooks(id));
   }

   @PutMapping("/borrow")
   @Secured(value = {"ROLE_ADMIN"})
   @ApiOperation(value = "Добавить книгу в список книг человека", httpMethod = "PUT")
   public ResponseEntity<PersonView> borrowBook(@RequestParam Integer personId,
                                                @RequestParam String bookName){
      return ResponseEntity.ok(personService.borrowBook(personId,bookName));
   }

   @PutMapping("/return")
   @Secured(value = {"ROLE_ADMIN"})
   @ApiOperation(value = "Убрать книгу из списка книг человека", httpMethod = "PUT")
   public ResponseEntity<PersonView> returnBook(@RequestParam Integer personId,
                                                 @RequestParam String bookName){
      return ResponseEntity.ok(personService.returnBook(personId,bookName));
   }

    @GetMapping("/")
    @Secured(value = {"ROLE_ADMIN"})
    @ApiOperation(value = "*ДЛЯ ТЕСТОВ* Получить список всех людей", httpMethod = "GET")
    public ResponseEntity<List<PersonView>> getAll(){
        return ResponseEntity.ok(personService.getAllPersons());
    }


}
