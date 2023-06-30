package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PodioDTO {

	private String name_piloto;
	private String pais_piloto;
	private String equipe_piloto;
	private Integer colocacao;
}
