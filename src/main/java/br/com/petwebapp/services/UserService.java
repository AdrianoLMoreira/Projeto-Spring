package br.com.petwebapp.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.petwebapp.security.UserSS;

public class UserService {

	
	public static UserSS authenticate() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
