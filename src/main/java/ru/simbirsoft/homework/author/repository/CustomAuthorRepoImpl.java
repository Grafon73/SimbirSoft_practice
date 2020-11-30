package ru.simbirsoft.homework.author.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.author.model.AuthorEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class CustomAuthorRepoImpl implements CustomAuthorRepo {

    private final EntityManager entityManager;


    @Override
    public List<AuthorEntity> findByFIOAndBirthDate(String firstName, String lastName, String middleName, Date from, Date to) {
        CriteriaQuery<AuthorEntity> criteria = buildCriteriaForFind(firstName,lastName,middleName,from,to);
        TypedQuery<AuthorEntity> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }


    private CriteriaQuery<AuthorEntity> buildCriteriaForFind(String firstName, String lastName, String middleName, Date from, Date to) {
        Date localDateTimeFrom=null;
        Date localDateTimeTo=null;
        if(from!=null){
            localDateTimeFrom = from;
       }
        if(to!=null){
            localDateTimeTo = to;
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuthorEntity> criteria = builder.createQuery(AuthorEntity.class);
        Root<AuthorEntity> obj = criteria.from(AuthorEntity.class);
        Predicate predicate = builder.conjunction();
        if(firstName !=null){
            predicate = builder.and(predicate, builder.equal(obj.get("firstName"), firstName));
        }
        if(lastName !=null){
            predicate = builder.and(predicate, builder.equal(obj.get("lastName"), lastName));
        }
        if(middleName !=null){
            predicate = builder.and(predicate, builder.equal(obj.get("middleName"), middleName));
        }
        if(from != null && to != null){
            predicate = builder.and(predicate, builder.greaterThanOrEqualTo(obj.get("birthDate"), localDateTimeFrom));
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(obj.get("birthDate"), localDateTimeTo));
        }else if(from != null){
            predicate = builder.and(predicate, builder.greaterThanOrEqualTo(obj.get("birthDate"), localDateTimeFrom));
        }else if(to !=null){
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(obj.get("birthDate"), localDateTimeTo));
        }
        criteria.where(predicate);
        return criteria;
    }

}
