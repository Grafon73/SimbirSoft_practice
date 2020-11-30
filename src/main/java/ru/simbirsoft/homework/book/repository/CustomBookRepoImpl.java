package ru.simbirsoft.homework.book.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.genre.model.GenreEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class CustomBookRepoImpl implements CustomBookRepo {

    private final EntityManager entityManager;

    @Override
    public List<BookEntity> findByGenreAndDate(String genre, Integer year, Attribute attribute) {
        CriteriaQuery<BookEntity> criteria = buildCriteriaForFind(genre,year,attribute);
        TypedQuery<BookEntity> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }

    private CriteriaQuery<BookEntity> buildCriteriaForFind(String genre, Integer year, Attribute attribute) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteria = builder.createQuery(BookEntity.class);
        criteria.distinct(true);
        Root<BookEntity> obj = criteria.from(BookEntity.class);
        Join<BookEntity, GenreEntity> join = obj.join("genres");
        Predicate predicate = builder.conjunction();
        if(genre!=null && obj.get("genres")!=null){
            predicate = builder.and(predicate, builder.equal(join.get("name"), genre));
        }
        if(year !=null) {
            if(attribute == Attribute.EQUALS){
                predicate = builder.and(predicate,
                        builder.equal(builder.function("YEAR", Integer.class,
                                obj.get("publicated") ),year));
            }
            if(attribute == Attribute.AFTER){
                predicate = builder.and(predicate,
                        builder.greaterThan(builder.function("YEAR", Integer.class,
                                obj.get("publicated") ),year));
            }
            if(attribute == Attribute.BEFORE){
                predicate = builder.and(predicate,
                        builder.lessThan(builder.function("YEAR", Integer.class,
                                obj.get("publicated") ),year));
            }
        }
        criteria.where(predicate);
        return criteria;
    }

}
