package ru.simbirsoft.homework.userinterface.service;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.userinterface.model.User;
import ru.simbirsoft.homework.userinterface.repository.UserRepo;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private UserRepo userRepository;
    private LoginAttemptService loginAttemptService;
    private HttpServletRequest request;
    private final Random randomInteger = new Random();

    @Autowired
    public MyUserDetailsService(UserRepo userRepository, LoginAttemptService loginAttemptService, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
        this.request = request;
    }


    public MyUserDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String name) throws UsernameNotFoundException {
        long start = System.currentTimeMillis();
        final String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }
        Optional<User> optionalUser = userRepository.findByUsername(name);

        if(!optionalUser.isPresent()){
            long finish = System.currentTimeMillis();
            while(finish-start < 100){
                finish = System.currentTimeMillis();
            }
            Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
            passwordEncoder.encode(getRandomString());
            throw new UsernameNotFoundException("No user found with username: " + name);
        }

        User user = optionalUser.get();

        long finish = System.currentTimeMillis();
        while(finish-start < 100){
            finish = System.currentTimeMillis();
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getAuthority())));
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    private String getRandomString(){
        RandomString randomString = new RandomString(
                randomInteger
                        .ints(5,10)
                        .findFirst()
                        .getAsInt());
        return randomString.nextString();
    }


}