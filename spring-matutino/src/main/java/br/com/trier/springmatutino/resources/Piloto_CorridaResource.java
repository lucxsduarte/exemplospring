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

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.Piloto_Corrida;
import br.com.trier.springmatutino.domain.dto.Piloto_CorridaDTO;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PilotoService;
import br.com.trier.springmatutino.services.Piloto_CorridaService;

@RestController
@RequestMapping (value = "/piloto_corrida")
public class Piloto_CorridaResource {

	@Autowired
	private Piloto_CorridaService service;
	@Autowired
	private PilotoService pilotoService;
	@Autowired
	private CorridaService corridaService;
	
	@PostMapping
	public ResponseEntity<Piloto_CorridaDTO> insert (@RequestBody Piloto_CorridaDTO piloto_corridaDTO){
		return ResponseEntity.ok(service.salvar(new Piloto_Corrida(
				piloto_corridaDTO, 
				pilotoService.findById(piloto_corridaDTO.getId_piloto()), 
				corridaService.findById(piloto_corridaDTO.getId_corrida()),
				piloto_corridaDTO.getColocacao()))
				.toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<Piloto_CorridaDTO>> listarTodos(){
		return ResponseEntity.ok(service.listAll().stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<Piloto_CorridaDTO> buscaPorId(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping("/piloto/{piloto}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorPiloto (@PathVariable Integer id_piloto){
		return ResponseEntity.ok(service.findByPiloto(pilotoService.findById(id_piloto)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/corrida/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorCorrida (@PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByCorrida(corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/colocacao/{colocacao}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacao (@PathVariable Integer colocacao){
		return ResponseEntity.ok(service.findByColocacao(colocacao).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/colocacao/entre/{colocacao1}/{colocacao2}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoEntre (@PathVariable Integer colocacao1, @PathVariable Integer colocacao2, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByColocacaoBetweenAndCorrida(colocacao1, colocacao2, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/colocacao/menor/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoMenor (@PathVariable Integer colocacao, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByColocacaoLessThanEqualAndCorrida(colocacao, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/colocacao/maior/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoMaior (@PathVariable Integer colocacao, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByColocacaoGreaterThanEqualAndCorrida(colocacao, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/colocacao/corrida/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoCorrida (@PathVariable Integer colocacao, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(service.findByColocacaoAndCorrida(colocacao, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Piloto_CorridaDTO> update (@PathVariable Integer id, @RequestBody Piloto_CorridaDTO piloto_corridaDTO){
		Corrida corrida = corridaService.findById(piloto_corridaDTO.getId_corrida());
		Piloto piloto = pilotoService.findById(piloto_corridaDTO.getId_piloto());
		Piloto_Corrida pilotoCorrida = new Piloto_Corrida(piloto_corridaDTO, piloto, corrida, piloto_corridaDTO.getColocacao());
		pilotoCorrida.setId(id);
		return ResponseEntity.ok(service.update(pilotoCorrida).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	
}
