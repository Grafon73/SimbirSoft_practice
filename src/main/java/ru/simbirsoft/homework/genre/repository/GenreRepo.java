package ru.simbirsoft.homework.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.simbirsoft.homework.genre.model.GenreEntity;

import java.util.Optional;

public interface GenreRepo extends JpaRepository<GenreEntity,Integer> {

    @Query("select distinct count (g) from BookEntity g inner join g.genres b  where b.name=:genre")
    int countAllBooksByGenre(@Param("genre")String genre);
    void removeAllByBooksIsNull();
    Optional<GenreEntity> findByName(String name);

}
