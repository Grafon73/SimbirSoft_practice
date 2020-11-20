package ru.simbirsoft.homework.librarycard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.person.model.PersonEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "library_card")
public class LibraryCard {

    @EmbeddedId
    private LibraryCardId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    private PersonEntity person;

    public LibraryCard(BookEntity book, PersonEntity person) {
        this.id = new LibraryCardId(book.getBookId(),person.getPersonId());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryCard that = (LibraryCard) o;
        return Objects.equals(book, that.book) &&
                Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, person);
    }
}
