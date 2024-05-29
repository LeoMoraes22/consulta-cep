package com.consulta.cep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CepApplication {

	public static void main(String[] args) {
		System.setProperty("vaadin.whitelisted-packages", "com/consulta/cep/");
		SpringApplication.run(CepApplication.class, args);
	}

}
