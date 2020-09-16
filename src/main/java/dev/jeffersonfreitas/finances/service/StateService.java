package dev.jeffersonfreitas.finances.service;

import dev.jeffersonfreitas.finances.model.State;
import dev.jeffersonfreitas.finances.model.dto.StateDTO;

public interface StateService {

	State saveState(State state);
	
	StateDTO findById(Long id);
}
