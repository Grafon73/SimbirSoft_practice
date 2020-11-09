package ru.simbirsoft.homework.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@ApiModel(description = "Книга")
@JsonFilter("PersonFilter")
public class Person {

    @NotNull(message = "First Name cannot be null")
    @ApiModelProperty(value = "Имя", example = "Иван")
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @ApiModelProperty(value = "Фамилия", example = "Иванов")
    private String lastName;

    @NotNull(message = "Middle Name cannot be null")
    @ApiModelProperty(value = "Отчество", example = "Иванович")
    private String middleName;

    @NotNull(message = "Birth Date cannot be null")
    @ApiModelProperty(value = "Дата рождения", example = "2008-05-30T04:20:12.121+00:00")
    private Date birthDay;

}
