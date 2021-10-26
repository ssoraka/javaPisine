package edu.school21.service.repositories;

import edu.school21.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {
	private final DataSource dataSource;

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

	public UsersRepositoryJdbcImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<User> findAll() {
		List<User> userList = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
			while (resultSet.next())
			{
				Long id = resultSet.getLong("id");
				String email = resultSet.getString("email");
				User user = new User(id, email);
				userList.add(user);
			}
			return userList;
		} catch (SQLException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
		return userList;
	}

	@Override
	public Optional<User> findById(Long id) {
		Optional<User> userOptional = Optional.empty();
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next())
			{
				String email = resultSet.getString("email");
				User user = new User(id, email);
				userOptional = Optional.of(user);
			}
			return  userOptional;
		} catch (SQLException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
		return userOptional;
	}

	@Override
	public void save(User user) {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_SAVE);
			statement.setLong(1, user.getId());
			statement.setString(2, user.getEmail());
			if (statement.executeUpdate() == 0) {
				throw new IllegalAccessException("Record was not save!");
			}
		} catch (SQLException | IllegalAccessException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
	}

	@Override
	public void update(User user) {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
			statement.setLong(2, user.getId());
			statement.setString(1, user.getEmail());
			if (statement.executeUpdate() == 0) {
				throw new IllegalAccessException("Record was not update!");
			}
		} catch (SQLException | IllegalAccessException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
	}

	@Override
	public void delete(Long id) {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
			statement.setLong(1, id);
			if (statement.executeUpdate() == 0) {
				throw new IllegalAccessException("Record was not delete!");
			}
		} catch (SQLException | IllegalAccessException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
	}

	@Override
	public Optional<User> findByEmail(String email) {
		Optional<User> userOptional = Optional.empty();
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next())
			{
				Long id = resultSet.getLong("id");
				User user = new User(id, email);
				userOptional = Optional.of(user);
			}
			return  userOptional;
		} catch (SQLException e) {
			try {
				throw new IllegalAccessException(e.getMessage());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
		}
		return userOptional;
	}

}
