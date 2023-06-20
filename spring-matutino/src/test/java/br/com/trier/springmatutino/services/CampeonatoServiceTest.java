package br.com.trier.springmatutino.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.services.exceptions.AnoInvalido;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class CampeonatoServiceTest extends BaseTests{

	@Autowired
	CampeonatoService campService;
	
	@Test
	@DisplayName ("Teste busca descricao")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void findByDescTest() {
		var lista = campService.findByDescriptionIgnoreCase("campeonato 1");
		assertEquals("Campeonato 1", lista.get(0).getDescription());
		lista = campService.findByDescriptionContainsIgnoreCase("nato");
		assertEquals(4, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName ("Teste busca descricao inexistente")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void findByDescNonExistentTest() {
		var lista = campService.findByDescriptionIgnoreCase("camp 1");
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca id")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void findByIdTest() {
		var camp = campService.findById(1);
		assertEquals("Campeonato 1", camp.getDescription());
		assertTrue(camp != null);
	}
	
	@Test
	@DisplayName ("Teste busca id inexistente")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campService.findById(10));
		assertEquals("Campeonato 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca ano")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void findByAnoTest() {
		var lista = campService.findByAno(1995);
		assertEquals("Campeonato 1", lista.get(0).getDescription());
		assertEquals(1, lista.get(0).getId());
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca ano inexistente")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void findByAnoNonExistentTest() {
		var lista = campService.findByAno(1996);
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca ano e descricao")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void findByAnoEDescriptionTest() {
		var lista = campService.findByAnoAndDescription(1995, "Campeonato 1");
		assertEquals("Campeonato 1", lista.get(0).getDescription());
		assertEquals(1, lista.get(0).getId());
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca ano entre ano")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void findByAnoEntreAnoTest() {
		var lista = campService.findByAnoBetween(1995,2015);
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Teste cadastra camp")
	void cadastraCamp() {
		var camp = new Campeonato(null, "Campeonato Novo", 2005);
		campService.salvar(camp);
		var campNovo = campService.findById(1);
		assertEquals(2005, campNovo.getAno());
	}
	
	@Test
	@DisplayName ("Teste cadastra camp ano impossivel")
	void cadastraErrorCamp() {
		var exception = assertThrows(AnoInvalido.class, () -> campService.salvar(new Campeonato(null, "Campeonato Novo", 1985)));
		assertEquals("Ano 1985 inválido", exception.getMessage()); 
	}
	
	@Test
	@DisplayName ("Teste update camp")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void updateCamp() {
		var camp = new Campeonato(1, "Campeonato Novo", 2005);
		campService.update(camp);
		var campNovo = campService.findById(1);
		assertEquals(2005, campNovo.getAno());
	}
	
	@Test
	@DisplayName ("Teste update camp ano impossivel")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void updateCampAnoError() {
		var exception = assertThrows(AnoInvalido.class, () -> campService.salvar(new Campeonato(1, "Campeonato Novo", 1985)));
		assertEquals("Ano 1985 inválido", exception.getMessage()); 
	}
	
	@Test
	@DisplayName ("Teste delete camp")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void deleteCamp() {
		campService.delete(1);
		var lista = campService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName ("Teste delete camp inexistente")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void deleteNonExistentCamp() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campService.delete(10));
		assertEquals("Campeonato 10 não encontrado", exception.getMessage()); 
	}
	
	@Test
	@DisplayName ("Teste lista todos")
	@Sql({"classpath:/resources/sqls/camp.sql"})
	void listAllTest() {
		var lista = campService.listAll();
		assertEquals(4, lista.size());
	}
	
	@Test
	@DisplayName("Teste lista todos vazio")
	void listAllErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campService.listAll());
		assertEquals("Nenhum campeonato encontrado", exception.getMessage());
	}
}
