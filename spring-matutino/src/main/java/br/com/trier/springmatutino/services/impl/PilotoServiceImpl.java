package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.repositories.PilotoRepository;
import br.com.trier.springmatutino.services.PilotoService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;

@Service
public class PilotoServiceImpl implements PilotoService{

	@Autowired
	private PilotoRepository repository;
	
	@Override
	public Piloto salvar(Piloto piloto) {
		return repository.save(piloto);
	}

	@Override
	public List<Piloto> listAll() {
		List<Piloto> lista = repository.findAll();
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhum piloto cadastrado");
		}
		return lista;
	}

	@Override
	public Piloto findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Piloto %s não existe".formatted(id)));
	}

	@Override
	public Piloto update(Piloto piloto) {
		findById(piloto.getId());
		return repository.save(piloto);
	}

	@Override
	public void delete(Integer id) {
		Piloto piloto = findById(id);
		repository.delete(piloto);

	}

	@Override
	public List<Piloto> findByNameIgnoreCase(String name) {
		List<Piloto> lista = repository.findByNameIgnoreCase(name);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhum piloto encontrado com o nome %s".formatted(name));
		}
		return lista;
	}

	@Override
	public List<Piloto> findByNameContains(String name) {
		List<Piloto> lista = repository.findByNameContains(name);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhum piloto encontrado contem no nome %s".formatted(name));
		}
		return lista;
	}

	@Override
	public List<Piloto> findByPais(Pais pais) {
		List<Piloto> lista = repository.findByPais(pais);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhum piloto encontrado do pais %s".formatted(pais.getName()));
		}
		return lista;
	}

	@Override
	public List<Piloto> findByEquipe(Equipe equipe) {
		List<Piloto> lista = repository.findByEquipe(equipe);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhum piloto encontrado da equipe %s".formatted(equipe.getName()));
		}
		return lista;
	}

}
