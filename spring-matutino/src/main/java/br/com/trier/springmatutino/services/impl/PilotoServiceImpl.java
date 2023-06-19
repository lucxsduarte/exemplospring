package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.repositories.PilotoRepository;
import br.com.trier.springmatutino.services.PilotoService;

@Service
public class PilotoServiceImpl implements PilotoService{

	@Autowired
	PilotoRepository repository;
	
	@Override
	public Piloto salvar(Piloto piloto) {
		return repository.save(piloto);
	}

	@Override
	public List<Piloto> listAll() {
		return repository.findAll();
	}

	@Override
	public Piloto findById(Integer id) {
		Optional<Piloto> piloto = repository.findById(id);
		return piloto.orElse(null);
	}

	@Override
	public Piloto update(Piloto piloto) {
		return repository.save(piloto);
	}

	@Override
	public void delete(Integer id) {
		Piloto piloto = findById(id);
		if(piloto != null) {
			repository.delete(piloto);
		}
	}

	@Override
	public List<Piloto> findByNameIgnoreCase(String name) {
		return repository.findByNameIgnoreCase(name);
	}

	@Override
	public List<Piloto> findByNameContains(String name) {
		return repository.findByNameContains(name);
	}

	@Override
	public List<Piloto> findByPais(Integer pais) {
		return repository.findByPais(pais);
	}

	@Override
	public List<Piloto> findByEquipe(Integer equipe) {
		return repository.findByEquipe(equipe);
	}

}
