package ru.simbirsoft.homework.librarycard.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.librarycard.service.LibraryService;
import ru.simbirsoft.homework.librarycard.view.LibraryView;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;



@Api(value = "LibraryController", description = "Управление записями о взятых книгах")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/library", produces = APPLICATION_JSON_VALUE)
public class LibraryController {

    private final LibraryService libraryService;


    @PutMapping("/")
    @ApiOperation(value = "Продление срока возврата книги", httpMethod = "PUT")
    public ResponseEntity<LibraryView> addDays(@RequestParam Integer bookID,
                                               @RequestParam Integer personID,
                                               @RequestParam Integer days) {

        return ResponseEntity.ok(libraryService.addDays(bookID,personID,days));
    }

    @GetMapping("/")
    @ApiOperation(value = "Список должников", httpMethod = "GET")
    public ResponseEntity<List<LibraryView>> getDebtors() {
        return ResponseEntity.ok(libraryService.getDebtors());
    }

}