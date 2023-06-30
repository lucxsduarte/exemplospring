package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CorridaDTO {

	private Integer id;
	private String data;
	private Integer id_pista;
	private Integer tamanho_pista;
	private Integer id_campeonato;
	private String desc_camp;
}
