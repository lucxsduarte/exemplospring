package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Corrida;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer>{

	List<Corrida> findByPista(Integer pista);
	
	List<Corrida> findByCampeonato(Integer campeonato);
	
	//List<Corrida> findByData(LocalDate data);
	
	//List<Corrida> findByDataBetween(LocalDate data1, LocalDate data2);
}
