package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository{
	Optional<User> findByLogin(String login);
	String findPasswordById(Long id);
	void savePassword(User user, String password);
	void saveByLogin(String login, String password);
}
