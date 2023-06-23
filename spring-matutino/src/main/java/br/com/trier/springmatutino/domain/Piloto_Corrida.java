package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.Piloto_CorridaDTO;
import br.com.trier.springmatutino.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode	(of = "id")
@Entity (name = "piloto_corrida")
public class Piloto_Corrida {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id_piloto_corrida")
	@Setter
	private Integer id;
	
	@ManyToOne
	private Piloto piloto;
	
	@ManyToOne
	private Corrida corrida;
	
	@Column (name = "colocacao_piloto")
	private Integer colocacao;
	
	public Piloto_CorridaDTO toDTO() {
		return new Piloto_CorridaDTO(id, piloto.getId(), piloto.getName(), piloto.getEquipe().getName(), piloto.getPais().getName(), 
				corrida.getId(), DateUtils.zoneDateTimeToStr(corrida.getData()), corrida.getPista().getId(), corrida.getPista().getTamanho(), 
				corrida.getCampeonato().getId(), corrida.getCampeonato().getDescription(), 
				colocacao);
	}
	
	public Piloto_Corrida (Piloto_CorridaDTO dto) {
		this(dto.getId(),
				new Piloto(dto.getId_piloto(), null, null, null),
				new Corrida(dto.getId_corrida(), null, null, null),
				dto.getColocacao());
	}
	
	public Piloto_Corrida (Piloto_CorridaDTO dto, Piloto piloto, Corrida corrida, Integer colocacao) {
		this(dto.getId(),
				piloto,
				corrida,
				dto.getColocacao()
				);
	}
}
