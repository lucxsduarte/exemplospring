package br.com.trier.springmatutino.services.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.repositories.CorridaRepository;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class CorridaServiceImpl implements CorridaService{

	@Autowired
	CorridaRepository repository;
	
	private void validaData(Corrida corrida) {
		ZonedDateTime dataAtual = ZonedDateTime.now(ZoneId.systemDefault());
		if(corrida.getData() == null || corrida.getData().isBefore(dataAtual)) {
			throw new ViolacaoIntegridade("Data inválida");
		}
	}

	@Override
	public Corrida salvar(Corrida corrida) {
		validaData(corrida);
		return repository.save(corrida);
	}

	@Override
	public List<Corrida> listAll() {
		List<Corrida> lista = repository.findAll();
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida cadastrada");
		}
		return lista;
	}

	@Override
	public Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Corrida %s não encontrada".formatted(id)));
	}

	@Override
	public Corrida update(Corrida corrida) {
		findById(corrida.getId());
		validaData(corrida);
		return repository.save(corrida);
	}

	@Override
	public void delete(Integer id) {
		Corrida corrida = findById(id);
		repository.delete(corrida);
		
	}

	@Override
	public List<Corrida> findByPista(Pista pista) {
		List<Corrida> lista = repository.findByPista(pista);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida encontrada nesta pista");
		}
		return lista;
	}

	@Override
	public List<Corrida> findByCampeonato(Campeonato campeonato) {
		List<Corrida> lista = repository.findByCampeonato(campeonato);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida encontrada neste campeonato");
		}
		return lista;
	}

	@Override
	public List<Corrida> findByData(ZonedDateTime data) {
		List<Corrida> lista = repository.findByData(data);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida encontrada na data".formatted(data));
		}
		return lista;
	}

	@Override
	public List<Corrida> findByDataBetween(ZonedDateTime data1, ZonedDateTime data2) {
		List<Corrida> lista = repository.findByDataBetween(data1, data2);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida encontrada entre a data %s e a data %s".formatted(data1, data2));
		}
		return lista;
	}

	
}
