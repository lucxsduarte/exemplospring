package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.PilotoDTO;
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
@EqualsAndHashCode(of = "id")
@Entity(name = "piloto")
public class Piloto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_piloto")
	@Setter
	private Integer id;

	@Column(name = "name_piloto")
	private String name;

	@ManyToOne
	private Pais pais;

	@ManyToOne
	private Equipe equipe;

	public Piloto(PilotoDTO dto) {
		this(dto.getId(), dto.getName(), new Pais(dto.getId_pais(), null), new Equipe(dto.getId_equipe(), null));
	}

	public Piloto(PilotoDTO dto, Pais pais, Equipe equipe) {
		this(dto.getId(), dto.getName(), pais, equipe);
	}

	public PilotoDTO toDto() {
		return new PilotoDTO(id, name, pais.getId(), pais.getName(), equipe.getId(), equipe.getName());
	}
}
