package ru.simbirsoft.homework.author.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.simbirsoft.homework.author.model.AuthorEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthorRepoTest {

    @Autowired
    AuthorRepo authorRepo;

    @Test
    public void findAuthorEntityByFirstNameAndLastNameAndMiddleName_Success(){
        String firstName = "Илья";
        String lastName = "Бугынин";
        String middleName =  "Витальевич";
        Optional<AuthorEntity> authorEntity =
                authorRepo.findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                        firstName,
                        lastName,
                        middleName
                );
        assertThat(authorEntity).isPresent();
        assertThat(authorEntity.get()).isNotNull();
        assertThat(authorEntity.get().getFirstName()).isEqualTo(firstName);
        assertThat(authorEntity.get().getLastName()).isEqualTo(lastName);
        assertThat(authorEntity.get().getMiddleName()).isEqualTo(middleName);
    }

    @Test
    public void findAuthorEntityByFirstNameAndLastNameAndMiddleName_Fail(){
        String firstName = "Илья";
        String lastName = "Бугынин";
        String middleName =  "ТЕСТ";
        Optional<AuthorEntity> authorEntity =
                authorRepo.findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                        firstName,
                        lastName,
                        middleName
                );
        assertThat(authorEntity).isNotPresent();
    }

}