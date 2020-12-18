package ru.simbirsoft.homework.userinterface.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.userinterface.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserRepoTest {

    @Autowired
    UserRepo userRepo;

    @Test
    void findByUsername() {
        Optional<User> user = userRepo.findByUsername("admin");
        assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo("admin");
    }
}