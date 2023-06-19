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

import br.com.trier.springmatutino.domain.Piloto_Corrida;
import br.com.trier.springmatutino.services.Piloto_CorridaService;

@RestController
@RequestMapping (value = "/piloto_corrida")
public class Piloto_CorridaResource {

	@Autowired
	private Piloto_CorridaService service;
	
	@PostMapping
	public ResponseEntity<Piloto_Corrida> insert (@RequestBody Piloto_Corrida piloto_corrida){
		Piloto_Corrida newPilotoCorrida = service.salvar(piloto_corrida);
		return newPilotoCorrida != null ? ResponseEntity.ok(newPilotoCorrida) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Piloto_Corrida>> listarTodos(){
		List<Piloto_Corrida> lista = service.listAll();
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<Piloto_Corrida> buscaPorId(@PathVariable Integer id){
		Piloto_Corrida piloto_corrida = service.findById(id);
		return piloto_corrida != null ? ResponseEntity.ok(piloto_corrida) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/piloto/{piloto}")
	public ResponseEntity<List<Piloto_Corrida>> buscaPorPiloto (@PathVariable Integer piloto){
		List<Piloto_Corrida> lista = service.findByPiloto(piloto);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/corrida/{corrida}")
	public ResponseEntity<List<Piloto_Corrida>> buscaPorCorrida (@PathVariable Integer corrida){
		List<Piloto_Corrida> lista = service.findByCorrida(corrida);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/colocacao/{colocacao}")
	public ResponseEntity<List<Piloto_Corrida>> buscaPorColocacao (@PathVariable Integer colocacao){
		List<Piloto_Corrida> lista = service.findByCorrida(colocacao);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/colocacao/entre/{colocacao1}/{colocacao2}/{corrida}")
	public ResponseEntity<List<Piloto_Corrida>> buscaPorColocacaoEntre (@PathVariable Integer colocacao1, @PathVariable Integer colocacao2, @PathVariable Integer corrida){
		List<Piloto_Corrida> lista = service.findByColocacaoBetweenAndCorrida(colocacao1, colocacao2, corrida);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/colocacao/menor/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_Corrida>> buscaPorColocacaoMenor (@PathVariable Integer colocacao, @PathVariable Integer corrida){
		List<Piloto_Corrida> lista = service.findByColocacaoLessThanEqualAndCorrida(colocacao, corrida);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/colocacao/maior/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_Corrida>> buscaPorColocacaoMaior (@PathVariable Integer colocacao, @PathVariable Integer corrida){
		List<Piloto_Corrida> lista = service.findByColocacaoGreaterThanEqualAndCorrida(colocacao, corrida);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/colocacao/corrida/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_Corrida>> buscaPorColocacaoCorrida (@PathVariable Integer colocacao, @PathVariable Integer corrida){
		List<Piloto_Corrida> lista = service.findByColocacaoAndCorrida(colocacao, corrida);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Piloto_Corrida> update (@PathVariable Integer id, @RequestBody Piloto_Corrida piloto_corrida){
		piloto_corrida.setId(id);
		piloto_corrida = service.update(piloto_corrida);
		return piloto_corrida != null ? ResponseEntity.ok(piloto_corrida) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	
}
