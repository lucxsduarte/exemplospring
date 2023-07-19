package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.repositories.UserRepository;
import br.com.trier.springmatutino.services.UserService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;
	
	@Override
	public User salvar(User user) {
		validaEmail(user);
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {
		List<User> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum usuário encontrado");
		}
		return lista;
	}

	@Override
	public User findById(Integer id) {
		Optional<User> objeto = repository.findById(id);
		return objeto.orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado".formatted(id)));
	}

	@Override
	public User update(User user) {
		findById(user.getId());
		validaEmail(user);
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);
	}
	
	public void validaEmail(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent() && !repository.findByEmail(user.getEmail()).orElse(null).getId().equals(user.getId())) {
            throw new ViolacaoIntegridade("O email %s já está sendo usado por outro usuário".formatted(user.getEmail()));
        }
    }

	@Override
	public List<User> findByNameIgnoreCase(String name) {
		List<User> lista = repository.findByNameIgnoreCase(name);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum usuário se chama %s".formatted(name));
		}
		return lista;
	}
	
	@Override
	public List<User> findByNameEndsWith(String name) {
		List<User> lista = repository.findByNameEndsWith(name);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum usuário termina com %s".formatted(name));
		}
		return lista;
	}


	
}
