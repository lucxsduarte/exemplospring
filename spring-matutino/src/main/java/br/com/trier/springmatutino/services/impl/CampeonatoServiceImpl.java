package br.com.trier.springmatutino.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.repositories.CampeonatoRepository;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.exceptions.AnoInvalido;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;

@Service
public class CampeonatoServiceImpl implements CampeonatoService{

	@Autowired
	CampeonatoRepository repository;
	
	@Override
	public Campeonato salvar(Campeonato campeonato) {
		verificaAno(campeonato);
		return repository.save(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
		List<Campeonato> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum campeonato encontrado");
		}
		return lista;
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> camp = repository.findById(id);
		return camp.orElseThrow(() -> new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(id)));
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		findById(campeonato.getId());
		verificaAno(campeonato);
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		Campeonato equipe = findById(id);
		if (equipe != null) {
			repository.delete(equipe);
		}
	}

	@Override
	public List<Campeonato> findByAno(Integer ano) {
		return repository.findByAno(ano);
	}

	@Override
	public List<Campeonato> findByAnoBetween(Integer ano1, Integer ano2) {
		return repository.findByAnoBetween(ano1, ano2);
	}

	@Override
	public List<Campeonato> findByDescriptionIgnoreCase(String description) {
		return repository.findByDescriptionIgnoreCase(description);
	}

	@Override
	public List<Campeonato> findByDescriptionContainsIgnoreCase(String description) {
		return repository.findByDescriptionContainsIgnoreCase(description);
	}

	@Override
	public List<Campeonato> findByAnoAndDescription(Integer ano, String description) {
		return repository.findByAnoAndDescription(ano, description);
	}
	
	public void verificaAno(Campeonato camp) {
		int anoAtual = LocalDate.now().getYear();
		int anoMaximo = anoAtual + 1;
		if (camp.getAno() <= 1990 || camp.getAno() >= anoMaximo) {
			throw new AnoInvalido("Ano %s inválido".formatted(camp.getAno()));
		}
	}


	
}
