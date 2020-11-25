package ru.simbirsoft.homework.book.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.homework.author.model.AuthorEntity;
import ru.simbirsoft.homework.genre.model.GenreEntity;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.person.model.PersonEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;



/**
 * Книга
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    Integer bookId;

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    /**
     * Название книги
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Дата поступления в бибилотеку
     */
    @Column(name = "admission_date")
    private LocalDateTime admited;

    /**
     * Дата поблукации
     */
    @Column(name = "publication_date")
    private LocalDateTime publicated;

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

    @ManyToMany(
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<GenreEntity> genres = new HashSet<>();

    @ManyToOne(
            fetch=FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "author_id",nullable = false)
    private AuthorEntity author;

    @OneToMany(
            mappedBy = "book"
    )
    private Set<LibraryCard> persons;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    public void removeGenre(GenreEntity genre) {
        this.genres.remove(genre);
        genre.getBooks().remove(this);
    }
    public void addGenre(GenreEntity genre) {
        this.genres.add(genre);
        genre.getBooks().add(this);
    }

    public void addPerson(PersonEntity person) {
        LibraryCard libraryCard = new LibraryCard(this, person);
        persons.add(libraryCard);
        person.getBooks().add(libraryCard);
    }

    public void removePerson(PersonEntity person) {
        for (Iterator<LibraryCard> iterator = persons.iterator();
             iterator.hasNext(); ) {
            LibraryCard libraryCard = iterator.next();

            if (libraryCard.getBook().equals(this) &&
                    libraryCard.getPerson().equals(person)) {
                iterator.remove();
                libraryCard.getPerson().getBooks().remove(libraryCard);
                libraryCard.setPerson(null);
                libraryCard.setBook(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
