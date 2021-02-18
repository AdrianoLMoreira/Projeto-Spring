package br.com.petwebapp.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.petwebapp.domain.Cliente;
import br.com.petwebapp.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendHtmlEmail(MimeMessage msg);

	void sendOrderConfirmationHTMLEmail(Pedido obj);

	void sendNewPassowrd(Cliente cliente, String newPass);
}
