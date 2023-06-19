package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer>{

	List<Campeonato> findByDescriptionIgnoreCase(String description);

	List<Campeonato> findByDescriptionContainsIgnoreCase(String description);
	
	List<Campeonato> findByAno(Integer ano);
	
	List<Campeonato> findByAnoBetween(Integer ano1, Integer ano2);
	
	List<Campeonato> findByAnoAndDescription(Integer ano, String description);
}
