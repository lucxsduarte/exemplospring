package br.com.trier.springmatutino.resources;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.domain.dto.PaisDTO;
import br.com.trier.springmatutino.domain.dto.PilotoDTO;
import br.com.trier.springmatutino.domain.dto.Piloto_CorridaDTO;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.EquipeService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PilotoService;
import br.com.trier.springmatutino.services.Piloto_CorridaService;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.utils.DateUtils;

@RestController
@RequestMapping (value = "/relatorio")
public class RelatorioResource {
	
	@Autowired 
	private CorridaService corridaService;
	@Autowired 
	private PistaService pistaService;
	@Autowired 
	private CampeonatoService campService;
	@Autowired
	private Piloto_CorridaService pilotoCorridaService;
	@Autowired
	private PilotoService pilotoService;
	@Autowired
	private PaisService paisService;
	@Autowired
	private EquipeService equipeService;
	
	@GetMapping("/camp/{id}")
	public ResponseEntity<CampeonatoDTO> buscaPorCodigoCamp(@PathVariable Integer id) {
		Campeonato campeonato = campService.findById(id);
		return ResponseEntity.ok(campeonato.toDto());
	}
	
	@GetMapping ("/corrida/{id}")
	public ResponseEntity<CorridaDTO> buscaPorCodigoCorrida(@PathVariable Integer id){
		Corrida corrida = corridaService.findById(id);
		return ResponseEntity.ok(corrida.toDTO());
	}
	
	@GetMapping ("/piloto-corrida/{id}")
	public ResponseEntity<Piloto_CorridaDTO> buscaPorIdPilotoCorrida(@PathVariable Integer id){
		return ResponseEntity.ok(pilotoCorridaService.findById(id).toDTO());
	}
	
	@GetMapping ("/pais/{id}")
	public ResponseEntity<PaisDTO> buscaPorCodigoPais(@PathVariable Integer id) {
		Pais pais = paisService.findById(id);
		return ResponseEntity.ok(pais.toDto());
	}
	
	@GetMapping ("/equipe/{id}")
	public ResponseEntity<Equipe> buscaPorCodigoEquipe(@PathVariable Integer id) {
		return ResponseEntity.ok(equipeService.findById(id));
	}
	
	@GetMapping ("/pista/{id}")
	public ResponseEntity<Pista> buscaPorCodigo(@PathVariable Integer id){
		return ResponseEntity.ok(pistaService.findById(id));
	}
	
	@GetMapping ("/piloto/{id}")
	public ResponseEntity<PilotoDTO> buscaPorId(@PathVariable Integer id){
		Piloto piloto = pilotoService.findById(id);
		return ResponseEntity.ok(piloto.toDto());
	}
	
	@GetMapping ("/camp")
	public ResponseEntity<List<CampeonatoDTO>> listarTodosCamp() {
		return ResponseEntity.ok(campService.listAll().stream().map(camp -> camp.toDto()).toList());
	}
	
	@GetMapping ("/corrida")
	public ResponseEntity<List<CorridaDTO>> listaTodosCorrida(){
		return ResponseEntity.ok(corridaService.listAll().stream().map((corrida) -> corrida.toDTO()).toList());
	}
	
