package edu.school21.service.application;

import edu.school21.service.config.ApplicationConfig;
import edu.school21.service.repositories.UsersRepository;
import edu.school21.service.services.UsersService;
import edu.school21.service.services.UsersServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
		System.out.println(usersRepository.findAll());
		usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
		System.out.println(usersRepository.findAll());
		//UsersService userService = context.getBean(UsersService.class);
		//System.out.println(userService.signUp("ivan@mail.ru"));
	}
}
