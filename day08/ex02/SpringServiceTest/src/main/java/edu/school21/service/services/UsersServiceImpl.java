package edu.school21.service.services;

import edu.school21.service.models.User;
import edu.school21.service.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService{
	@Autowired
	@Qualifier("usersRepositoryJdbcTemplate")
	private UsersRepository repository;
	@Override
	public String signUp(String email) {
		Optional<User> optionalUser = repository.findByEmail(email);
		String password;
		if (optionalUser.isPresent()) {
			password = repository.findPasswordById(optionalUser.get().getId());
		} else {
			password = passwordGenerate();
			repository.saveByEmail(email, password);
		}
		return password;
	}

	private String passwordGenerate() {
		int lenPassword = (int) (Math.random() * 24) + 8;
		String password = "";
		for (int i = 0; i < lenPassword; i++) {
			password += (char) (Math.random() * 95 + 32);
		}
		return password;
	}
}
