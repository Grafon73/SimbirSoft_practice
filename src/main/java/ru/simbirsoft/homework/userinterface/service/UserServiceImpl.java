package ru.simbirsoft.homework.userinterface.service;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.simbirsoft.homework.aop.annotations.LogException;
import ru.simbirsoft.homework.exception.DataNotFoundException;
import ru.simbirsoft.homework.mapper.MyCustomMapperForPerson;
import ru.simbirsoft.homework.person.model.PersonEntity;
import ru.simbirsoft.homework.person.view.PersonView;
import ru.simbirsoft.homework.userinterface.model.Role;
import ru.simbirsoft.homework.userinterface.model.User;
import ru.simbirsoft.homework.userinterface.repository.UserRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final Argon2PasswordEncoder passwordEncoder;
    private final MapperFactory mapperFactory;

    public boolean saveUser(User user) {
        Optional<User> userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB.isPresent()) {
            return false;
        }
        user.setRole(new Role(user.getUsername(), "ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepo.save(user);
        return true;
    }

    @LogException
    public PersonView loadByUsername(String username){
        Optional<User> userFromDB = userRepo.findByUsername(username);
        User user = userFromDB.orElseThrow(()->
                new DataNotFoundException("Пользователь не найден"));
        mapperFactory.classMap(PersonEntity.class, PersonView.class)
                .customize(new MyCustomMapperForPerson(mapperFactory))
                .register();
        return mapperFactory.getMapperFacade().map(user.getPersonEntity(), PersonView.class);
    }
}
