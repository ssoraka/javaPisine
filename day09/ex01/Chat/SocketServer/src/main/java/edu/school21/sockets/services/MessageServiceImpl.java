package edu.school21.sockets.services;


import edu.school21.sockets.models.User;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class MessageServiceImpl implements MessageService{
	@Autowired
	private UsersRepository userRrepository;
	@Autowired
	private MessageRepository messageRepository;

	@Override
	public void saveMessage(String login, String text) {
		Optional<User> optionalUser = userRrepository.findByLogin(login);
		if (optionalUser.isPresent()) {
			Message message = new Message(0L, optionalUser.get(), text, LocalDateTime.now());
			messageRepository.save(message);
		} else {
			System.err.println("Database access error!");
		}
	}
}
