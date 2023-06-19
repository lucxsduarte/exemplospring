package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Piloto;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Integer>{

	List<Piloto> findByNameIgnoreCase(String name);
	
	List<Piloto> findByNameContains(String name);
	
	List<Piloto> findByPais(Integer pais);
	
	List<Piloto> findByEquipe(Integer equipe);
}
