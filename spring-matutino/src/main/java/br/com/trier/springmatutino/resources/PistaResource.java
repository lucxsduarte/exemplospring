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

import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PistaService;

@RestController
@RequestMapping (value = "/pista")
public class PistaResource {
	
	@Autowired
	private PistaService service;
	@Autowired
	private PaisService paisService;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<Pista> insert(@RequestBody Pista pista){
		paisService.findById(pista.getPais().getId());
		return ResponseEntity.ok(service.salvar(pista));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<Pista>> listarTodos(){
		return ResponseEntity.ok(service.listAll());
		
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping ("/{id}")
	public ResponseEntity<Pista> buscaPorCodigo(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping ("/{id}")
	public ResponseEntity<Pista> update(@PathVariable Integer id, @RequestBody Pista pista){
		paisService.findById(pista.getPais().getId());
		pista.setId(id);
		return ResponseEntity.ok(service.update(pista));
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping ("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/tamanho/{tamanho}")
	public ResponseEntity<List<Pista>> buscaPorTamanho(@PathVariable Integer tamanho){
		return ResponseEntity.ok(service.findByTamanho(tamanho));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/tamanho/entre/{tamanho1}/{tamanho2}")
	public ResponseEntity<List<Pista>> buscaPorTamanhoEntre(@PathVariable Integer tamanho1, @PathVariable Integer tamanho2){
		return ResponseEntity.ok(service.findByTamanhoBetween(tamanho1, tamanho2));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping ("/pais/{id_pais}")
	public ResponseEntity<List<Pista>> buscaPorPaisOrderByTamanhoDesc(@PathVariable Integer id_pais){
		return ResponseEntity.ok(service.findByPaisOrderByTamanhoDesc(paisService.findById(id_pais)));
	}
	

}
