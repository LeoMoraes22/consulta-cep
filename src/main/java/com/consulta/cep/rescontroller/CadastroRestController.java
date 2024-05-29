package com.consulta.cep.rescontroller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consulta.cep.controller.CadastroController;
import com.consulta.cep.exception.DefaultException;
import com.consulta.cep.model.Cadastro;
import com.google.gson.Gson;

import elemental.json.Json;
import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/api/cadastro")
@Validated
@PermitAll
public class CadastroRestController {

	private final CadastroController controller;
	
	public CadastroRestController(CadastroController controller) {
		this.controller = controller;
	}
	
	@PostMapping
	public ResponseEntity<Cadastro> saveContact(@RequestBody Cadastro cadastro) throws DefaultException, Exception{
		
		//** consumindo API 
		URL url = new URL("https://viacep.com.br/ws/"+ cadastro.getCep() +"/json/");
		URLConnection connection = url.openConnection();
		InputStream iStream = connection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
		
		String cep = "";
		StringBuilder jsonCep = new StringBuilder();
		
		while ((cep = bufferedReader.readLine()) != null) {
			jsonCep.append(cep);
		}
		
		System.out.println(jsonCep.toString());
		
		Cadastro cadastroAux = new Gson().fromJson(jsonCep.toString(), Cadastro.class);
		
		cadastro.setCep(cadastroAux.getCep());
		cadastro.setBairro(cadastroAux.getBairro());
		cadastro.setLocalidade(cadastroAux.getLocalidade());
		cadastro.setComplemento(cadastroAux.getComplemento());
		cadastro.setLogradouro(cadastroAux.getLogradouro());
		cadastro.setUf(cadastroAux.getUf());
		
		//** consumindo API
		
		Cadastro cadastroSaved = controller.save(cadastro);
		return new ResponseEntity<>(cadastroSaved, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Cadastro>> listCadastro() throws DefaultException{
		List<Cadastro> cadastros = controller.list();
		return new ResponseEntity<>(cadastros, HttpStatus.OK);
	}	
	
	@PutMapping("/contacts")
	public ResponseEntity<Cadastro> updateCadastro(@RequestBody Cadastro cadastro) throws DefaultException{
		Cadastro updateCadastro = controller.update(cadastro);
		return new ResponseEntity<>(updateCadastro, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCadastro(@PathVariable Long id) throws DefaultException{
		Cadastro cadastro = new Cadastro();
		cadastro.setId(id);
		controller.delete(cadastro);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cadastro> getCadastroById(@PathVariable Long id){
		try {
			Cadastro cadastro = controller.load(id);
			return new ResponseEntity<>(cadastro, HttpStatus.OK);
		} catch (DefaultException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
