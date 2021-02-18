package br.com.petwebapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.petwebapp.domain.Categoria;
import br.com.petwebapp.domain.Produto;
import br.com.petwebapp.dto.ProdutoNewDTO;
import br.com.petwebapp.repository.CategoriaRepository;
import br.com.petwebapp.repository.ProdutoRepository;
import br.com.petwebapp.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private CategoriaService catService;

	@Autowired
	public ProdutoRepository repo;

	@Autowired
	private CategoriaRepository catRepo;

	public Produto buscar(Integer id) throws ObjectNotFoundException {

		Optional<Produto> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = catRepo.findAllById(ids);
		//return repo.search(nome, categorias, pageRequest);
		if(categorias.size()>0) {
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		}else {
			return repo.findProduto(nome, pageRequest);
		}
	}

	public Produto insert(Produto obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Produto fromDTO(ProdutoNewDTO obj) throws ObjectNotFoundException {
		// throw new UnsupportedOperationException();
		Produto prod = new Produto(null, obj.getNome(), obj.getPreco());
		Categoria cat1 = new Categoria();
		cat1 = catService.find(obj.getCategoriaId());
		cat1.getProdutos().add(prod);
		prod.getCategorias().add(cat1);
		catRepo.save(cat1);
		
		return prod;
	}
	

//	ESTE MÉTODO NÃO FUNCIONA COM ENDPOINT, MAS FUNCIONA EM TESTE DIRETO PRECISA SER ESTUDADO
	public List<Produto> findByName(String nome){
		List<Produto> lista = new ArrayList<>();
		try {
	
		lista = repo.findByNomeContainingIgnoreCase(nome);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
//	ESTE MÉTODO NÃO FUNCIONA COM ENDPOINT, MAS FUNCIONA EM TESTE DIRETO PRECISA SER ESTUDADO
	
	
	public List<Produto> findAll() {
		List<Produto> lista = new ArrayList<>();
		lista = repo.findAll();
		
		return lista;
	}
	
	
}
