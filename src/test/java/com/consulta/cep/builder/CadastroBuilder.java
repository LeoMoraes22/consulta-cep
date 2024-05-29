package com.consulta.cep.builder;

import com.consulta.cep.model.Cadastro;

public class CadastroBuilder {
	
	private Cadastro cadastro;
	
	public static CadastroBuilder build() {
		CadastroBuilder builder = new CadastroBuilder();
		builder.cadastro = new Cadastro();
		
		builder.cadastro.setBairro("teste");
		builder.cadastro.setCep("16306136");
		builder.cadastro.setLocalidade("Penápolis");
		builder.cadastro.setComplemento("complemento");
		builder.cadastro.setLogradouro("endereco");
		builder.cadastro.setUf("SP");
		
		return builder;
	}
	
	public Cadastro now() {
		return cadastro;
	}

}
