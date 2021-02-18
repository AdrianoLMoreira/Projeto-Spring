package br.com.petwebapp.services.validaton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ClienteUpdateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//INTERFACE CRIADA COMO ANOTAÇÃO PERSONALIZADA
public @interface ProdutoUpdate {
	
	String message() default "Erro de validação";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default{};
}
