package edu.school21.service.services;

import edu.school21.service.config.TestApplicationConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UsersServiceImplTest {

	@Test
	void UsersServicesTest() throws IllegalAccessException {
		ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
		UsersService userService = context.getBean(UsersService.class);
		String password = userService.signUp("user@mail.com");
		assertNotEquals(password, "");
	}

}
