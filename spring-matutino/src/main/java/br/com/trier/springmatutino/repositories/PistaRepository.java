package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Pista;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Integer>{

	List<Pista> findByPais(Integer pais);
	
	List<Pista> findByTamanho(Double tamanho);
	
	List<Pista> findByTamanhoBetween(Double tamanho1, Double tamanho2);
}
