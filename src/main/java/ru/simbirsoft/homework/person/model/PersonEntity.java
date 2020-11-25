package ru.simbirsoft.homework.person.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Человек
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "person")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    Integer personId;


    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    /**
     * Дата рождения
     */
    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * Имя
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Фамилия
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Отчество
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Время создания записи
     */
    @Column(name = "create_date",columnDefinition = "timestamp with time zone")
    private LocalDateTime created;

    /**
     * Время изменения записи
     */
    @Column(name = "update_date",columnDefinition = "timestamp with time zone")
    private LocalDateTime updated;

    @OneToMany(
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
            )
    private Set<LibraryCard> books;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();

    }
    @PreUpdate
    public void onUpdate() {
       updated = LocalDateTime.now();
    }

    public void addBook(BookEntity book) {
        LibraryCard card = new LibraryCard(book,this);
        books.add(card);
        book.getPersons().add(card);
    }

    public void removeBook(BookEntity book) {
        for (Iterator<LibraryCard> iterator = books.iterator();
             iterator.hasNext(); ) {
            LibraryCard libraryCard = iterator.next();
            if (libraryCard.getBook().equals(book) &&
                    libraryCard.getPerson().equals(this)) {
                iterator.remove();
                libraryCard.getBook().getPersons().remove(libraryCard);
                libraryCard.setPerson(null);
                libraryCard.setBook(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(middleName, that.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthDate, firstName, lastName, middleName);
    }
}
