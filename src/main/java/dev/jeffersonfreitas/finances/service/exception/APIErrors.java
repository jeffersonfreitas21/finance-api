package dev.jeffersonfreitas.finances.service.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;

public class APIErrors {

	
	List<String> erros;
	
	public APIErrors(BindingResult bindingResult) {
		this.erros = new ArrayList<>();
		bindingResult.getAllErrors().forEach(e -> this.erros.add(e.getDefaultMessage()));
	}
	
	public List<String> getErrors(){
		return this.erros;
	}
}
