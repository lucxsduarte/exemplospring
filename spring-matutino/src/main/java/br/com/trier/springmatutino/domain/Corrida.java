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
@Entity (name = "corrida")
public class Corrida {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id_corrida")
	@Setter
	private Integer id;
	
	//@Column (name = "data_corrida")
	//private LocalDate data;
	
	@Column (name = "pista_corrida")
	private Integer pista;
	
	@Column (name = "camp_corrida")
	private Integer campeonato;
	
}
