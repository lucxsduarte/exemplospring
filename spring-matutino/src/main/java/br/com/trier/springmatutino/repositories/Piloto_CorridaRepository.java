package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Piloto_Corrida;

@Repository
public interface Piloto_CorridaRepository extends JpaRepository<Piloto_Corrida, Integer>{

	List<Piloto_Corrida> findByPiloto(Integer piloto);
	
	List<Piloto_Corrida> findByCorrida(Integer corrida);
	
	List<Piloto_Corrida> findByColocacao(Integer colocacao);
	
	List<Piloto_Corrida> findByColocacaoBetweenAndCorrida(Integer colocacao1, Integer colocacao2, Integer corrida);
	
	List<Piloto_Corrida> findByColocacaoLessThanEqualAndCorrida(Integer colocacao, Integer corrida);
	
	List<Piloto_Corrida> findByColocacaoGreaterThanEqualAndCorrida(Integer colocacao, Integer corrida);
	
	List<Piloto_Corrida> findByColocacaoAndCorrida(Integer colocacao, Integer corrida);
	
}
