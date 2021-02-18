package br.com.petwebapp.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petwebapp.domain.Cliente;
import br.com.petwebapp.domain.ItemPedido;
import br.com.petwebapp.domain.PagamentoComBoleto;
import br.com.petwebapp.domain.Pedido;
import br.com.petwebapp.domain.enums.EstadoPagamento;
import br.com.petwebapp.repository.ClienteRepository;
import br.com.petwebapp.repository.ItemPedidoRepository;
import br.com.petwebapp.repository.PagamentoRepository;
import br.com.petwebapp.repository.PedidoRepository;
import br.com.petwebapp.security.UserSS;
import br.com.petwebapp.services.exception.AuthorizationException;
import br.com.petwebapp.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository repoPagamento;

	@Autowired
	private ProdutoService serviceProd;

	@Autowired
	private ItemPedidoRepository repoItemPedido;
	
	@Autowired
	private ClienteRepository repoCliente;
	
	@Autowired
	private ClienteService serviceCliente;
	
	@Autowired
	private EmailService emailService;

	public Pedido buscar(Integer id) throws ObjectNotFoundException {

		Optional<Pedido> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}

	@Transactional
	public Pedido insert(Pedido obj) throws ObjectNotFoundException {
		obj.setId(null);
		obj.setCliente(serviceCliente.find(obj.getCliente().getId()));
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherVencimentoBoleto(pgto, obj.getInstante());
		}

//		SALVAMOS O PEDIDO ANTES DO PAGAMENTO
		obj = repo.save(obj);

//		SALVAMOS O PAGAMENTO PEGANDO DIRETO DO PEDIDO
		repoPagamento.save(obj.getPagamento());

		for (ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(serviceProd.buscar(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		
		repoItemPedido.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHTMLEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) throws ObjectNotFoundException {
		UserSS user = UserService.authenticate();
		if(user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = serviceCliente.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);

	}

}
