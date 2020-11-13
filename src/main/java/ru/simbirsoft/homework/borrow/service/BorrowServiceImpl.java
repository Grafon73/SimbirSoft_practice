package ru.simbirsoft.homework.borrow.service;

import org.springframework.stereotype.Service;
import ru.simbirsoft.homework.borrow.dto.BooksBorrow;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService{

    static List<BooksBorrow> borrowList = new ArrayList<>();

    @Override
    public List<BooksBorrow> addBorrow(BooksBorrow booksBorrow) {
        borrowList.add(booksBorrow);
        return  borrowList;
    }
}
