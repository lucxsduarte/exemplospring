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

import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.services.PistaService;

@RestController
@RequestMapping (value = "/pista")
public class PistaResource {
	
	@Autowired
	private PistaService service;
	
	@PostMapping
	public ResponseEntity<Pista> insert(@RequestBody Pista pista){
		Pista newPista = service.salvar(pista);
		return newPista != null ? ResponseEntity.ok(newPista) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Pista>> listarTodos(){
		List<Pista> lista = service.listAll();
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
		
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<Pista> buscaPorCodigo(@PathVariable Integer id){
		Pista pista = service.findById(id);
		return pista != null ? ResponseEntity.ok(pista) : ResponseEntity.noContent().build();
	}
	
	@PutMapping ("/{id}")
	public ResponseEntity<Pista> update(@PathVariable Integer id, @RequestBody Pista pista){
		pista.setId(id);
		pista = service.update(pista);
		return pista != null ? ResponseEntity.ok(pista) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/tamanho/{tamanho}")
	public ResponseEntity<List<Pista>> buscaPorTamanho(@PathVariable Double tamanho){
		List<Pista> lista = service.findByTamanho(tamanho);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/tamanho/entre/{tamanho1}/{tamanho2}")
	public ResponseEntity<List<Pista>> buscaPorTamanhoEntre(@PathVariable Double tamanho1, @PathVariable Double tamanho2){
		List<Pista> lista = service.findByTamanhoBetween(tamanho1, tamanho2);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping ("/pais/{pais}")
	public ResponseEntity<List<Pista>> buscaPorPais(@PathVariable Integer pais){
		List<Pista> lista = service.findByPais(pais);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	

}
