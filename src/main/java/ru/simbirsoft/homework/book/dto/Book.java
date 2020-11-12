package ru.simbirsoft.homework.book.dto;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String genre;

}
