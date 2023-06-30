package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotoDTO {

	private Integer id;
	private String name;
	private Integer id_pais;
	private String name_pais;
	private Integer id_equipe;
	private String name_equipe;
}
