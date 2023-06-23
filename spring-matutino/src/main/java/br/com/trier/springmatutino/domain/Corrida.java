package br.com.trier.springmatutino.domain;

import java.time.ZonedDateTime;

import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "corrida")
public class Corrida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_corrida")
	@Setter
	private Integer id;

	@Column(name = "data_corrida")
	private ZonedDateTime data;

	@ManyToOne
	@NotNull
	private Pista pista;

	@ManyToOne
	@NotNull
	private Campeonato campeonato;

	public CorridaDTO toDTO() {
		return new CorridaDTO(id, DateUtils.zoneDateTimeToStr(data), pista.getId(), pista.getTamanho(),
				campeonato.getId(), campeonato.getDescription());
	}

	public Corrida(CorridaDTO dto) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getData()), new Pista(dto.getId_pista(), null, null),
				new Campeonato(dto.getId_campeonato(), null, null));
	}

	public Corrida(CorridaDTO dto, Pista pista, Campeonato camp) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getData()), pista, camp);
	}

}
