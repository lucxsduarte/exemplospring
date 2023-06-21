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
import br.com.trier.springmatutino.domain.dto.EquipeDTO;
import br.com.trier.springmatutino.services.EquipeService;

@RestController
@RequestMapping (value = "/equipe")
public class EquipeResource {

	@Autowired
	private EquipeService service;
	
	@PostMapping
	public ResponseEntity<EquipeDTO> insert(@RequestBody Equipe equipe) {
		Equipe newEquipe = service.salvar(equipe);
		return ResponseEntity.ok(newEquipe.toDto());
	}
	
	@GetMapping
	public ResponseEntity<List<EquipeDTO>> listarTodos(){
		return ResponseEntity.ok(service.listAll().stream().map(equipe -> equipe.toDto()).toList());
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<EquipeDTO> buscaPorCodigo(@PathVariable Integer id) {
		Equipe newEquipe = service.findById(id);
		return ResponseEntity.ok(newEquipe.toDto());
	}
	
	@PutMapping ("/{id}")
	public ResponseEntity<EquipeDTO> update(@PathVariable Integer id, @RequestBody EquipeDTO equipeDTO){
		Equipe equipe = new Equipe(equipeDTO);
		equipe.setId(id);
		equipe = service.update(equipe);
		return ResponseEntity.ok(equipe.toDto());
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping ("/name/{name}")
	public ResponseEntity<List<EquipeDTO>> buscaPorNome(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map(equipe -> equipe.toDto()).toList());
	}
	
	@GetMapping ("/name/contem/{name}")
	public ResponseEntity<List<EquipeDTO>> buscaPorNomeContem(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name).stream().map(equipe -> equipe.toDto()).toList());
	}
}
