package br.com.trier.springmatutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.services.UserService;

@RestController
@RequestMapping (value = "/usuarios")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User user) {
		User newUser = service.salvar(user);
		return newUser!=null ? ResponseEntity.ok(newUser) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> buscaPorCodigo(@PathVariable Integer id) {
		User user = service.findById(id);
		return user!=null ? ResponseEntity.ok(user) : ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<User>> listarTodos() {
		List<User> lista = service.listAll();
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User user) {
		user.setId(id);
		user = service.update(user);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<User>> buscaPorNome(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCase(name));
	}
	
	@GetMapping("/name/termina/{name}")
	public ResponseEntity<List<User>> buscaPorNomeTerminaCom(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameEndsWith(name));
	}
	
}
