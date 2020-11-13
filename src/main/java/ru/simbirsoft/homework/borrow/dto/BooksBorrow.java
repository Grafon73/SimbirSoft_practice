package ru.simbirsoft.homework.borrow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.book.dto.Book;
import ru.simbirsoft.homework.person.dto.Person;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;


@ApiModel(description = "Выдача книг")
@Getter
@Setter
public class BooksBorrow {

    @Valid
    private Person person;

    @Valid
    private Book book;

    @NotNull(message = "Date cannot be null")
    @ApiModelProperty(value = "Дата взятия книги", example = "2020-10-20 12:49:23.123+03:00")
    private ZonedDateTime zonedDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSXXX")
    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }
}
