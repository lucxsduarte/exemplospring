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
import br.com.trier.springmatutino.services.PilotoService;

@RestController
@RequestMapping (value = "/piloto")
public class PilotoResource {
	
	@Autowired
	PilotoService service;
	
	@PostMapping
	public ResponseEntity<Piloto> insert(@RequestBody Piloto piloto){
		Piloto newPiloto = service.salvar(piloto);
		return newPiloto != null ? ResponseEntity.ok(newPiloto) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Piloto>> listarTodos(@PathVariable Integer id){
		List<Piloto> lista = service.listAll();
		return lista.size() > 1 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<Piloto> buscaPorId(@PathVariable Integer id){
		Piloto piloto = service.findById(id);
		return piloto != null ? ResponseEntity.ok(piloto) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping ("/nome/{name}")
	public ResponseEntity<List<Piloto>> buscaPorNome(@PathVariable String name){
		List<Piloto> lista = service.findByNameIgnoreCase(name);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping ("/nome/contem/{name}")
	public ResponseEntity<List<Piloto>> buscaPorNomeContem(@PathVariable String name){
		List<Piloto> lista = service.findByNameContains(name);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping ("/equipe/{equipe}")
	public ResponseEntity<List<Piloto>> buscaPorEquipe(@PathVariable Integer equipe){
		List<Piloto> lista = service.findByEquipe(equipe);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping ("/pais/{pais}")
	public ResponseEntity<List<Piloto>> buscaPoPais(@PathVariable Integer pais){
		List<Piloto> lista = service.findByEquipe(pais);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@PutMapping ("/{id}")
	public ResponseEntity<Piloto> udate(@PathVariable Integer id, @RequestBody Piloto piloto){
		piloto.setId(id);
		piloto = service.update(piloto);
		return piloto != null ? ResponseEntity.ok(piloto) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
