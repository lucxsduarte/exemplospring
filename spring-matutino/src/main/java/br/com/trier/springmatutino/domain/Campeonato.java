package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity(name = "camp" )
public class Campeonato {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id_camp")
	@Setter
	private Integer id;
	
	@Column (name = "desc_camp")
	private String description;
	
	@Column (name = "ano_camp")
	private Integer ano;
	
	public Campeonato (CampeonatoDTO dto) {
		this(dto.getId(), dto.getDescription(), dto.getAno());
	}
	
	public CampeonatoDTO toDto() {
		return new CampeonatoDTO(this.id, this.description, this.ano);
	}
}
