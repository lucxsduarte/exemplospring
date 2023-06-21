package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;

public interface PistaService {

	Pista salvar (Pista campeonato);
	
	List<Pista> listAll();
	
	Pista findById(Integer id);
	
	Pista update(Pista campeonato);
	
	void delete(Integer id);
	
	List<Pista> findByPaisOrderByTamanhoDesc(Pais pais);
	
	List<Pista> findByTamanho(Integer tamanho);
	
	List<Pista> findByTamanhoBetween(Integer tamanho1, Integer tamanho2);
	
}
