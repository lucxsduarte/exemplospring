package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.repositories.PaisRepository;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PaisServiceImpl implements PaisService{

	@Autowired
	PaisRepository repository;
	
	@Override
	public Pais salvar(Pais pais) {
		findByname(pais);
		return repository.save(pais);
	}

	@Override
	public List<Pais> listAll() {
		List<Pais> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum pais encontrado");
		}
		return lista;
	}

	@Override
	public Pais findById(Integer id) {
		Optional<Pais> pais = repository.findById(id);
		return pais.orElseThrow(() -> new ObjetoNaoEncontrado("Pais %s não encontrado".formatted(id)));
	}

	@Override
	public Pais update(Pais pais) {
		findById(pais.getId());
		findByname(pais);
		return repository.save(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais pais = findById(id);
		if (pais != null) {
			repository.delete(pais);
		}
	}
	
	private void findByname (Pais pais) {
		Pais paisNovo = repository.findByName(pais.getName());
		if( paisNovo != null && paisNovo.getId() != pais.getId()) {
			throw new ViolacaoIntegridade("Pais já cadastrado: %s".formatted(pais.getName()));
		}
	}

	@Override
	public List<Pais> findByNameIgnoreCase(String name) {
		return repository.findByNameIgnoreCase(name);
	}

	@Override
	public List<Pais> findByNameContains(String name) {
		return repository.findByNameContains(name);
	}

}
