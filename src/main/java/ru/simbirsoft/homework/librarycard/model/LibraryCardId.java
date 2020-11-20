package ru.simbirsoft.homework.librarycard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryCardId implements Serializable {

    @Column(name = "person_person_id")
    private Integer personId;
    @Column(name = "book_book_id")
    private Integer bookId;


}
