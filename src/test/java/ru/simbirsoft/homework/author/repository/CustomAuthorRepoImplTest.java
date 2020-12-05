package ru.simbirsoft.homework.author.repository;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.simbirsoft.homework.author.model.AuthorEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
class CustomAuthorRepoImplTest {

    @Autowired
    CustomAuthorRepo customAuthorRepo;


    @Test
    @SneakyThrows
    void findByFIOAndBirthDate_Success() {
        String firstName = "Илья";
        String lastName = "Бугынин";
        String middleName =  "Витальевич";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date from = format.parse( "2018-12-31" );
        Date to = format.parse( "2020-12-31" );
        List<AuthorEntity> authorEntity = customAuthorRepo.findByFIOAndBirthDate(firstName,lastName,middleName,from,to);
        assertThat(authorEntity).isNotEmpty();
        assertThat(authorEntity.get(0).getFirstName()).isEqualTo(firstName);
        assertThat(authorEntity.get(0).getLastName()).isEqualTo(lastName);
        assertThat(authorEntity.get(0).getBirthDate()).isBetween(from,to);
    }

    @Test
    @SneakyThrows
    void findByFIOAndBirthDate_Fail() {
        String firstName = "Илья";
        String lastName = "Бугынин";
        String middleName =  "Витальевич";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date from = format.parse( "2011-12-31" );
        Date to = format.parse( "2012-12-31" );
        List<AuthorEntity> authorEntity = customAuthorRepo.findByFIOAndBirthDate(firstName,lastName,middleName,from,to);
        assertThat(authorEntity).isEmpty();
    }


}