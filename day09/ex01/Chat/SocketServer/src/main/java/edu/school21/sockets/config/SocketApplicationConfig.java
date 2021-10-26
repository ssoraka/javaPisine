package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.net.ServerSocket;

@Configuration
@PropertySource("db.properties")
@ComponentScan(basePackages = "edu.school21.sockets")
public class SocketApplicationConfig {
	@Value("${db.url}")
	private String url;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	@Value("${db.driverClassName}")
	private String driverClassName;

	@Bean
	public DataSource hikariDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setDriverClassName(driverClassName);
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public PasswordEncoder bCryptEncoder() {
		return new BCryptPasswordEncoder();
	}
}
