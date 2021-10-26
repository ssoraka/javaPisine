package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public boolean signUp(String login, String password) {
        final Optional<User> optionalUser = repository.findByLogin(login);
        if (optionalUser.isPresent()) {
            return false;
        }
        String hash = encoder.encode(password);
        repository.saveByLogin(login, hash);
        return true;
    }
}
