package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class CorridaServiceTest extends BaseTests{

	@Autowired
	CorridaService corridaService;
	@Autowired
	PistaService pistaService;
	@Autowired
	CampeonatoService campService;
	
	@Test
	@DisplayName ("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByIdTest() {
		var corrida = corridaService.findById(1);
		assertTrue(corrida != null);
		assertEquals(1, corrida.getPista().getId());
	}
	
	@Test
	@DisplayName ("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findById(10));
		assertEquals("Corrida 10 não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste por Data ")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByDateTest() {
		var lista = corridaService.findByData(ZonedDateTime.of(2020,12,20,0,0,0,0, ZoneId.systemDefault()));
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName ("Teste por Data inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByDateErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findByData(ZonedDateTime.of(2023,12,20,10,30,0,0, ZoneId.systemDefault())));
		assertEquals("Nenhuma corrida encontrada na data", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste por Data entre")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByDateEntreTest() {
		var lista = corridaService.findByDataBetween(ZonedDateTime.of(2005,05,25,0,0,0,0, ZoneId.systemDefault()) ,ZonedDateTime.of(2023,12,25,0,0,0,0, ZoneId.systemDefault()));
		assertEquals(4, lista.size());
	}
	
	@Test
	@DisplayName ("Teste por Data entre vazio")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByDateEntreErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findByDataBetween(ZonedDateTime.of(2023,12,26,0,0,0,0, ZoneId.systemDefault()) ,ZonedDateTime.of(2023,12,27,0,0,0,0, ZoneId.systemDefault())));
		assertEquals("Nenhuma corrida encontrada entre a data 2023-12-26T00:00-03:00[America/Sao_Paulo] e a data 2023-12-27T00:00-03:00[America/Sao_Paulo]", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por pista")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByPistaTest() {
		var lista = corridaService.findByPista(pistaService.findById(2));
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por pista inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByPistaNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findByPista(pistaService.findById(4)));
		assertEquals("Nenhuma corrida encontrada nesta pista", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por campeonato")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByCampeonatoTest() {
		var lista = corridaService.findByCampeonato(campService.findById(2));
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por campeonato inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByCampeonatoNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findByCampeonato(campService.findById(5)));
		assertEquals("Nenhuma corrida encontrada neste campeonato", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Lista todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void listAllPaisTest() {
		var lista = corridaService.listAll();
		assertTrue(lista != null);
		assertEquals(4, lista.size());
	}
	
	@Test
	@DisplayName ("Lista todos vazio")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void listAllPaisErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.listAll());
		assertEquals("Nenhuma corrida cadastrada", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste cadastra corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void cadastraTest() {
		var corrida = new Corrida(null, ZonedDateTime.of(2020,12,20,0,0,0,0, ZoneId.systemDefault()), pistaService.findById(1), campService.findById(4));
		corridaService.salvar(corrida);
		var corridaNova = corridaService.findById(1);
		assertEquals(4, corridaNova.getCampeonato().getId());
	}
	
	@Test
	@DisplayName ("Teste cadastra corrida campeonato null")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void cadastraCampeonatoErrorTest() {
		var corrida = new Corrida(null, ZonedDateTime.of(2020,12,20,0,0,0,0, ZoneId.systemDefault()), pistaService.findById(1), null);
		var exception = assertThrows(ViolacaoIntegridade.class, () -> corridaService.salvar(corrida));
		assertEquals("Campeonato não pode ser nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste cadastra corrida data null")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void cadastraDataErrorTest() {
		var corrida = new Corrida(null, null, pistaService.findById(1), null);
		var exception = assertThrows(ViolacaoIntegridade.class, () -> corridaService.salvar(corrida));
		assertEquals("Data inválida", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste cadastra corrida data sem campeonato")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void cadastraErrorTest() {
		var corrida = new Corrida(null, ZonedDateTime.of(2023,04,20,10,30,0,0, ZoneId.systemDefault()), pistaService.findById(1), campService.findById(1));
		var exception = assertThrows(ViolacaoIntegridade.class, () -> corridaService.salvar(corrida));
		assertEquals("Ano da corrida diferente do ano do campeonato", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste update corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void updateTest() {
		var corrida = new Corrida(1, ZonedDateTime.of(2023,07,28,10,30,0,0, ZoneId.systemDefault()), pistaService.findById(1), campService.findById(5));
		corridaService.update(corrida);
		var corridaNova = corridaService.findById(1);
		assertEquals(5, corridaNova.getCampeonato().getId());
	}
	
	@Test
	@DisplayName ("Teste delete corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void deleteTest() {
		corridaService.delete(1);
		var lista = corridaService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Teste delete inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	@Sql({"classpath:/resources/sqls/camp.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void deleteNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.delete(9));
		assertEquals("Corrida 9 não encontrada", exception.getMessage());
	}
	
}
