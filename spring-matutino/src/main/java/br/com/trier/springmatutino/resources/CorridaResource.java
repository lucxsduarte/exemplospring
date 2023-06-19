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
import br.com.trier.springmatutino.services.CorridaService;

@RestController
@RequestMapping (value = "/corrida")
public class CorridaResource {
	
	@Autowired 
	private CorridaService service;
	
	@PostMapping
	public ResponseEntity<Corrida> insert(@RequestBody Corrida corrida){
		Corrida newCorrida = service.salvar(corrida);
		return newCorrida != null ? ResponseEntity.ok(newCorrida) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Corrida>> listaTodos(){
		List<Corrida> lista = service.listAll();
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}

	@GetMapping ("/{id}")
	public ResponseEntity<Corrida> buscaPorCodigo(@PathVariable Integer id){
		Corrida corrida = service.findById(id);
		return corrida != null ? ResponseEntity.ok(corrida) : ResponseEntity.badRequest().build();
	}
	
	@PutMapping ("/{id}")
	public ResponseEntity<Corrida> update(@PathVariable Integer id, @RequestBody Corrida corrida){
		corrida.setId(id);
		corrida = service.update(corrida);
		return corrida != null ? ResponseEntity.ok(corrida) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/pista/{pista}")
	public ResponseEntity<List<Corrida>> buscaPorPista(@PathVariable Integer pista){
		List<Corrida> lista = service.findByPista(pista);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/campeonato/{campeonato}")
	public ResponseEntity<List<Corrida>> buscaPorCampeonato(@PathVariable Integer campeonato){
		List<Corrida> lista = service.findByCampeonato(campeonato);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	/*@GetMapping("/data/{data}")
	public ResponseEntity<List<Corrida>> buscaPorData(@PathVariable LocalDate data){
		List<Corrida> lista = service.findByData(data);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/data/entre/{data1}/{data2}")
	public ResponseEntity<List<Corrida>> buscaPorDataEntre(@PathVariable LocalDate data1, @PathVariable LocalDate data2){
		List<Corrida> lista = service.findByDataBetween(data1, data2);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}*/
}
