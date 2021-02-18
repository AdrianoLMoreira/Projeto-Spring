package br.com.petwebapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.petwebapp.domain.Cidade;
import br.com.petwebapp.dto.CidadeDTO;
import br.com.petwebapp.dto.CidadeNewDTO;
import br.com.petwebapp.repository.CidadeRepository;
import br.com.petwebapp.repository.ClienteRepository;
import br.com.petwebapp.services.exception.DataIntegrityException;
import br.com.petwebapp.services.exception.ObjectNotFoundException;

@Service
public class CidadeService {

	@Autowired
	private ClienteRepository repoCliente;

	@Autowired
	private CidadeRepository repoCidade;

	public Cidade find(Integer id) throws ObjectNotFoundException {

		Optional<Cidade> obj = repoCidade.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada!"));
	}

	public List<Cidade> findAll() throws ObjectNotFoundException {

		List<Cidade> obj = repoCidade.findAll();

		return obj;
	}

	public Cidade insert(Cidade obj) {
		obj.setId(null);
		return repoCidade.save(obj);
	}

	public Cidade update(Cidade obj) throws ObjectNotFoundException {
		Cidade newObj = find(obj.getId());
		updateData(newObj, obj);
		return repoCidade.save(newObj);
	}

	public void delete(Integer id) throws ObjectNotFoundException, DataIntegrityException {
		find(id);
		try {
			repoCidade.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir esta Cidade!");
		}

	}

	public Page<Cidade> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repoCidade.findAll(pageRequest);

	}

	public Cidade fromDTO(CidadeDTO objDto) {
		return new Cidade(objDto.getId(), objDto.getNome(), objDto.getEstado());
	}

	public Cidade fromDTO(CidadeNewDTO obj) {
		// throw new UnsupportedOperationException();
		Cidade cid = new Cidade(null, obj.getNome(), obj.getEstado());
		
		return cid;
	}

	private void updateData(Cidade newObj, Cidade obj) {

		if (obj.getEstado() != null) {
			newObj.setEstado(obj.getEstado());
		}
		
	}
}
