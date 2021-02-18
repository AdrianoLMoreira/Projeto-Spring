package br.com.petwebapp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.petwebapp.domain.enums.TipoPessoa;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;
	private String endereco;
	private String cpf;
	private Boolean ativo;
	private Character status;
	private Integer tipo;

	private Date dataCadastro;

	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "loja_id")
	private Loja loja;

	private Cidade cidade;

//	@JsonBackReference
//	@OneToMany(mappedBy = "pessoa")
//	private java.util.List<Pedido> pedidos = new ArrayList<>();

	public Pessoa() {

	}

	public Pessoa(Integer id, String nome, String endereco, Date dataCadastro, Loja loja) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.dataCadastro = dataCadastro;
		this.loja = loja;
	}

	public Pessoa(Integer id, String nome, String endereco, String cpf, Boolean ativo, Character status,
			TipoPessoa tipo, Date dataCadastro, Loja loja) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.cpf = cpf;
		this.ativo = ativo;
		this.status = status;
		this.tipo = tipo.getCod();
		this.dataCadastro = dataCadastro;
		this.loja = loja;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public TipoPessoa getTipo() {
		return TipoPessoa.toEnum(tipo);
	}

	public void setTipo(TipoPessoa tipo) {
		this.tipo = tipo.getCod();
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
//	public java.util.List<Pedido> getPedidos() {
//		return pedidos;
//	}
//	
//	public void setPedidos(java.util.List<Pedido> pedidos) {
//		this.pedidos = pedidos;
//	}
}
