package ru.simbirsoft.homework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;


@ApiModel(description = "Выдача книг")
public class BooksBorrow {

    @Valid
    private Person person;

    @NotNull(message = "Date cannot be null")
    @ApiModelProperty(value = "Дата взятия книги", example = "2020-10-20 12:49:23.123+03:00")
    private ZonedDateTime zonedDateTime;


    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSXXX")
    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }
}
