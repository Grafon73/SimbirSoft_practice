package ru.simbirsoft.homework.borrow.service;

import ru.simbirsoft.homework.borrow.dto.BooksBorrow;

import java.util.List;


public interface BorrowService {
    List<BooksBorrow> addBorrow(BooksBorrow booksBorrow);
}
