package ru.simbirsoft.homework.dto;


import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ApiModel(description = "Человек")
@JsonFilter("BookFilter")
public class Book {

    @NotNull(message = "Name cannot be null")
    @ApiModelProperty(value = "Название книги", example = "Удивительное приключение")
    private String name;

    @NotNull(message = "Author cannot be null")
    @ApiModelProperty(value = "Автор книги", example = "Иванов Иван")
    private String author;

    @NotNull(message = "Genre cannot be null")
    @ApiModelProperty(value = "Жанр книги", example = "Ужасы")
    private String genre;
}
