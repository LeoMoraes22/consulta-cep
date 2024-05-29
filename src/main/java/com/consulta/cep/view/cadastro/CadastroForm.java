package com.consulta.cep.view.cadastro;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serial;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;

import com.consulta.cep.controller.CadastroController;
import com.consulta.cep.exception.DefaultException;
import com.consulta.cep.model.Cadastro;
import com.consulta.cep.view.layout.MainLayout;
import com.consulta.cep.view.utils.GenericForm;
import com.google.gson.Gson;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "cadastro", layout = MainLayout.class)
@PageTitle("Cadastro | CEP")
@PermitAll
public class CadastroForm extends GenericForm<Cadastro, Long, CadastroController> implements HasUrlParameter<Long>{
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private TextField txtBairro;
	
	private TextField txtCep;
	
	private TextField txtCidade;
	
	private TextField txtComplemento;
	
	private TextField txtEndereco;
	
	private TextField txtEstado;

	public CadastroForm(@Autowired CadastroController cadastroController) {
		super(Cadastro.class, cadastroController, new Cadastro());
		
		setTitle("Cadastro | CEP");
		setSuccessRoute("cadastros");
		
		instanciarComponente();
		
		txtCep.addValueChangeListener(e ->{
			try {
				consultarCep();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		addInForm(txtCep, 3, "cep");
		addInForm(txtBairro, 3, "bairro");
		addInForm(txtCidade, 3, "localidade");
		addInForm(txtComplemento, 3, "complemento");
		addInForm(txtEndereco, 3, "logradouro");
		addInForm(txtEstado, 3, "uf");
		
		createBinder();
	}

	private void consultarCep() throws Exception {
		//** consumindo API 
		URL url = new URL("https://viacep.com.br/ws/"+ txtCep.getValue().toString() +"/json/");
		URLConnection connection = url.openConnection();
		InputStream iStream = connection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
		
		String cep = "";
		StringBuilder jsonCep = new StringBuilder();
		
		while ((cep = bufferedReader.readLine()) != null) {
			jsonCep.append(cep);
		}
		
		Cadastro cadastroAux = new Gson().fromJson(jsonCep.toString(), Cadastro.class);
		
		txtBairro.setValue(cadastroAux.getBairro());
		txtCidade.setValue(cadastroAux.getLocalidade());
		txtComplemento.setValue(cadastroAux.getComplemento());
		txtEndereco.setValue(cadastroAux.getLogradouro());
		txtEstado.setValue(cadastroAux.getUf());
	}

	private void instanciarComponente() {
		
		txtBairro = new TextField("Bairro");
		
		txtCep = new TextField("CEP");
		
		txtCidade = new TextField("Cidade");
		
		txtComplemento = new TextField("Complemento");
		
		txtEndereco = new TextField("Endereço");
		
		txtEstado = new TextField("Estado");
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
		try {

			if (parameter != null) {
				setBean(controller.load(parameter)); 
			} else {
				setBean(new Cadastro());
			}

		} catch (DefaultException e) {
			mostrarAviso();
		}
		createBinder();
	}
	
	private void mostrarAviso() {
		ConfirmDialog dialogMessage = new ConfirmDialog();
		dialogMessage.setHeader("Registro não encontrado");
		dialogMessage.setText(new Html("<p>Clique no botão para visualizar todos os registros</p>"));

		dialogMessage.setConfirmText("Redirecionar");

		dialogMessage.addConfirmListener(ev -> {
			voltar();
			dialogMessage.close();
		});
		dialogMessage.open();
	}

}
