package dev.jeffersonfreitas.finances.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.jeffersonfreitas.finances.model.State;
import dev.jeffersonfreitas.finances.model.dto.StateDTO;
import dev.jeffersonfreitas.finances.service.StateService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/state")
@AllArgsConstructor
public class StateController {
	
	private final StateService service;
	private final ModelMapper mapper;

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateDTO newState(@RequestBody @Valid StateDTO dto) {
		State state = mapper.map(dto, State.class);
		state = service.saveState(state);
		return mapper.map(state, StateDTO.class);		
	}
	
	@GetMapping("{id}")
	public StateDTO getById(@PathVariable Long id) {
		return service.findById(id).map( s -> mapper.map(s, StateDTO.class))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		State state = service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		service.delete(state);
	}
}
