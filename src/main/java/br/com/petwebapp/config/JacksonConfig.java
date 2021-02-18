package br.com.petwebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.petwebapp.domain.PagamentoComBoleto;
import br.com.petwebapp.domain.PagamentoComCartao;

public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
		
		public void configure(ObjectMapper objectMapper) {
			objectMapper.registerSubtypes(PagamentoComCartao.class);
			objectMapper.registerSubtypes(PagamentoComBoleto.class);
			super.configure(objectMapper);
			
		}
	};
	return builder;
	}

}
