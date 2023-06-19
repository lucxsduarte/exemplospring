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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity (name = "piloto")
public class Piloto {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id_piloto")
	@Setter
	private Integer id;
	
	@Column (name = "name_piloto")
	private String name;
	
	@Column (name = "pais_piloto")
	private Integer pais;
	
	@Column (name = "equipe_piloto")
	private Integer equipe;
}
