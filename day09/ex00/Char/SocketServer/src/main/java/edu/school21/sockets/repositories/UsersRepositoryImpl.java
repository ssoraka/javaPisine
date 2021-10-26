package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository{

    private final JdbcTemplate template;

    private final String SQL_FIND_ALL =
            "SELECT * FROM users";

    private final String SQL_FIND_BY_ID =
            "SELECT * FROM users WHERE id = ?";

    private final String SQL_UPDATE =
            "UPDATE users SET email = ? WHERE id = ?";

    private final String SQL_SAVE =
            "INSERT INTO users (email) VALUES (?)";

    private final String SQL_DELETE =
            "DELETE FROM users WHERE id = ?";

    private final String SQL_FIND_BY_LOGIN =
            "SELECT * FROM users WHERE login = ?";

    private final String SQL_FIND_PASSWORD_BY_ID =
            "SELECT password FROM users WHERE id = ?";

    private final String SQL_SAVE_PASSWORD =
            "UPDATE users SET password = ? WHERE id = ?";

    private final String SQL_CREATE =
            "INSERT INTO users (login, password) VALUES (?, ?)";

    public UsersRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("login"));


    private final RowMapper<String> passwordRowMapper = (rs, rowNum) ->
            rs.getString("password");

    @Override
    public Optional<User> findByLogin(String login) {
        List<User> userList = template.query(SQL_FIND_BY_LOGIN, userRowMapper, login);
        if (userList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(userList.get(0));
        }
    }

    @Override
    public String findPasswordById(Long id) {
        List<String> passwordList = template.query(SQL_FIND_PASSWORD_BY_ID, passwordRowMapper, id);
        if (passwordList.isEmpty()) {
            return "";
        } else {
            return passwordList.get(0);
        }
    }

    @Override
    public void savePassword(User user, String password) {
        if (template.update(SQL_SAVE_PASSWORD, password, user.getId()) != 1)
            throw new IllegalStateException("Error database update!");
    }

    @Override
    public void saveByLogin(String login, String password) {
        if (template.update(SQL_CREATE, login, password) != 1)
            throw new IllegalStateException("Error database insert!");
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
        if (template.update(SQL_SAVE, entity.getEmail()) != 1)
            throw new IllegalStateException("Error database save!");
    }

    @Override
    public void update(User entity) {
        if (template.update(SQL_UPDATE, entity.getEmail(), entity.getId()) != 1)
            throw new IllegalStateException("Error database update!");
    }

    @Override
    public void delete(Long id) {
        if (template.update(SQL_DELETE, id) != 1)
            throw new IllegalStateException("Error database delete!");
    }
}
