package ru.simbirsoft.homework.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Authorities")
public class Role{
    @Id
    private String username;
    private String authority;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    public Role(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }
}