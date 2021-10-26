package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {

    public static void main( String[] args )
    {
        Program program = new Program();
        System.out.println("--START TEST  READ  MESSAGES---");
        program.testReadMessage();
        System.out.println("-- END  TEST  READ  MESSAGES---");
        System.out.println("--START TEST  CREATE   MESSAGES---");
        program.testCreateMessage();
        System.out.println("-- END  TEST  CREATE   MESSAGES---");
        System.out.println("--START TEST UPDATE MESSAGES---");
        program.testUpdateMessage();
        System.out.println("-- END  TEST UPDATE MESSAGES---");
    }

    private HikariDataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        HikariDataSource dataSource = null;
        try {
            config.setJdbcUrl( "jdbc:postgresql://localhost:5433/postgres" );
            config.setUsername( "postgres" );
            config.setPassword( "" );
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
            dataSource = new HikariDataSource( config );
            return dataSource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    private void testReadMessage(){
        HikariDataSource dataSource = hikariDataSource();
        Scanner sc = new Scanner(System.in);
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl( dataSource );

        System.out.println("Введите id сообщения или литеру для продолжения");
        while(true) {
            System.out.print("->");
            if (sc.hasNextInt()) {
                Long messageId = sc.nextLong();
                Optional<Message> optional = messagesRepository.findById(messageId);
                if (optional.isPresent()) {
                    Message message = optional.get();
                    System.out.println(message);
                }
                else {
                    System.out.println("Сообщение не найдено!");
                }
            } else {
                break;
            }
        }
        dataSource.close();
    }

    private void testCreateMessage() {
        HikariDataSource dataSource = hikariDataSource();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl( dataSource );
        User creator = new User(1L,"user", "user", new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom room = new Chatroom(1L, "room", creator, new ArrayList<>());
        Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
        messagesRepository.save(message);
        System.out.println(message.getId());
        dataSource.close();
    }

    private void testUpdateMessage() {
        HikariDataSource dataSource = hikariDataSource();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl( dataSource );
        Optional<Message> messageOptional = messagesRepository.findById(11L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("bye");
            message.setDateTime(null);
            messagesRepository.update(message);
        } else {
            System.err.println("Сообщение не найдено!");
        }
        dataSource.close();
    }
}
