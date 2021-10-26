package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User>{
    Optional<User> findByLogin(String email);
    String findPasswordById(Long id);
    void savePassword(User user, String password);
    void saveByLogin(String login, String hashPassword);
}
