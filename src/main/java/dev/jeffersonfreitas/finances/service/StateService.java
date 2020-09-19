package dev.jeffersonfreitas.finances.service;

import java.util.Optional;

import dev.jeffersonfreitas.finances.model.State;

public interface StateService {

	State saveState(State state);
	
	Optional<State> findById(Long id);

	void delete(State state);
}
