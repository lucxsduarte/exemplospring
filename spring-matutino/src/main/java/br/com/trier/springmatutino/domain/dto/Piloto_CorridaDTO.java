package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Piloto_CorridaDTO {

	private Integer id;
	private Integer id_piloto;
	private String name_piloto;
	private Integer id_equipe;
	private String equipe_piloto;
	private Integer id_pais;
	private String pais_piloto;
	private Integer id_corrida;
	private String data_corrida;
	private Integer id_pista;
	private Integer tamanho_pista_corrida;
	private Integer id_campeonato;
	private String desc_campeonato;
	private Integer colocacao;
}
