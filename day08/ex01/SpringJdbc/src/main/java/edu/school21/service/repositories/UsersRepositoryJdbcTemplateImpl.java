package edu.school21.service.repositories;

import edu.school21.service.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
	private final JdbcTemplate template;

	private final String SQL_FIND_ALL =
			"SELECT * FROM users";

	private final String SQL_FIND_BY_ID =
			"SELECT * FROM users WHERE id = ?";

	private final String SQL_UPDATE =
			"UPDATE users SET email = ? WHERE id = ?";

	private final String SQL_SAVE =
			"INSERT INTO users (emails) VALUES (?)";

	private final String SQL_DELETE =
			"DELETE FROM users WHERE id = ?";

	private final String SQL_FIND_BY_EMAIL =
			"SELECT * FROM users WHERE email = ?";

	public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	private RowMapper<User> userRowMapper = (rs, rowNum) ->
		new User(rs.getLong("id"), rs.getString("email"));

	@Override
	public Optional<User> findByEmail(String email) {
		List<User> userList = template.query(SQL_FIND_BY_EMAIL, userRowMapper, email);
		if (userList.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(userList.get(0));
		}
	}

	@Override
	public Optional<User> findById(Long id) {
		List<User> userList = template.query(SQL_FIND_BY_ID, userRowMapper, id);
		if (userList.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(userList.get(0));
		}
	}

	@Override
	public List<User> findAll() {
		return template.query(SQL_FIND_ALL, userRowMapper);
	}

	@Override
	public void save(User entity) {

	}

	@Override
	public void update(User entity) {

	}

	@Override
	public void delete(Long id) {

	}
}
