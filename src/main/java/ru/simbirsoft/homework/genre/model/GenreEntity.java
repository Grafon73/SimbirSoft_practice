package ru.simbirsoft.homework.genre.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.homework.book.model.BookEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Set;

/**
 * Книга
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "genre")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    Integer genreId;

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    /**
     * Название жанра
     */
    @Column(name = "genre_name", nullable = false)
    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "genres",
            cascade = {CascadeType.MERGE})
    private Set<BookEntity> books;

}