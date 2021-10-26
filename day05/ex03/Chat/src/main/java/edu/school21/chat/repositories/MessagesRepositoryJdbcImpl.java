package edu.school21.chat.repositories;

import edu.school21.chat.app.NotSavedSubEntityException;
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


    private final String SQL_UPDATE_MESSAGE =
            "UPDATE messages " +
                    "SET mText = ?, dateT = ? " +
                    "WHERE id=?";

    private final String SQL_SAVE_MESSAGE = "insert into messages (room, author, mText, dateT) " +
            "values (?, ?, ?, ?);";
    private final String SQL_LAST_MESSAGE_ID = "SELECT ID FROM messages ORDER BY ID DESC LIMIT 1";

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

    public void save(Message message) {
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                PreparedStatement pStatemenet = connection.prepareStatement(SQL_SAVE_MESSAGE);
        ) {
            ResultSet resultSet = statement.executeQuery(SQL_LAST_MESSAGE_ID);
            Long last_id = 0l;
            if (resultSet.next()) {
                last_id = resultSet.getLong("id");
            }

            pStatemenet.setLong(1, message.getRoom().getId());
            pStatemenet.setLong(2, message.getAuthor().getId());
            pStatemenet.setString(3, message.getText());
            pStatemenet.setTimestamp(4, Timestamp.valueOf(message.getDateTime()));
            if (pStatemenet.executeUpdate() == 1) {
                resultSet = statement.executeQuery(SQL_LAST_MESSAGE_ID);
                if (resultSet.next()) {
                    Long newLast_id = resultSet.getLong("id");
                    if (newLast_id != last_id) {
                        message.setId(newLast_id);
                        System.out.println("Сообщение добавлено.");
                        return;
                    }
                }
            }
            System.err.println("Сообщение не добавлено!");
        } catch (SQLException | NullPointerException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        }
    }

    @Override
    public void update(Message message) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pStatemenet = connection.prepareStatement(SQL_UPDATE_MESSAGE)
        ) {
            pStatemenet.setLong(3, message.getId());
            pStatemenet.setString(1, message.getText());
            if (message.getDateTime() == null) {
                pStatemenet.setNull(2, Types.TIMESTAMP);
            } else {
                pStatemenet.setTimestamp(2, Timestamp.valueOf(message.getDateTime()));
            }
            if (pStatemenet.executeUpdate() == 1) {
                System.out.println("Сообщение обновлено.");
                return;
            }
            System.err.println("Сообщение не обновлено!");
        } catch (SQLException | NullPointerException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        }
    }
}
