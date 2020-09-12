package dev.jeffersonfreitas.finances.controller;

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
	
	
	private StateDTO createNewDTO() {
		return StateDTO.builder().nome("State of Ceara").uf("CE").build();
	}

}
