package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Piloto_Corrida;
import br.com.trier.springmatutino.repositories.Piloto_CorridaRepository;
import br.com.trier.springmatutino.services.Piloto_CorridaService;

@Service
public class Piloto_CorridaServiceImpl implements Piloto_CorridaService{
	
	@Autowired
	Piloto_CorridaRepository repository;
	
	@Override
	public Piloto_Corrida salvar(Piloto_Corrida piloto_corrida) {
		return repository.save(piloto_corrida);
	}

	@Override
	public List<Piloto_Corrida> listAll() {
		return repository.findAll();
	}

	@Override
	public Piloto_Corrida findById(Integer id) {
		Optional<Piloto_Corrida> piloto_corrida = repository.findById(id);
		return piloto_corrida.orElse(null);
	}

	@Override
	public Piloto_Corrida update(Piloto_Corrida piloto_corrida) {
		return repository.save(piloto_corrida);
	}

	@Override
	public void delete(Integer id) {
		Piloto_Corrida piloto_corrida = findById(id);
		if(piloto_corrida != null) {
			repository.delete(piloto_corrida);
		}
		
	}

	@Override
	public List<Piloto_Corrida> findByPiloto(Integer piloto) {
		return repository.findByPiloto(piloto);
	}

	@Override
	public List<Piloto_Corrida> findByCorrida(Integer corrida) {
		return repository.findByCorrida(corrida);
	}

	@Override
	public List<Piloto_Corrida> findByColocacao(Integer colocacao) {
		return repository.findByColocacao(colocacao);
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoAndCorrida(Integer colocacao, Integer corrida) {
		return repository.findByColocacaoAndCorrida(colocacao, corrida);
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoBetweenAndCorrida(Integer colocacao1, Integer colocacao2,Integer corrida) {
		return repository.findByColocacaoBetweenAndCorrida(colocacao1, colocacao2, corrida);
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoLessThanEqualAndCorrida(Integer colocacao, Integer corrida) {
		return repository.findByColocacaoLessThanEqualAndCorrida(colocacao, corrida);
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoGreaterThanEqualAndCorrida(Integer colocacao, Integer corrida) {
		return repository.findByColocacaoGreaterThanEqualAndCorrida(colocacao, corrida);
	}

}
