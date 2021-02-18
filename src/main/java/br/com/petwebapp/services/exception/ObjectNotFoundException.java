package br.com.petwebapp.services.exception;

import javassist.NotFoundException;

public class ObjectNotFoundException extends NotFoundException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, (Exception) cause);
	}

}
