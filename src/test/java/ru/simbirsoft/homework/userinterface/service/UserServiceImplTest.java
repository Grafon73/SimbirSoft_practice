package ru.simbirsoft.homework.userinterface.service;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.simbirsoft.homework.exception.DataNotFoundException;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.person.model.PersonEntity;
import ru.simbirsoft.homework.person.view.PersonView;
import ru.simbirsoft.homework.userinterface.model.User;
import ru.simbirsoft.homework.userinterface.repository.UserRepo;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepo userRepo;
    @Mock
    Argon2PasswordEncoder passwordEncoder;
    @Mock
    MapperFactory mapperFactory;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void saveUser_Success() {
        User user = new User();
        user.setUsername("Test");
        Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        assertTrue(userService.saveUser(user));
        Mockito.verify(userRepo, Mockito.times(1))
                .findByUsername(Mockito.anyString());
        Mockito.verify(userRepo, Mockito.times(1))
                .save(Mockito.any(User.class));
    }

    @Test
    void saveUser_UserAlreadyExist() {
        User user = new User();
        user.setUsername("Test");
        Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        assertFalse(userService.saveUser(user));
        Mockito.verify(userRepo, Mockito.times(1))
                .findByUsername(Mockito.anyString());
        Mockito.verify(userRepo, Mockito.times(0))
                .save(Mockito.any(User.class));
    }

    @Test
    void loadByUsername_Success() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setBooks(Collections.singleton(new LibraryCard()));
        User user = new User();
        user.setPersonEntity(personEntity);
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(mapperFactory.classMap(PersonEntity.class, PersonView.class))
                .thenReturn(customMapperFactory.classMap(PersonEntity.class, PersonView.class));
        Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        assertThat(userService.loadByUsername("Test")).isInstanceOf(PersonView.class);
        Mockito.verify(userRepo, Mockito.times(1))
                .findByUsername(Mockito.anyString());
    }

    @Test
    void loadByUsername_UserNotFound() {
        Mockito.when(userRepo.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, ()-> userService.loadByUsername("Test"));
        Mockito.verify(userRepo, Mockito.times(1))
                .findByUsername(Mockito.anyString());
    }
}