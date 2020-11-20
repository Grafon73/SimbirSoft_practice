package ru.simbirsoft.homework.author.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.homework.book.model.BookEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Автор
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "author")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    Integer authorId;

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

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
            cascade={CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "author",
            orphanRemoval = true)
   private Set<BookEntity> books;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    public void setBooks(Set<BookEntity> books) {
        if (this.books == null) {
            this.books = books;
        } else if (this.books != books) {
            this.books.clear();
            if (books != null) {
                this.books.addAll(books);
            }
        }
    }
}