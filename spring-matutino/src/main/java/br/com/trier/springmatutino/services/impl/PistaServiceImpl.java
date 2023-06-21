package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.repositories.PistaRepository;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PistaServiceImpl implements PistaService{
	
	@Autowired
	private PistaRepository repository;
	
	private void validarPista(Pista pista) {
		if(pista.getTamanho() == null || pista.getTamanho() <= 0) {
			throw new ViolacaoIntegridade("Tamanho inválido");
		}
		
	}
	
	@Override
	public Pista salvar(Pista pista) {
		validarPista(pista);
		return repository.save(pista);
	}

	@Override
	public List<Pista> listAll() {
		List<Pista> lista = repository.findAll();
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhuma pista cadastrada");
		}
		return lista;
	}

	@Override
	public Pista findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Pista %s não existe".formatted(id)));
	}

	@Override
	public Pista update(Pista pista) {
		findById(pista.getId());
		validarPista(pista);
		return repository.save(pista);
	}

	@Override
	public void delete(Integer id) {
		Pista pista = findById(id);
		repository.delete(pista);
		
	}

	@Override
	public List<Pista> findByPaisOrderByTamanhoDesc(Pais pais) {
		List<Pista> lista = repository.findByPaisOrderByTamanhoDesc(pais);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhuma pista encontrada no pais %s".formatted(pais.getName()));
		}
		return lista;
	}

	@Override
	public List<Pista> findByTamanho(Integer tamanho) {
		List<Pista> lista = repository.findByTamanho(tamanho);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhuma pista encontrada o tamanho de %s km".formatted(tamanho));
		}
		return lista;
	}

	@Override
	public List<Pista> findByTamanhoBetween(Integer tamanho1, Integer tamanho2) {
		List<Pista> lista = repository.findByTamanhoBetween(tamanho1, tamanho2);
		if(lista.size() == 0 ) {
			throw new ObjetoNaoEncontrado("Nenhuma pista encontrada entre os tamanhos %s km e %s km".formatted(tamanho1, tamanho2));
		}
		return lista;
	}


}
