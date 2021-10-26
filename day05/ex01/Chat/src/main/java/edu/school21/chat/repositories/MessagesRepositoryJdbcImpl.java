package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    DataSource dataSource;

    private final String SQL_SELECT_BY_ID =
            "SELECT * , users.user_id as id_user, chatrooms.id as id_room " +
                    "FROM messages " +
                    "	LEFT JOIN users ON messages.author = users.user_id " +
                    "	LEFT JOIN chatrooms ON messages.room = chatrooms.id " +
                    "WHERE messages.id=?";

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statemenet = connection.prepareStatement(SQL_SELECT_BY_ID)
        ) {
            statemenet.setLong(1, id);
            ResultSet resultSet = statemenet.executeQuery();
            if (resultSet.next()) {
                Long user_id = resultSet.getLong("user_id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User author = new User(user_id, login, password);
                Long room_id = resultSet.getLong("id_room");
                String name = resultSet.getString("name");
                Chatroom room = new Chatroom(room_id, name);
                String text = resultSet.getString("mText");
                Object obj = resultSet.getObject("dateT");
                LocalDateTime dateTime;
                if (obj == null)
                    dateTime = null;
                else
                    dateTime = resultSet.getTimestamp("dateT").toLocalDateTime();
                return Optional.of(new Message(id, author, room, text, dateTime));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
