package br.com.petwebapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.petwebapp.domain.Loja;
import br.com.petwebapp.repository.LojaRepository;
import br.com.petwebapp.services.exception.ObjectNotFoundException;


@Service
public class LojaService {

	@Autowired
	private LojaRepository repo; 
	
public Loja buscar(Integer id) throws ObjectNotFoundException {
	
	Optional<Loja> obj = repo.findById(id);

	return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}


public List<Loja> buscarTodas() throws ObjectNotFoundException {
	
	List<Loja> obj = repo.findAll();

	return obj;
	}
}
