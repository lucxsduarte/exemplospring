package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.repositories.PistaRepository;
import br.com.trier.springmatutino.services.PistaService;

@Service
public class PistaServiceImpl implements PistaService{
	
	@Autowired
	PistaRepository repository;
	
	@Override
	public Pista salvar(Pista pista) {
		return repository.save(pista);
	}

	@Override
	public List<Pista> listAll() {
		return repository.findAll();
	}

	@Override
	public Pista findById(Integer id) {
		Optional<Pista> pista = repository.findById(id);
		return pista.orElse(null);
	}

	@Override
	public Pista update(Pista pista) {
		return repository.save(pista);
	}

	@Override
	public void delete(Integer id) {
		Pista pista = findById(id);
		if (pista != null) {
			repository.delete(pista);
		}
		
	}

	@Override
	public List<Pista> findByPais(Integer pais) {
		return repository.findByPais(pais);
	}

	@Override
	public List<Pista> findByTamanho(Double tamanho) {
		return repository.findByTamanho(tamanho);
	}

	@Override
	public List<Pista> findByTamanhoBetween(Double tamanho1, Double tamanho2) {
		return repository.findByTamanhoBetween(tamanho1, tamanho2);
	}

}
