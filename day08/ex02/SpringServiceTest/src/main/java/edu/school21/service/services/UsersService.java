package edu.school21.service.services;

import org.springframework.stereotype.Component;

@Component
public interface UsersService {
	String signUp(String email);
}
