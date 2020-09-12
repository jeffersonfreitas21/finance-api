package dev.jeffersonfreitas.finances.model.dto;

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
	
	private String nome;
	
	private String uf;

}
