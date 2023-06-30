package br.com.trier.springmatutino.services;

import java.time.ZonedDateTime;
import java.util.List;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;

public interface CorridaService {

	Corrida salvar (Corrida corrida);
	List<Corrida> listAll();
	Corrida findById(Integer id);
	Corrida update(Corrida corrida);
	void delete(Integer id);
	List<Corrida> findByPista(Pista pista);
	List<Corrida> findByCampeonato(Campeonato campeonato);
	List<Corrida> findByData(ZonedDateTime data);
	List<Corrida> findByDataBetween(ZonedDateTime data1, ZonedDateTime data2);
}
