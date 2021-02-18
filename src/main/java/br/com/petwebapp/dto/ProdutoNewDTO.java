package br.com.petwebapp.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class ProdutoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Preenchimento obrigatório no campo nome")
	@Length(max = 80, min = 5, message = "O nome deve ter entre 5 e 80 caracteres")
	private String nome;

	private Double preco;

	private Integer categoriaId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

}
