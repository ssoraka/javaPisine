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
		Optional<User> optionalUser = repository.findByLogin(login);
		if (optionalUser.isPresent()) {
			return false;
		}
		repository.saveByLogin(login, encoder.encode(password));
		return true;
	}

	@Override
	public boolean signIn(String login) {
		Optional<User> optionalUser = repository.findByLogin(login);
		return optionalUser.isPresent();
	}

	@Override
	public boolean signIn(String login, String password) {
		Optional<User> optionalUser = repository.findByLogin(login);
		return optionalUser.filter(user -> encoder.matches(password, repository.findPasswordById(user.getId()))).isPresent();
	}
}
