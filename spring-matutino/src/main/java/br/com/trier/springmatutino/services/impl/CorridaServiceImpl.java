package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.repositories.CorridaRepository;
import br.com.trier.springmatutino.services.CorridaService;

@Service
public class CorridaServiceImpl implements CorridaService{

	@Autowired
	CorridaRepository repository;

	@Override
	public Corrida salvar(Corrida corrida) {
		return repository.save(corrida);
	}

	@Override
	public List<Corrida> listAll() {
		return repository.findAll();
	}

	@Override
	public Corrida findById(Integer id) {
		Optional<Corrida> corrida = repository.findById(id);
		return corrida.orElse(null);
	}

	@Override
	public Corrida update(Corrida corrida) {
		return repository.save(corrida);
	}

	@Override
	public void delete(Integer id) {
		Corrida corrida = findById(id);
		if (corrida != null) {
			repository.delete(corrida);
		}
	}

	@Override
	public List<Corrida> findByPista(Integer pista) {
		return repository.findByPista(pista);
	}

	@Override
	public List<Corrida> findByCampeonato(Integer campeonato) {
		return repository.findByCampeonato(campeonato);
	}

	/*@Override
	public List<Corrida> findByData(LocalDate data) {
		return repository.findByData(data);
	}

	@Override
	public List<Corrida> findByDataBetween(LocalDate data1, LocalDate data2) {
		return repository.findByDataBetween(data1, data2);
	}*/

	
}
