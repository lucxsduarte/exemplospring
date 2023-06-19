package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Pais;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests{

	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName ("Busca por nome")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameTest() {
		var lista = paisService.findByNameIgnoreCase("brasil");
		assertEquals(1, lista.size());
		assertEquals("Brasil", lista.get(0).getName());
		lista = paisService.findByNameContains("a");
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Busca por nome inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameNonExistentTest() {
		var lista = paisService.findByNameIgnoreCase("brasi");
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Busca por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTest() {
		var pais = paisService.findById(1);
		assertTrue(pais != null);
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName ("Lista todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllPaisTest() {
		var lista = paisService.listAll();
		assertTrue(lista != null);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste cadastra pais")
	void salvarPaisTest() {
		var pais = paisService.salvar(new Pais(null,"Argentina"));
		var lista = paisService.listAll();
		var paisCadastrado = paisService.findById(1);
		assertEquals(1, lista.size());
		assertEquals("Argentina", paisCadastrado.getName());
		assertEquals(1, paisCadastrado.getId());
	}
	
	@Test
	@DisplayName ("Teste update pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updatePaisTest() {
		var pais = paisService.update(new Pais(1,"Chile"));
		paisService.update(pais);
		var paisNovo = paisService.findById(1);
		assertEquals(1, pais.getId());
		assertEquals("Chile", pais.getName());
	}
	
	@Test
	@DisplayName("Teste deleta pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deletePaisTest() {
		paisService.delete(1);
		var lista = paisService.listAll();
		var pais = paisService.findById(2);
		assertEquals(1, lista.size());
		assertEquals("Bolivia", pais.getName());
	}
	
	@Test
	@DisplayName("Teste deleta pais inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deletePaisNonExistentTest() {
		paisService.delete(3);
		var lista = paisService.listAll();
		assertEquals(2, lista.size());
	}
}
