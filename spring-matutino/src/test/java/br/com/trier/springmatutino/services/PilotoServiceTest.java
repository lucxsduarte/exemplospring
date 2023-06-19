package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Piloto;
import jakarta.transaction.Transactional;

@Transactional
public class PilotoServiceTest extends BaseTests{

	@Autowired
	PilotoService pilotoService;
	
	@Test
	@DisplayName ("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByIdTest() {
		var piloto = pilotoService.findById(1);
		assertTrue(piloto != null);
		assertEquals("Jorge", piloto.getName());
	}
	
	@Test
	@DisplayName ("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByIdNonExistentTest() {
		var piloto = pilotoService.findById(7);
		assertTrue(piloto == null);
	}
	
	@Test
	@DisplayName ("Teste busca por nome")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByNameTest() {
		var lista = pilotoService.findByNameIgnoreCase("Lucas");
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getEquipe());
		lista = pilotoService.findByNameContains("Jorge");
		assertEquals(2, lista.get(0).getEquipe());
	}
	
	@Test
	@DisplayName ("Teste busca por nome inexistente")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByNameNonExistentTest() {
		var lista = pilotoService.findByNameIgnoreCase("Arro");
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por pais")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByPaisTest() {
		var lista = pilotoService.findByPais(1);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por Equipe")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByEquipeTest() {
		var lista = pilotoService.findByEquipe(2);
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName ("Teste cadastra piloto")
	void cadastraTest() {
		var piloto = new Piloto(null, "Matheus", 1, 1);
		pilotoService.salvar(piloto);
		var novoPiloto = pilotoService.findById(1);
		assertEquals(1, novoPiloto.getEquipe());
	}
	
	@Test
	@DisplayName ("Teste update piloto")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void updateTest() {
		var piloto = new Piloto(1, "Matheus", 1, 1);
		pilotoService.update(piloto);
		var novoPiloto = pilotoService.findById(1);
		assertEquals(1, novoPiloto.getEquipe());
	}
	
	@Test
	@DisplayName ("Teste delete piloto")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void deleteTest() {
		pilotoService.delete(1);
		var lista = pilotoService.listAll();
		assertEquals(5, lista.size());
	}
	
	@Test
	@DisplayName ("Teste delete piloto inexistente")
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void deleteNonExistentTest() {
		pilotoService.delete(9);
		var lista = pilotoService.listAll();
		assertEquals(6, lista.size());
	}
}
