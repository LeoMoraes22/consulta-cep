package com.consulta.cep.view.cadastro;

import java.io.Serial;

import org.springframework.beans.factory.annotation.Autowired;

import com.consulta.cep.controller.CadastroController;
import com.consulta.cep.exception.DefaultException;
import com.consulta.cep.model.Cadastro;
import com.consulta.cep.view.layout.MainLayout;
import com.consulta.cep.view.utils.GenericGrid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "cadastros", layout = MainLayout.class)
@PageTitle("Cadastros | CEP")
@PermitAll
public class CadastroGrid extends GenericGrid<Cadastro, Long, CadastroController>{

	@Serial
	private static final long serialVersionUID = 1L;

	public CadastroGrid(@Autowired CadastroController controller) throws DefaultException {
		super(controller, Cadastro.class, Cadastro::getId);
		setTitle("Cadastro | CEP");
		setRotaForm("cadastro");
		configurarGrid();
	}

	private void configurarGrid() {
		getGrid().addColumn(Cadastro::getId).setHeader("Id");
        getGrid().addColumn(Cadastro::getBairro).setHeader("Bairro");
        getGrid().addColumn(Cadastro::getCep).setHeader("CEP");  
        getGrid().addColumn(Cadastro::getLocalidade).setHeader("Cidade");
        getGrid().addColumn(Cadastro::getComplemento).setHeader("Complemento");
        getGrid().addColumn(Cadastro::getLogradouro).setHeader("Endereco");  
        getGrid().addColumn(Cadastro::getUf).setHeader("Estado");  
	}
	
	

}
