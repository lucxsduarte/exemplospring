package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Equipe;
import jakarta.transaction.Transactional;

@Transactional
public class EquipeServiceTest extends BaseTests{
	
	@Autowired
	EquipeService equipeService;
	
	@Test
	@DisplayName ("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findById() {
		var equipe = equipeService.findById(1);
		assertTrue(equipe != null);
		assertEquals(1, equipe.getId());
		assertEquals("Lotus", equipe.getName());
	}
	
	@Test
	@DisplayName ("Teste busca por nome")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByName() {
		var lista = equipeService.findByNameIgnoreCase("Mercedes");
		assertEquals(1, lista.size());
		assertEquals("Mercedes", lista.get(0).getName());
		lista = equipeService.findByNameContains("e");
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por nome inexistente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByNameNonExistent() {
		var lista = equipeService.findByNameIgnoreCase("merc");
		assertEquals(0, lista.size());
	}

	@Test
	@DisplayName ("Teste lista todos")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void listAllTest() {
		var lista = equipeService.listAll();
		assertEquals(4, lista.size());
	}
	
	@Test
	@DisplayName ("Teste cadastra equipe")
	void salvarEquipeTest() {
		var equipe = equipeService.salvar(new Equipe(null, "Equipe Nova"));
		var lista = equipeService.listAll();
		var equipeNova = equipeService.findById(1);
		assertEquals("Equipe Nova", lista.get(0).getName());
		assertEquals("Equipe Nova", equipeNova.getName());
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName ("Teste update equipe")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateEquipeTest() {
		var equipe = equipeService.update(new Equipe(1, "Equipe Nova"));
		var lista = equipeService.listAll();
		var equipeNova = equipeService.findById(1);
		assertEquals(1, lista.get(0).getId());
		assertEquals("Equipe Nova", lista.get(0).getName());
		assertEquals(4, lista.size());
	}
	
	@Test
	@DisplayName ("Teste deleta equipe")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void deleteEquipeTest() {
		equipeService.delete(1);
		var lista = equipeService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Teste deleta equipe não existente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void deleteEquipeNonExistentTest() {
		equipeService.delete(5);
		var lista = equipeService.listAll();
		assertEquals(4, lista.size());
	}
}
