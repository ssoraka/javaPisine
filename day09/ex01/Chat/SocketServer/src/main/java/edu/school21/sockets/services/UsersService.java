package edu.school21.sockets.services;

public interface UsersService {
	boolean signUp(String login, String password);
	boolean signIn(String login);
	boolean signIn(String login, String password);
}
