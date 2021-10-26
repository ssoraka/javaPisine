package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class MessageRepositoryImpl implements MessageRepository {
	private final JdbcTemplate template;

	private final String SQL_SAVE = "insert into messages (author, mtext, dateT) " +
			"values (?, ?, ?);";

	public MessageRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public Optional findById(Long id) {
		return Optional.empty();
	}

	@Override
	public List findAll() {
		return null;
	}

	@Override
	public void save(Object entity) {
		Message message = (Message)entity;
		if (template.update(SQL_SAVE,
							message.getAuthor().getId(),
							message.getText(),
							Timestamp.valueOf(message.getDateTime())) != 1)
			throw new IllegalStateException("Error database save!");
	}

	@Override
	public void update(Object entity) {

	}

	@Override
	public void delete(Long id) {

	}
}
