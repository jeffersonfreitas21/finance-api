package dev.jeffersonfreitas.finances.service.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

public class APIErrors {

	
	List<String> erros;
	
	public APIErrors(BindingResult bindingResult) {
		this.erros = new ArrayList<>();
		bindingResult.getAllErrors().forEach(e -> this.erros.add(e.getDefaultMessage()));
	}
	
	public APIErrors(ResponseStatusException ex) {
		this.erros = Arrays.asList(ex.getReason());
	}
	
	public List<String> getErrors(){
		return this.erros;
	}
}