	@GetMapping("/piloto-corrida")
	public ResponseEntity<List<Piloto_CorridaDTO>> listarTodosPilotoCorrida(){
		return ResponseEntity.ok(pilotoCorridaService.listAll().stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping ("/equipe")
	public ResponseEntity<List<Equipe>> listarTodosEquipe(){
		return ResponseEntity.ok(equipeService.listAll());
	}
	
	@GetMapping ("/pista")
	public ResponseEntity<List<Pista>> listarTodos(){
		return ResponseEntity.ok(pistaService.listAll());
	}
	
	@GetMapping ("/piloto")
	public ResponseEntity<List<PilotoDTO>> listarTodos(@PathVariable Integer id){
		return ResponseEntity.ok(pilotoService.listAll().stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@GetMapping("/camp/ano/{ano}")
	public ResponseEntity<List<CampeonatoDTO>> buscarPorAno(@PathVariable Integer ano) {
		return ResponseEntity.ok(campService.findByAno(ano).stream().map(camp -> camp.toDto()).toList());
	}
	
	@GetMapping("/camp/ano/entre/{ano1}/{ano2}")
	public ResponseEntity<List<CampeonatoDTO>> buscaPorAnoEntre(@PathVariable Integer ano1, @PathVariable Integer ano2) {
		return ResponseEntity.ok(campService.findByAnoBetween(ano1, ano2).stream().map(camp -> camp.toDto()).toList());
	}
	
	@GetMapping ("/camp/desc/{description}")
	public ResponseEntity<List<CampeonatoDTO>> buscaPorDescriptionIgnoreCase(@PathVariable String description) {
		return ResponseEntity.ok(campService.findByDescriptionIgnoreCase(description).stream().map(camp -> camp.toDto()).toList());
	}
	
	@GetMapping ("/camp/desc/contem/{description}")
	public ResponseEntity<List<CampeonatoDTO>> buscaPorDescriptionContem(@PathVariable String description) {
		return ResponseEntity.ok(campService.findByDescriptionContainsIgnoreCase(description).stream().map(camp -> camp.toDto()).toList());
	}
	
	@GetMapping ("/camp/ano/desc/{description}/{ano}")
	public ResponseEntity<List<CampeonatoDTO>> buscaPorAnoAndDescription(@PathVariable Integer ano, @PathVariable String description) {
		return ResponseEntity.ok(campService.findByAnoAndDescription(ano, description).stream().map(camp -> camp.toDto()).toList());
	}
	
	@GetMapping("/piloto-corrida/piloto/{piloto}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorPiloto (@PathVariable Integer id_piloto){
		return ResponseEntity.ok(pilotoCorridaService.findByPiloto(pilotoService.findById(id_piloto)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/piloto-corrida/corrida/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorCorrida (@PathVariable Integer id_corrida){
		return ResponseEntity.ok(pilotoCorridaService.findByCorrida(corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/piloto-corrida/colocacao/{colocacao}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacao (@PathVariable Integer colocacao){
		return ResponseEntity.ok(pilotoCorridaService.findByColocacao(colocacao).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/piloto-corrida/colocacao/entre/{colocacao1}/{colocacao2}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoEntre (@PathVariable Integer colocacao1, @PathVariable Integer colocacao2, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(pilotoCorridaService.findByColocacaoBetweenAndCorrida(colocacao1, colocacao2, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/piloto-corrida/colocacao/menor/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoMenor (@PathVariable Integer colocacao, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(pilotoCorridaService.findByColocacaoLessThanEqualAndCorrida(colocacao, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/piloto-corrida/colocacao/maior/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoMaior (@PathVariable Integer colocacao, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(pilotoCorridaService.findByColocacaoGreaterThanEqualAndCorrida(colocacao, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping("/piloto-corrida/colocacao/corrida/{colocacao}/{corrida}")
	public ResponseEntity<List<Piloto_CorridaDTO>> buscaPorColocacaoCorrida (@PathVariable Integer colocacao, @PathVariable Integer id_corrida){
		return ResponseEntity.ok(pilotoCorridaService.findByColocacaoAndCorrida(colocacao, corridaService.findById(id_corrida)).stream().map(pilotoC -> pilotoC.toDTO()).toList());
	}
	
	@GetMapping ("/equipe/name/{name}")
	public ResponseEntity<List<Equipe>> buscaPorNomeEquipe(@PathVariable String name) {
		return ResponseEntity.ok(equipeService.findByNameIgnoreCase(name));
	}
	
	@GetMapping ("/equipe/name/contem/{name}")
	public ResponseEntity<List<Equipe>> buscaPorNomeContemEquipe(@PathVariable String name) {
		return ResponseEntity.ok(equipeService.findByNameContainsIgnoreCase(name));
	}
	
	@GetMapping("/pista/tamanho/{tamanho}")
	public ResponseEntity<List<Pista>> buscaPorTamanho(@PathVariable Integer tamanho){
		return ResponseEntity.ok(pistaService.findByTamanho(tamanho));
	}
	
	@GetMapping("/pista/tamanho/entre/{tamanho1}/{tamanho2}")
	public ResponseEntity<List<Pista>> buscaPorTamanhoEntre(@PathVariable Integer tamanho1, @PathVariable Integer tamanho2){
		return ResponseEntity.ok(pistaService.findByTamanhoBetween(tamanho1, tamanho2));
	}
	
	@GetMapping ("/pista/pais/{id_pais}")
	public ResponseEntity<List<Pista>> buscaPorPaisOrderByTamanhoDesc(@PathVariable Integer id_pais){
		return ResponseEntity.ok(pistaService.findByPaisOrderByTamanhoDesc(paisService.findById(id_pais)));
	}
	
	@GetMapping ("/piloto/nome/{name}")
	public ResponseEntity<List<PilotoDTO>> buscaPorNome(@PathVariable String name){
		return ResponseEntity.ok(pilotoService.findByNameIgnoreCase(name).stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@GetMapping ("/piloto/nome/contem/{name}")
	public ResponseEntity<List<PilotoDTO>> buscaPorNomeContem(@PathVariable String name){
		return ResponseEntity.ok(pilotoService.findByNameContains(name).stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@GetMapping ("/piloto/equipe/{equipe}")
	public ResponseEntity<List<PilotoDTO>> buscaPorEquipe(@PathVariable Integer id_equipe){
		return ResponseEntity.ok(pilotoService.findByEquipe(equipeService.findById(id_equipe)).stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@GetMapping ("/piloto/pais/{pais}")
	public ResponseEntity<List<PilotoDTO>> buscaPoPais(@PathVariable Integer id_pais){
		return ResponseEntity.ok(pilotoService.findByPais(paisService.findById(id_pais)).stream().map(piloto -> piloto.toDto()).toList());
	}
	
	@GetMapping ("/pais/name/{name}")
	public ResponseEntity<List<PaisDTO>> buscaPorNomePais(@PathVariable String name) {
		return ResponseEntity.ok(paisService.findByNameIgnoreCase(name).stream().map(pais -> pais.toDto()).toList());
		
	}
	
	@GetMapping ("/pais/name/contem/{name}")
	public ResponseEntity<List<PaisDTO>> buscaPorNomeContemPais(@PathVariable String name) {
		return ResponseEntity.ok(paisService.findByNameContains(name).stream().map(pais -> pais.toDto()).toList());
	}
	
	@GetMapping("/corrida/pista/{id_pista}")
	public ResponseEntity<List<CorridaDTO>> buscaPorPista(@PathVariable Integer id_pista){
		return ResponseEntity.ok(corridaService.findByPista(pistaService.findById(id_pista)).stream().map(corrida -> corrida.toDTO()).toList());
	}
	
	@GetMapping("/corrida/campeonato/{id_campeonato}")
	public ResponseEntity<List<CorridaDTO>> buscaPorCampeonato(@PathVariable Integer id_campeonato){
		return ResponseEntity.ok(corridaService.findByCampeonato(campService.findById(id_campeonato)).stream().map(corrida -> corrida.toDTO()).toList());
	}
	
	@GetMapping("/corrida/data/{data}")
	public ResponseEntity<List<CorridaDTO>> buscaPorData(@PathVariable String data){
		return ResponseEntity.ok(corridaService.findByData(DateUtils.strToZonedDateTime(data)).stream().map(corrida -> corrida.toDTO()).toList());
	}
	
	@GetMapping("/corrida/data/entre/{data1}/{data2}")
	public ResponseEntity<List<CorridaDTO>> buscaPorDataEntre(@PathVariable ZonedDateTime data1, @PathVariable ZonedDateTime data2){
		return ResponseEntity.ok(corridaService.findByDataBetween(data1, data2).stream().map(corrida -> corrida.toDTO()).toList());
	}
}
