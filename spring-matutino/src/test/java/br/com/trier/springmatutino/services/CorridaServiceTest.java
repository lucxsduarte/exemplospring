package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Corrida;
import jakarta.transaction.Transactional;

@Transactional
public class CorridaServiceTest extends BaseTests{

	@Autowired
	CorridaService corridaService;
	
	@Test
	@DisplayName ("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByIdTest() {
		var corrida = corridaService.findById(1);
		assertTrue(corrida != null);
		assertEquals(1, corrida.getPista());
	}
	
	@Test
	@DisplayName ("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByIdNonExistentTest() {
		var corrida = corridaService.findById(5);
		assertTrue(corrida == null);
	}
	
	//@Test
	//@DisplayName ("Teste por Data")
	//@Sql({"classpath:/resources/sqls/corrida.sql"})
	//void findByDateTest() {
	//	var corrida = corridaService.findByData(LocalDate.of(2023, 03, 25));
	//	assertTrue(corrida == null);
	//}
	
	@Test
	@DisplayName ("Teste busca por pista")
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByPistaTest() {
		var lista = corridaService.findByPista(2);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por pista inexistente")
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByPistaNonExistentTest() {
		var lista = corridaService.findByPista(5);
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por campeonato")
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByCampeonatoTest() {
		var lista = corridaService.findByCampeonato(2);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por campeonato inexistente")
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByCampeonatoNonExistentTest() {
		var lista = corridaService.findByCampeonato(5);
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Teste cadastra corrida")
	void cadastraTest() {
		var corrida = new Corrida(null,1,3);
		corridaService.salvar(corrida);
		var corridaNova = corridaService.findById(1);
		assertEquals(3, corridaNova.getCampeonato());
	}
	
	@Test
	@DisplayName ("Teste update corrida")
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void updateTest() {
		var corrida = new Corrida(1, 1, 3);
		corridaService.update(corrida);
		var corridaNova = corridaService.findById(1);
		assertEquals(3, corridaNova.getCampeonato());
	}
	
	@Test
	@DisplayName ("Teste delete corrida")
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void deleteTest() {
		corridaService.delete(1);
		var lista = corridaService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Teste delete inexistente")
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void deleteNonExistentTest() {
		corridaService.delete(5);
		var lista = corridaService.listAll();
		assertEquals(4, lista.size());
	}
	
}
