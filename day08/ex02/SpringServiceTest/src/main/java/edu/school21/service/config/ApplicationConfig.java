package edu.school21.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.service.repositories.UsersRepositoryJdbcImpl;
import edu.school21.service.repositories.UsersRepositoryJdbcTemplateImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("db.properties")
@ComponentScan(basePackages = "edu.school21.service")
public class ApplicationConfig {
	@Value("${db.url}")
	private String url;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	@Value("${db.driverClassName}")
	private String driverClassName;

	@Bean
	public DriverManagerDataSource standardDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClassName);
		return dataSource;
	}

	@Bean
	public UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate() {
		return new UsersRepositoryJdbcTemplateImpl(standardDataSource());
	}

	@Bean
	public HikariDataSource hikariDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setDriverClassName(driverClassName);
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public UsersRepositoryJdbcImpl usersRepositoryJdbc() {
		return new UsersRepositoryJdbcImpl(hikariDataSource());
	}
}
