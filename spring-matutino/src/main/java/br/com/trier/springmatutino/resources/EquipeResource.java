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

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.services.EquipeService;

@RestController
@RequestMapping (value = "/equipe")
public class EquipeResource {

	@Autowired
	private EquipeService service;
	
	@PostMapping
	public ResponseEntity<Equipe> insert(@RequestBody Equipe equipe) {
		return ResponseEntity.ok(service.salvar(equipe));
	}
	
	@GetMapping
	public ResponseEntity<List<Equipe>> listarTodos(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<Equipe> buscaPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PutMapping ("/{id}")
	public ResponseEntity<Equipe> update(@PathVariable Integer id, @RequestBody Equipe equipe){
		equipe.setId(id);
		return ResponseEntity.ok(service.update(equipe));
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping ("/name/{name}")
	public ResponseEntity<List<Equipe>> buscaPorNome(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameIgnoreCase(name));
	}
	
	@GetMapping ("/name/contem/{name}")
	public ResponseEntity<List<Equipe>> buscaPorNomeContem(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name));
	}
}
