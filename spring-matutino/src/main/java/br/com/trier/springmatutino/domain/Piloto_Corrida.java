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
@EqualsAndHashCode	(of = "id")
@Entity (name = "piloto_corrida")
public class Piloto_Corrida {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id_piloto_corrida")
	@Setter
	private Integer id;
	
	@Column (name = "piloto")
	private Integer piloto;
	
	@Column (name = "corrida")
	private Integer corrida;
	
	@Column (name = "colocacao_piloto")
	private Integer colocacao;
}
