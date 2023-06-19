package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Corrida;

public interface CorridaService {

	Corrida salvar (Corrida corrida);
	
	List<Corrida> listAll();
	
	Corrida findById(Integer id);
	
	Corrida update(Corrida corrida);
	
	void delete(Integer id);
	
	List<Corrida> findByPista(Integer pista);
	
	List<Corrida> findByCampeonato(Integer campeonato);
	
	//List<Corrida> findByData(LocalDate data);
	
	//List<Corrida> findByDataBetween(LocalDate data1, LocalDate data2);
}
