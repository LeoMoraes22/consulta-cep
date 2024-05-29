package com.consulta.cep.controller;

import org.springframework.stereotype.Service;

import com.consulta.cep.exception.DefaultException;
import com.consulta.cep.model.Cadastro;
import com.consulta.cep.repository.CadastroRepository;

@Service
public class CadastroController extends ControllerGeneric<Cadastro, Long, CadastroRepository>{

	public CadastroController(CadastroRepository repository) {
        this.repository = repository;
    }
    
    @Override
    protected void validate(Cadastro entity, Mode mode) throws DefaultException{
    	super.validate(entity, mode);
    	
    	switch (mode) {
			case SAVE: 
				System.out.println("save");
				//if(repository.existsById(entity.getId())) throw new DefaultException("Contact existente");
				break;
			case UPDATE:
				System.out.println("update");
				if(!repository.existsById(entity.getId())) throw new DefaultException("Contact não existente");
				break;
			case DELETE:
				System.out.println("delete");
				if(!repository.existsById(entity.getId())) throw new DefaultException("Contact não existente");
				break;
		}
    }
}
