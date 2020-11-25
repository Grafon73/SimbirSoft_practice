package ru.simbirsoft.homework.librarycard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.person.model.PersonEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "library_card")
public class LibraryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer cardId;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    private PersonEntity person;

    public LibraryCard(BookEntity book, PersonEntity person) {
        this.book = book;
        this.person = person;
    }

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    /**
     * Время создания записи
     */
    @Column(name = "create_date",columnDefinition = "timestamp with time zone")
    private LocalDateTime created;

    /**
     * Время создания записи
     */
    @Column(name = "return_date",columnDefinition = "timestamp with time zone")
    private LocalDateTime returned;

    /**
     * Время изменения записи
     */
    @Column(name = "update_date",columnDefinition = "timestamp with time zone")
    private LocalDateTime updated;

    /**
     * Находится ли книга в библиотеке
     */
    @Column(name = "in_library")
    private boolean inLibrary;


    @PrePersist
    protected void onCreate() {
       LocalDateTime dateTime = LocalDateTime.now();
        created = dateTime;
        returned = dateTime.plusDays(7);
    }
    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    public void addDays(Integer days){
       returned= returned.plusDays(days);
    }

    public void setInLibrary(boolean inLibrary) {
        returned = LocalDateTime.now();
        this.inLibrary = inLibrary;
    }
}
