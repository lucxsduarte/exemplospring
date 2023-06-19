package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Pista;

public interface PistaService {

	Pista salvar (Pista campeonato);
	
	List<Pista> listAll();
	
	Pista findById(Integer id);
	
	Pista update(Pista campeonato);
	
	void delete(Integer id);
	
	List<Pista> findByPais(Integer pais);
	
	List<Pista> findByTamanho(Double tamanho);
	
	List<Pista> findByTamanhoBetween(Double tamanho1, Double tamanho2);
	
}
