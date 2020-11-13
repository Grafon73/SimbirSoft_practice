package ru.simbirsoft.homework.person.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Книга")
public class Person {

    @NotNull(message = "First Name cannot be null")
    @ApiModelProperty(value = "Имя", example = "Иван")
    @JsonView(Person.class)
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @ApiModelProperty(value = "Фамилия", example = "Иванов")
    @JsonView(Person.class)
    private String lastName;

    @NotNull(message = "Middle Name cannot be null")
    @ApiModelProperty(value = "Отчество", example = "Иванович")
    @JsonView(Person.class)
    private String middleName;

    @NotNull(message = "Birth Date cannot be null")
    @ApiModelProperty(value = "Дата рождения", example = "2008-05-30T04:20:12.121+00:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date birthDay;

}
