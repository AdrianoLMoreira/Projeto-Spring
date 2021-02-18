package br.com.petwebapp.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.petwebapp.domain.Categoria;
import br.com.petwebapp.domain.Cidade;
import br.com.petwebapp.domain.Cliente;
import br.com.petwebapp.domain.Endereco;
import br.com.petwebapp.domain.Estado;
import br.com.petwebapp.domain.ItemPedido;
import br.com.petwebapp.domain.Pagamento;
import br.com.petwebapp.domain.PagamentoComCartao;
import br.com.petwebapp.domain.Pedido;
import br.com.petwebapp.domain.Produto;
import br.com.petwebapp.domain.enums.EstadoPagamento;
import br.com.petwebapp.domain.enums.Perfil;
import br.com.petwebapp.domain.enums.TipoCliente;
import br.com.petwebapp.repository.CategoriaRepository;
import br.com.petwebapp.repository.CidadeRepository;
import br.com.petwebapp.repository.ClienteRepository;
import br.com.petwebapp.repository.EnderecoRepository;
import br.com.petwebapp.repository.EstadoRepository;
import br.com.petwebapp.repository.ItemPedidoRepository;
import br.com.petwebapp.repository.PagamentoRepository;
import br.com.petwebapp.repository.PedidoRepository;
import br.com.petwebapp.repository.ProdutoRepository;

@Service
public class DBService {

	
	@Autowired
	private BCryptPasswordEncoder pe;	
	
	@Autowired
	private ItemPedidoRepository repoItemPedido;
	
	@Autowired
	private PagamentoRepository repoPagamento;
	
	@Autowired
	private EstadoRepository repoEstado;
	
	@Autowired
	private EnderecoRepository repoEndereco;
	
	@Autowired
	private CidadeRepository repoCidade;
	
	@Autowired
	private CategoriaRepository repoCat;

	@Autowired
	private ProdutoRepository repoProd;
	
	@Autowired
	private ClienteRepository repoCli;

	@Autowired
	private PedidoRepository repoPed;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Categoria 1");
		Categoria cat2 = new Categoria(null, "Categoria 2");
		Categoria cat3 = new Categoria(null, "Categoria 3");

		Produto prod1 = new Produto(null, "Mouse", 20.00);
		Produto prod2 = new Produto(null, "Teclado", 25.00);
		Produto prod3 = new Produto(null, "Monitor 23'", 800.00);
		Produto prod4 = new Produto(null, "Cx Som", 50.00);
		Produto prod5 = new Produto(null, "Carregador Notebook", 180.00);

		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod3, prod4));
		cat3.getProdutos().addAll(Arrays.asList(prod1, prod2, prod5));

		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1));
		prod3.getCategorias().addAll(Arrays.asList(cat1, cat2));

		repoCat.saveAll(Arrays.asList(cat1, cat2, cat3));
		repoProd.saveAll(Arrays.asList(prod1, prod2, prod3, prod4, prod5));
		
		Cliente cli1 = new Cliente(null, "Cibeli Vieira", 
				"cibeli@gmail.com", "877.436.951.51", 
				TipoCliente.PESSOAFISICA,
				pe.encode("cibeli123"));
		
		Cliente cli2 = new Cliente(null, "Adriano Moreira", 
				"adriano.am88.am@gmail.com", "121.949.898.77", 
				TipoCliente.PESSOAFISICA,
				pe.encode("123456"));
		cli2.addPerfil(Perfil.ADMIN);
		
		Cliente cli3 = new Cliente(null, "Yasmin Moreira", 
				"yasmin@gmail.com", "565.805.520.08", 
				TipoCliente.PESSOAFISICA,
				pe.encode("cibeli123"));
		
		Estado est = new Estado(null, "São Paulo");
			
		Cidade cid1 = new Cidade(null, "Taqua2");
		cid1.setEstado(est);
		est.getCidades().addAll(Arrays.asList(cid1));
		repoEstado.save(est);
		repoCidade.save(cid1);
		
		repoCli.saveAll(Arrays.asList(cli1, cli2, cli3));
		
		Endereco end1 = new Endereco(null, "Rua oi", "220", null, "Taquarão", "15900-000", cli1, cid1);
		cli1.getEnderecos().addAll(Arrays.asList(end1));
		
		Endereco end2 = new Endereco(null, "Rua T Micali", "150", null, "Taquarão 1", "15900-000", cli1, cid1);
		cli2.getEnderecos().addAll(Arrays.asList(end2));
		
		repoEndereco.saveAll(Arrays.asList(end1, end2));
		repoEndereco.save(end1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido pedido1= new Pedido(null, sdf.parse("05/04/2020 08:52"),
				cli1, end1);
		
		// prod = repoProd.findById(1).orElseThrow(()->new ObjectNotFoundException("não encontrado"));
		//Produto prod2 = repoProd.findById(2).orElseThrow(()->new ObjectNotFoundException("não encontrado"));

		ItemPedido itemped = new ItemPedido(pedido1, prod1, 0.00, 1, 200.00);
		ItemPedido itemped2 = new ItemPedido(pedido1, prod2, 0.00, 3, 500.00);
		
		Pagamento pagam =new PagamentoComCartao(null, EstadoPagamento.QUITADO,
				pedido1, 4);
//		Pagamento pagam =new Pagamento();
//		pagam.setEstado(EstadoPagamento.QUITADO);
//		pagam.setPedido(pedido1);
				
		cli1.getPedidos().addAll(Arrays.asList(pedido1));
		pedido1.setPagamento(pagam);
		try {
	
		repoPagamento.save(pagam);
		repoPed.save(pedido1);
		}catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			System.out.println("--------- --------- ----- ---------- ---------- ----------");
			e.printStackTrace();
		}
		pedido1.getItens().addAll(Arrays.asList(itemped, itemped2));
		prod1.getItens().addAll(Arrays.asList(itemped));
		prod2.getItens().addAll(Arrays.asList(itemped2));
		
		repoItemPedido.save(itemped);
		repoItemPedido.save(itemped2);
	}
	
	

}
