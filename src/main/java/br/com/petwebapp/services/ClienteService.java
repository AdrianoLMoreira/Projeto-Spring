package br.com.petwebapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.petwebapp.domain.Cidade;
import br.com.petwebapp.domain.Cliente;
import br.com.petwebapp.domain.Endereco;
import br.com.petwebapp.domain.enums.Perfil;
import br.com.petwebapp.domain.enums.TipoCliente;
import br.com.petwebapp.dto.ClienteDTO;
import br.com.petwebapp.dto.ClienteNewDTO;
import br.com.petwebapp.repository.CidadeRepository;
import br.com.petwebapp.repository.ClienteRepository;
import br.com.petwebapp.security.UserSS;
import br.com.petwebapp.services.exception.AuthorizationException;
import br.com.petwebapp.services.exception.DataIntegrityException;
import br.com.petwebapp.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;	
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository repoCidade;

	public Cliente find(Integer id) throws ObjectNotFoundException {

		UserSS userSS = UserService.authenticate();
		if(userSS==null || !userSS.getId().equals(id) && !userSS.hasRole(Perfil.ADMIN)) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		Optional<Cliente> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}

	public List<Cliente> findAll() throws ObjectNotFoundException {

		List<Cliente> obj = repo.findAll();

		return obj;
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Cliente update(Cliente obj) throws ObjectNotFoundException {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) throws ObjectNotFoundException, DataIntegrityException {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir este Cliente!");
		}

	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, pe.encode(objDto.getSenha()));
		
	}

	public Cliente fromDTO(ClienteNewDTO obj) {
		// throw new UnsupportedOperationException();
		Cliente cli = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(),
				TipoCliente.toEnum(obj.getTipo()), obj.getSenha());
		Optional<Cidade> cid = repoCidade.findById(obj.getCidadeId());
		Endereco endereco = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(),
				obj.getBairro(), obj.getCep(), cli, cid.get());
		cli.getEnderecos().add(endereco);
		if (obj.getTelefone2() != null) {
			cli.getTelefones().add(obj.getTelefone2());
		}
		if (obj.getTelefone3() != null) {
			cli.getTelefones().add(obj.getTelefone3());
		}
		cli.getTelefones().add(obj.getTelefone1());
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {

		if (obj.getEmail() != null) {
			newObj.setEmail(obj.getEmail());
		}
		if (obj.getNome() != null) {
			newObj.setNome(obj.getNome());
		}

	}
}
