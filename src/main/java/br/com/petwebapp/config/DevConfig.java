package br.com.petwebapp.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.petwebapp.services.DBService;
import br.com.petwebapp.services.EmailService;
import br.com.petwebapp.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean initiateDataBase() throws ParseException {
		
//		SE O PROFILE ESTIVER COMO CREATE NÃO EXECUTA O SERVIÇO
		if(strategy.contains("create") || strategy.contains("none")) {
			return false;
		}
//		SE O PROFILE ESTIVER COMO CREATE NÃO EXECUTA O SERVIÇO
			
		
		dbService.instantiateTestDatabase();
		return true;
	}

	@Bean
	EmailService emailService() {
		return new SmtpEmailService();
	}
}
