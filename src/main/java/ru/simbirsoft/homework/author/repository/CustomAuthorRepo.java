package ru.simbirsoft.homework.author.repository;

import ru.simbirsoft.homework.author.model.AuthorEntity;

import java.util.Date;
import java.util.List;

public interface CustomAuthorRepo {
    List<AuthorEntity> findByFIOAndBirthDate(String firstName, String lastName, String middleName, Date from, Date to);

}
