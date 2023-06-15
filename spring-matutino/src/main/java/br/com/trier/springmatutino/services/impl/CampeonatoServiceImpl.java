package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.repositories.CampeonatoRepository;
import br.com.trier.springmatutino.services.CampeonatoService;

@Service
public class CampeonatoServiceImpl implements CampeonatoService{

	@Autowired
	CampeonatoRepository repository;
	
	@Override
	public Campeonato salvar(Campeonato campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
		return repository.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> equipe = repository.findById(id);
		return equipe.orElse(null);
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		Campeonato equipe = findById(id);
		if (equipe != null) {
			repository.delete(equipe);
		}
	}
	
}
