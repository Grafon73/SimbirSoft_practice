package ru.simbirsoft.homework.authorization.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String password;
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Role role;
}

