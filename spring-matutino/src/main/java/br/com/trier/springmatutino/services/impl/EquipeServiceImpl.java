package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.repositories.EquipeRepository;
import br.com.trier.springmatutino.services.EquipeService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class EquipeServiceImpl implements EquipeService{

	@Autowired
	EquipeRepository repository;
	
	@Override
	public Equipe salvar(Equipe equipe) {
		findByName(equipe);
		return repository.save(equipe);
	}

	@Override
	public List<Equipe> listAll() {
		List<Equipe> lista = repository.findAll();
		if( lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhuma equipe encontrada");
		}
		return lista;
	}

	@Override
	public Equipe findById(Integer id) {
		Optional<Equipe> equipe = repository.findById(id);
		return equipe.orElseThrow(() -> new ObjetoNaoEncontrado("Equipe %s não encontrada".formatted(id)));
	}

	@Override
	public Equipe update(Equipe equipe) {
		findById(equipe.getId());
		findByName(equipe);
		return repository.save(equipe);
	}

	@Override
	public void delete(Integer id) {
		Equipe equipe = findById(id);
		if (equipe != null) {
			repository.delete(equipe);
		}
	}
	
	private void findByName (Equipe equipe) {
		Equipe equipeNova = repository.findByName(equipe.getName());
		if ( equipeNova != null && equipeNova.getId() != equipe.getId()) {
			throw new ViolacaoIntegridade("Equipe já cadastrada: %s".formatted(equipe.getName()));
		}
	}

	@Override
	public List<Equipe> findByNameIgnoreCase(String name) {
		return repository.findByNameIgnoreCase(name);
	}

	@Override
	public List<Equipe> findByNameContainsIgnoreCase(String name) {
		return repository.findByNameContainsIgnoreCase(name);
	}

}
