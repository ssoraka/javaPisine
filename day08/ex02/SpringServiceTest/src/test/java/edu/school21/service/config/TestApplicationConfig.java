package edu.school21.service.config;

import edu.school21.service.repositories.UsersRepository;
import edu.school21.service.repositories.UsersRepositoryJdbcImpl;
import edu.school21.service.repositories.UsersRepositoryJdbcTemplateImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "edu.school21.service")
public class TestApplicationConfig {
	private EmbeddedDatabase dataSource;

	private final String SQL_DROP_TABLE =
			"DROP TABLE users IF EXISTS; ";

	private final String SQL_CREATE_TABLE =
			"CREATE TABLE users (id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, email VARCHAR(150), password VARCHAR(32))";

	@Bean
	public DataSource hsqlDataSource() {
		try {
			dataSource = new EmbeddedDatabaseBuilder().build();
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_DROP_TABLE);
			statement.execute();
			statement = connection.prepareStatement(SQL_CREATE_TABLE);
			statement.execute();
			return dataSource;
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	@Bean
	public UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate() {
		return new UsersRepositoryJdbcTemplateImpl(hsqlDataSource());
	}

	@Bean
	public UsersRepositoryJdbcImpl usersRepositoryJdbc() {
		return new UsersRepositoryJdbcImpl(hsqlDataSource());
	}



}
