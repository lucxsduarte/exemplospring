package br.com.trier.springmatutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;
import br.com.trier.springmatutino.services.CampeonatoService;

@RestController
@RequestMapping (value = "/camp")
public class CampeonatoResource {
	
	@Autowired
	private CampeonatoService service;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<CampeonatoDTO> insert(@RequestBody CampeonatoDTO campeonato) {
		Campeonato newCampeonato = service.salvar(new Campeonato(campeonato));
		return ResponseEntity.ok(newCampeonato.toDto());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<CampeonatoDTO> buscaPorCodigo(@PathVariable Integer id) {
		Campeonato campeonato = service.findById(id);
		return ResponseEntity.ok(campeonato.toDto());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<CampeonatoDTO>> listarTodos() {
		return ResponseEntity.ok(service.listAll().stream().map(camp -> camp.toDto()).toList());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<CampeonatoDTO> update(@PathVariable Integer id, @RequestBody CampeonatoDTO campeonatoDTO) {
		Campeonato campeonato = new Campeonato(campeonatoDTO);
		campeonato.setId(id);
		campeonato = service.update(campeonato);
		return ResponseEntity.ok(campeonato.toDto());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/ano/{ano}")
	public ResponseEntity<List<CampeonatoDTO>> buscarPorAno(@PathVariable Integer ano) {
		return ResponseEntity.ok(service.findByAno(ano).stream().map(camp -> camp.toDto()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/ano/entre/{ano1}/{ano2}")
	public ResponseEntity<List<CampeonatoDTO>> buscaPorAnoEntre(@PathVariable Integer ano1, @PathVariable Integer ano2) {
		return ResponseEntity.ok(service.findByAnoBetween(ano1, ano2).stream().map(camp -> camp.toDto()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping ("/desc/{description}")
	public ResponseEntity<List<CampeonatoDTO>> buscaPorDescriptionIgnoreCase(@PathVariable String description) {
		return ResponseEntity.ok(service.findByDescriptionIgnoreCase(description).stream().map(camp -> camp.toDto()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping ("/desc/contem/{description}")
	public ResponseEntity<List<CampeonatoDTO>> buscaPorDescriptionContem(@PathVariable String description) {
		return ResponseEntity.ok(service.findByDescriptionContainsIgnoreCase(description).stream().map(camp -> camp.toDto()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping ("/ano/desc/{description}/{ano}")
	public ResponseEntity<List<CampeonatoDTO>> buscaPorAnoAndDescription(@PathVariable Integer ano, @PathVariable String description) {
		return ResponseEntity.ok(service.findByAnoAndDescription(ano, description).stream().map(camp -> camp.toDto()).toList());
	}


}
