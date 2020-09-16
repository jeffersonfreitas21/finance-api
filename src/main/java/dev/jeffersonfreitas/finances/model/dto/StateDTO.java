package dev.jeffersonfreitas.finances.model.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateDTO {
	
	private Long id;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String uf;

}
