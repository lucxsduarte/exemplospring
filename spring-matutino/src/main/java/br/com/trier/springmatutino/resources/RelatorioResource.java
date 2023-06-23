package br.com.trier.springmatutino.resources;


import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.dto.ColocacaoPorCorrida;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.domain.dto.CorridaPaisAnoDTO;
import br.com.trier.springmatutino.domain.dto.CorridasPorCampeonato;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;

@RestController
@RequestMapping (value = "/relatorios")
public class RelatorioResource {
	
	@Autowired
	private PaisService paisService;
	@Autowired
	private PistaService pistaService;
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private CampeonatoService campService;
	
	@GetMapping ("/corrida-por-pais-ano/{paisId}/{ano}")
	public ResponseEntity<CorridaPaisAnoDTO>  findCorridaPorPaisEAno(@PathVariable Integer paisId, @PathVariable Integer ano){
		Pais pais = paisService.findById(paisId);
		
		List<CorridaDTO> corridasDTOS = pistaService.findByPaisOrderByTamanhoDesc(pais).stream().flatMap(pista -> {
				try {
					return corridaService.findByPista(pista).stream();
				} catch (ObjetoNaoEncontrado e) {
					return Stream.empty();
				}
		})
		.filter(corrida -> corrida.getData().getYear() == ano)
		.map(Corrida::toDTO)
		.toList();
		
		return ResponseEntity.ok(new CorridaPaisAnoDTO(ano, pais.getName(), corridasDTOS));
	}
	
	@GetMapping ("corrida-por-campeonato/{campId}")
	public ResponseEntity<CorridasPorCampeonato> findCorridasPorCampeonato(@PathVariable Integer campId){
		Campeonato camp = campService.findById(campId);
		
		List<CorridaDTO> corridasDTOS = corridaService.findByCampeonato(camp).stream().flatMap(corrida -> {
			try {
				return corridaService.findByCampeonato(camp).stream();
			} catch (ObjetoNaoEncontrado e) {
				return Stream.empty();
			}
		})
		.map(Corrida::toDTO)
		.toList();
		
		return ResponseEntity.ok(new CorridasPorCampeonato(camp.getDescription(), corridasDTOS));
	}
	
	
	
}