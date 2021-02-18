package br.com.petwebapp.domain.enums;

public enum StatusPagamento {
	
	PENDENTE (1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private Integer cod;
	private String descricao;
	
	private StatusPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
public static StatusPagamento toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for (StatusPagamento x : StatusPagamento.values()) {
			if(x.getCod() == cod) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
