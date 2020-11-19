package ru.simbirsoft.homework.exception;

public class DataNotFound extends RuntimeException{
    public DataNotFound(String data) {
        super(String.format("Не найдены данные для %s", data));
    }
}
