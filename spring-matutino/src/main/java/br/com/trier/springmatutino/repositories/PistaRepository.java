package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Integer>{

	List<Pista> findByPaisOrderByTamanhoDesc(Pais pais);
	
	List<Pista> findByTamanho(Integer tamanho);
	
	List<Pista> findByTamanhoBetween(Integer tamanho1, Integer tamanho2);
	
}
