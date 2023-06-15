package br.com.trier.springmatutino.domain;

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
	private String descriçao;
	
	@Column (name = "ano_camp")
	private Integer ano;
}
