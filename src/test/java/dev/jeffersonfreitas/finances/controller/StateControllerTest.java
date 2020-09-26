package dev.jeffersonfreitas.finances.controller;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.jeffersonfreitas.finances.model.State;
import dev.jeffersonfreitas.finances.model.dto.StateDTO;
import dev.jeffersonfreitas.finances.service.StateService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class StateControllerTest {
	
	static String API_STATE = "/api/state";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	StateService service;
	
	
	//CADASTRAR
	@Test
	@DisplayName("Should create a new state")
	public void createNewState() throws Exception{
		StateDTO dto = createNewDTO();
		State savedState = State.builder().id(1l).nome(dto.getNome()).uf(dto.getUf()).build();
		
		BDDMockito.given(service.saveState(Mockito.any(State.class))).willReturn(savedState);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
									.post(API_STATE)
									.contentType(MediaType.APPLICATION_JSON_VALUE)
									.accept(MediaType.APPLICATION_JSON_VALUE)
									.content(json);
		
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(1l))
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value(dto.getNome()))
			.andExpect(MockMvcResultMatchers.jsonPath("uf").value(dto.getUf()));		
	}
	
	
	//ERRO VALIDAÇÃO AO CADASTRAR
	@Test
	@DisplayName("Throw error when a state is not valid")
	public void invalidStateToSave() throws Exception {
		String json = new ObjectMapper().writeValueAsString(new StateDTO());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_STATE)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.content(json);
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	

	@Test
	@DisplayName("Should find a state by id")
	public void findStateByIdTest() throws Exception{
		Long id = 1l;
		State state = State.builder().id(id).nome("State").uf("ST").build();
		BDDMockito.given(service.findById(id)).willReturn(Optional.of(state));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_STATE.concat("/" + id))
				.accept(MediaType.APPLICATION_JSON_VALUE);
		
		mvc.perform(request)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("nome").value(state.getNome()))
		.andExpect(MockMvcResultMatchers.jsonPath("uf").value(state.getUf()));
	}
	
	
	@Test
	@DisplayName("Throw error when not found state by id")
	public void notFindStateByIdTest() throws Exception{
		Long id = 1l;
		BDDMockito.given(service.findById(id)).willReturn(Optional.empty());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_STATE.concat("/" + id))
				.accept(MediaType.APPLICATION_JSON_VALUE);
		
		mvc.perform(request)
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	

	@Test
	@DisplayName("Should delete a state")
	public void deleteBankTest() throws Exception {
		Long id = 1l;
		State state = State.builder().id(id).nome("State").uf("ST").build();
		BDDMockito.given(service.findById(id)).willReturn(Optional.of(state));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API_STATE.concat("/" + id));
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	
	@Test
	@DisplayName("Throw error when not found a state to delete")
	public void notFoundForDeleteStateTest() throws Exception{
		BDDMockito.given(service.findById(1l)).willReturn(Optional.empty());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API_STATE.concat("/1"));
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	@DisplayName("Updating a state")
	public void updateStateTest() throws Exception{
		Long id = 1l;
		
		State updatingState = State.builder().id(id).nome("some state").uf("SS").build();
		BDDMockito.given(service.findById(id)).willReturn(Optional.of(updatingState));
		
		State updatedState = State.builder().id(id).nome("Ceara").uf("CE").build();
		BDDMockito.given(service.saveState(updatingState)).willReturn(updatedState);
		
		String json = new ObjectMapper().writeValueAsString(createNewDTO());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API_STATE.concat("/"+1))
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json);
		
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value(createNewDTO().getNome()))
			.andExpect(MockMvcResultMatchers.jsonPath("uf").value(createNewDTO().getUf()));
	}
	
	
	@Test
	@DisplayName("Should return not found when try to updating a state")
	public void notFoundUpdatingStateTest() throws Exception{
		BDDMockito.given(service.findById(1l)).willReturn(Optional.empty());
		String json = new ObjectMapper().writeValueAsString(createNewDTO());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API_STATE.concat("/"+1))
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json);
		
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	//ERRO NÃO ENCONTRAR REGISTRO PRA ATUALIZAR
	
	//FILTRAR
	
	
	
	
	private StateDTO createNewDTO() {
		return StateDTO.builder().nome("Ceara").uf("CE").build();
	}

}
