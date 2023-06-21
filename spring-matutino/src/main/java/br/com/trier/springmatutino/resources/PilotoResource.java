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
import br.com.trier.springmatutino.domain.dto.PilotoDTO;
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
	public ResponseEntity<PilotoDTO> insert(@RequestBody PilotoDTO pilotoDTO){
		Piloto newPiloto = service.salvar(new Piloto(pilotoDTO));
		return ResponseEntity.ok(newPiloto.toDto());
	}
	
	@GetMapping
	public ResponseEntity<List<PilotoDTO>> listarTodos(@PathVariable Integer id){
		return ResponseEntity.ok(service.listAll().stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<PilotoDTO> buscaPorId(@PathVariable Integer id){
		Piloto piloto = service.findById(id);
		return ResponseEntity.ok(piloto.toDto());
	}
	
	@GetMapping ("/nome/{name}")
	public ResponseEntity<List<PilotoDTO>> buscaPorNome(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@GetMapping ("/nome/contem/{name}")
	public ResponseEntity<List<PilotoDTO>> buscaPorNomeContem(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContains(name).stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@GetMapping ("/equipe/{equipe}")
	public ResponseEntity<List<PilotoDTO>> buscaPorEquipe(@PathVariable Integer id_equipe){
		return ResponseEntity.ok(service.findByEquipe(equipeService.findById(id_equipe)).stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@GetMapping ("/pais/{pais}")
	public ResponseEntity<List<PilotoDTO>> buscaPoPais(@PathVariable Integer id_pais){
		return ResponseEntity.ok(service.findByPais(paisService.findById(id_pais)).stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@PutMapping ("/{id}")
	public ResponseEntity<PilotoDTO> udate(@PathVariable Integer id, @RequestBody PilotoDTO pilotoDTO){
		Piloto piloto = new Piloto(pilotoDTO);
		piloto.setId(id);
		piloto = service.update(piloto);
		return ResponseEntity.ok(piloto.toDto());
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
