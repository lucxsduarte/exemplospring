package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;


public interface PilotoService {

	Piloto salvar (Piloto piloto);
	
	List<Piloto> listAll();
	
	Piloto findById(Integer id);
	
	Piloto update(Piloto piloto);
	
	void delete(Integer id);
	
	List<Piloto> findByNameIgnoreCase(String name);
	
	List<Piloto> findByNameContains(String name);
	
	List<Piloto> findByPais(Pais pais);
	
	List<Piloto> findByEquipe(Equipe equipe);
	
}
