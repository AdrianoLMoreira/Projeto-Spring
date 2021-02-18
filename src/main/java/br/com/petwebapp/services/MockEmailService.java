package br.com.petwebapp.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import br.com.petwebapp.domain.Pedido;


//ESTA CLASSE SERVE COMO TESTE DE ENVIO DE EMAIL
public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulação de envio de e-mail ");
		LOG.info(msg.toString());
		LOG.info("E-mail enviado");
		
	}


	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulação de envio de e-mail HTML ");
		LOG.info(msg.toString());
		LOG.info("E-mail enviado");
		
	}

}
