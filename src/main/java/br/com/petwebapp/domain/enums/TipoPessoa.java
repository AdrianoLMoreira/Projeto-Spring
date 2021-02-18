package br.com.petwebapp.domain.enums;

public enum TipoPessoa {
	
	CLIENTE(1, "Cliente"),
	FUNCIONARIO(2, "Funcionario");

	private int cod;
	private String descricao;
	
	private TipoPessoa(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}


	public String getDescricao() {
		return descricao;
	}

	
	public static TipoPessoa toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for (TipoPessoa x : TipoPessoa.values()) {
			if(x.getCod() == cod) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
