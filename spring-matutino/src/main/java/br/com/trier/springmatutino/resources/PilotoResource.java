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

import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.services.EquipeService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PilotoService;

@RestController
@RequestMapping (value = "/piloto")
public class PilotoResource {
	
	@Autowired
	private PilotoService service;
	@Autowired
	private PaisService paisService;
	@Autowired
	private EquipeService equipeService;
	
	@PostMapping
	public ResponseEntity<Piloto> insert(@RequestBody Piloto piloto){
		return ResponseEntity.ok(service.salvar(piloto));
	}
	
	@GetMapping
	public ResponseEntity<List<Piloto>> listarTodos(@PathVariable Integer id){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<Piloto> buscaPorId(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping ("/nome/{name}")
	public ResponseEntity<List<Piloto>> buscaPorNome(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCase(name));
	}
	
	@GetMapping ("/nome/contem/{name}")
	public ResponseEntity<List<Piloto>> buscaPorNomeContem(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContains(name));
	}
	
	@GetMapping ("/equipe/{equipe}")
	public ResponseEntity<List<Piloto>> buscaPorEquipe(@PathVariable Integer id_equipe){
		return ResponseEntity.ok(service.findByEquipe(equipeService.findById(id_equipe))) ;
	}
	
	@GetMapping ("/pais/{pais}")
	public ResponseEntity<List<Piloto>> buscaPoPais(@PathVariable Integer id_pais){
		return ResponseEntity.ok(service.findByPais(paisService.findById(id_pais)));
	}
	
	@PutMapping ("/{id}")
	public ResponseEntity<Piloto> udate(@PathVariable Integer id, @RequestBody Piloto piloto){
		paisService.findById(piloto.getPais().getId());
		equipeService.findById(piloto.getEquipe().getId());
		piloto.setId(id);
		return ResponseEntity.ok(service.update(piloto));
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
