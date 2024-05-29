package com.consulta.cep.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.consulta.cep.builder.CadastroBuilder;
import com.consulta.cep.exception.DefaultException;
import com.consulta.cep.model.Cadastro;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CadastroControllerTest {

	@Autowired
	private CadastroController controller;
	

	private Cadastro cadastro;
	
	@BeforeAll
	void BuilBean() {
		cadastro = CadastroBuilder.build().now();
	}
	
	@Test
	@Order(1)
	void testSave() throws DefaultException {
		assertNotNull(controller.save(cadastro));
	}
	
	@Test
	@Order(2)
	void testList() throws DefaultException {
		assertNotNull(controller.list());
	}
	
	@Test
	@Order(3)
	void testLoad() throws DefaultException {
		cadastro.setBairro("load");
		Cadastro cadastroSave = controller.save(cadastro);
		assertNotNull(cadastroSave);
		
		Cadastro foundCadastro = controller.load(cadastroSave.getId());
		
		assertNotNull(foundCadastro);
		
		assertEquals(cadastroSave.getId(), foundCadastro.getId());
	}
	
	@Test
	@Order(4)
	void testUpdate() throws DefaultException {
		cadastro.setBairro("update");
		Cadastro cadastroSave = controller.save(cadastro);
		
		String bairroAntigo = cadastroSave.getBairro();
		
		cadastroSave.setBairro("new bairro");
		cadastroSave = controller.save(cadastroSave);
		
		assertNotEquals(bairroAntigo, cadastroSave.getBairro());
	}
	
	@Test
	@Order(5)
	void testDelete() throws DefaultException {
		cadastro.setBairro("delete");
		Cadastro cadastroSave = controller.save(cadastro);
		controller.delete(cadastroSave);
		
		assertThrows(DefaultException.class, () -> controller.load(cadastroSave.getId()));
	}
	
}
