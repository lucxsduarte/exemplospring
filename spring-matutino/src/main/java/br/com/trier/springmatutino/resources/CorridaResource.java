package br.com.trier.springmatutino.resources;

import java.time.ZonedDateTime;
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

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PistaService;

@RestController
@RequestMapping (value = "/corrida")
public class CorridaResource {
	
	@Autowired 
	private CorridaService service;
	@Autowired 
	private PistaService pistaService;
	@Autowired 
	private CampeonatoService campService;
	
	@PostMapping
	public ResponseEntity<CorridaDTO> insert(@RequestBody CorridaDTO corridaDTO){
		Corrida newCorrida = service.salvar(new Corrida(corridaDTO));
		return ResponseEntity.ok(newCorrida.toDto());
	}
	
	@GetMapping
	public ResponseEntity<List<CorridaDTO>> listaTodos(){
		return ResponseEntity.ok(service.listAll().stream().map(corrida -> corrida.toDto()).toList());
	}

	@GetMapping ("/{id}")
	public ResponseEntity<CorridaDTO> buscaPorCodigo(@PathVariable Integer id){
		Corrida corrida = service.findById(id);
		return ResponseEntity.ok(corrida.toDto());
	}
	
	@PutMapping ("/{id}")
	public ResponseEntity<CorridaDTO> update(@PathVariable Integer id, @RequestBody CorridaDTO corridaDTO){
		Corrida corrida = new Corrida(corridaDTO);
		corrida.setId(id);
		corrida = service.update(corrida);
		return ResponseEntity.ok(corrida.toDto());
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/pista/{id_pista}")
	public ResponseEntity<List<CorridaDTO>> buscaPorPista(@PathVariable Integer id_pista){
		return ResponseEntity.ok(service.findByPista(pistaService.findById(id_pista)).stream().map(corrida -> corrida.toDto()).toList());
	}
	
	@GetMapping("/campeonato/{id_campeonato}")
	public ResponseEntity<List<CorridaDTO>> buscaPorCampeonato(@PathVariable Integer id_campeonato){
		return ResponseEntity.ok(service.findByCampeonato(campService.findById(id_campeonato)).stream().map(corrida -> corrida.toDto()).toList());
	}
	
	@GetMapping("/data/{data}")
	public ResponseEntity<List<CorridaDTO>> buscaPorData(@PathVariable ZonedDateTime data){
		return ResponseEntity.ok(service.findByData(data).stream().map(corrida -> corrida.toDto()).toList());
	}
	
	@GetMapping("/data/entre/{data1}/{data2}")
	public ResponseEntity<List<CorridaDTO>> buscaPorDataEntre(@PathVariable ZonedDateTime data1, @PathVariable ZonedDateTime data2){
		return ResponseEntity.ok(service.findByDataBetween(data1, data2).stream().map(corrida -> corrida.toDto()).toList());
	}
}
