package br.com.petwebapp.services.exception;

import javassist.NotFoundException;

public class DataIntegrityException extends NotFoundException{

	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, (Exception) cause);
	}

}
