package ru.simbirsoft.homework.book.dto;


import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Человек")
public class Book {

    @NotNull(message = "Name cannot be null")
    @ApiModelProperty(value = "Название книги", example = "Удивительное приключение")
    @JsonView(Book.class)
    private String name;

    @NotNull(message = "Author cannot be null")
    @ApiModelProperty(value = "Автор книги", example = "Иванов Иван")
    @JsonView(Book.class)
    private String author;

    @NotNull(message = "Genre cannot be null")
    @ApiModelProperty(value = "Жанр книги", example = "Ужасы")
    private String genre;

}
