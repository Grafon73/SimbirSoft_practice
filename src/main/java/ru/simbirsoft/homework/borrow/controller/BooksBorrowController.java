package ru.simbirsoft.homework.borrow.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.homework.borrow.dto.BooksBorrow;
import ru.simbirsoft.homework.borrow.service.BorrowService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(value = "BooksBorrowController", description = "Регистрация выдачи книг")
@RestController
@RequestMapping(value = "/borrow", produces = APPLICATION_JSON_VALUE)
public class BooksBorrowController {

    private final BorrowService borrowService;

    public BooksBorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/")
    @ApiOperation(value = "Добавить человека в список", httpMethod = "POST")
    public List<BooksBorrow> addBorrow(@Valid  @RequestBody BooksBorrow booksBorrow){
          return borrowService.addBorrow(booksBorrow);
    }
}
