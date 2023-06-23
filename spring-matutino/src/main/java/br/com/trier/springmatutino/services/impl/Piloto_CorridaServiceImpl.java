package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.Piloto_Corrida;
import br.com.trier.springmatutino.repositories.Piloto_CorridaRepository;
import br.com.trier.springmatutino.services.Piloto_CorridaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class Piloto_CorridaServiceImpl implements Piloto_CorridaService{
	
	@Autowired
	Piloto_CorridaRepository repository;
	
	private void validaPilotoCorrida(Piloto_Corrida piloto_corrida) {
		if(piloto_corrida.getColocacao() == null){
			throw new ViolacaoIntegridade("Colocacao nula!");
		}
		if(piloto_corrida.getColocacao() == 0) {
			throw new ViolacaoIntegridade("Colocacao zero!");
		}
	}
	
	@Override
	public Piloto_Corrida salvar(Piloto_Corrida piloto_corrida) {
		validaPilotoCorrida(piloto_corrida);
		return repository.save(piloto_corrida);
	}

	@Override
	public List<Piloto_Corrida> listAll() {
		List<Piloto_Corrida> lista = repository.findAll();
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhum piloto/corrida cadastrado");
		}
		return lista;
	}

	@Override
	public Piloto_Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("O piloto/corrida %s não foi cadastrado".formatted(id)));
	}

	@Override
	public Piloto_Corrida update(Piloto_Corrida piloto_corrida) {
		findById(piloto_corrida.getId());
		validaPilotoCorrida(piloto_corrida);
		return repository.save(piloto_corrida);
	}

	@Override
	public void delete(Integer id) {
		Piloto_Corrida piloto_corrida = findById(id);
		repository.delete(piloto_corrida);
	}

	@Override
	public List<Piloto_Corrida> findByPiloto(Piloto piloto) {
		List<Piloto_Corrida> lista = repository.findByPiloto(piloto);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Piloto %s não cadastrado".formatted(piloto.getId()));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByCorrida(Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByCorrida(corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Corrida %s não cadastrada".formatted(corrida.getId()));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacao(Integer colocacao) {
		List<Piloto_Corrida> lista = repository.findByColocacao(colocacao);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Colocação %s não encontrada".formatted(colocacao));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoAndCorrida(Integer colocacao, Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByColocacaoAndCorrida(colocacao, corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Não foi possivel encontrar a colocação %s na corrida %s".formatted(colocacao, corrida.getId()));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoBetweenAndCorrida(Integer colocacao1, Integer colocacao2,Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByColocacaoBetweenAndCorrida(colocacao1, colocacao2, corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Não foi possivel achar resultado entre as colocações %s e %s na corrida %s".formatted(colocacao1, colocacao2, corrida.getId()));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoLessThanEqualAndCorrida(Integer colocacao, Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByColocacaoLessThanEqualAndCorrida(colocacao, corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Não foi possivel encontrar a colocação menor ou igual a %s na corrida %s".formatted(colocacao, corrida.getId()));
		}
		return lista;
	}

	@Override
	public List<Piloto_Corrida> findByColocacaoGreaterThanEqualAndCorrida(Integer colocacao, Corrida corrida) {
		List<Piloto_Corrida> lista = repository.findByColocacaoLessThanEqualAndCorrida(colocacao, corrida);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Não foi possivel encontrar a colocação maior ou igual a %s na corrida %s".formatted(colocacao, corrida.getId()));
		}
		return lista;
	}

}
