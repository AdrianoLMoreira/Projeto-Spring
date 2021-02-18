package br.com.petwebapp.resources;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.petwebapp.domain.Loja;
import br.com.petwebapp.services.LojaService;
import br.com.petwebapp.services.exception.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/lojas")
public class LojaResource {
	
	@Autowired
	private LojaService service;

	@RequestMapping(method = RequestMethod.GET)
	public java.util.List<Loja> listar() throws ObjectNotFoundException {

		java.util.List<Loja> listaLojas = new ArrayList<>();
		listaLojas = service.buscarTodas();
		
		return listaLojas;
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Loja> find(@PathVariable Integer id) throws ObjectNotFoundException {
		
		Loja loja1 = service.buscar(id);
		
		return ResponseEntity.ok().body(loja1);
	}
	
	
}
