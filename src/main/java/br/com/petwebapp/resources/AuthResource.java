package br.com.petwebapp.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.petwebapp.dto.EmailDTO;
import br.com.petwebapp.security.JWTUtil;
import br.com.petwebapp.security.UserSS;
import br.com.petwebapp.services.AuthService;
import br.com.petwebapp.services.UserService;
import br.com.petwebapp.services.exception.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	AuthService authService;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response){
		
		UserSS user = UserService.authenticate();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer "+token);		
		return ResponseEntity.noContent().build();
	}
	
	
	//@VALID SERVE PARA VALIDAR AS ANOTAÇÕES QUE FORAM CONFIGURADAS NO DTO
	//@REQUESTBODY INFORMA QUE DEVE SER CONSIDERADO O QUE VIER NO CORPO DA REQUISÇÃO
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) throws ObjectNotFoundException{
		authService.sendNewPassword(emailDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
}
