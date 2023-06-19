package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Pista;
import jakarta.transaction.Transactional;

@Transactional
public class PistaServiceTest extends BaseTests{
	
	@Autowired
	PistaService pistaService;
	
	@Test
	@DisplayName ("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByIdTest() {
		var pista = pistaService.findById(1);
		assertTrue(pista != null);
		assertEquals(7.5, pista.getTamanho());
		assertEquals(1, pista.getPais());
	}
	
	@Test
	@DisplayName ("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByIdNonExistentTest() {
		var pista = pistaService.findById(5);
		assertTrue(pista == null);
	} 
	
	@Test
	@DisplayName ("Teste busca por pais")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByPaisTest() {
		var lista = pistaService.findByPais(1);
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName ("Teste busca por pais inexistente")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByPaisNonExistentTest() {
		var lista = pistaService.findByPais(4);
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por tamanho")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByTamanhoTest() {
		var lista = pistaService.findByTamanho(7.5);
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getPais());
	}
	
	@Test
	@DisplayName ("Teste busca por tamanho não existente")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByTamanhoNonExistentTest() {
		var lista = pistaService.findByTamanho(9.0);
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por tamanho entre tamanho")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByTamanhoEntreTamanhoTest() {
		var lista = pistaService.findByTamanhoBetween(6.0, 9.0);
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getPais());
	}
	
	@Test
	@DisplayName("Teste cadastra pista")
	void cadastraPistaTest() {
		var pista = new Pista(null,1,6.0);
		pistaService.salvar(pista);
		var pistaNova = pistaService.findById(1);
		assertEquals(6.0, pistaNova.getTamanho());
	}
	
	@Test
	@DisplayName("Teste update pista")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void updatePistaTest() {
		var pista = new Pista(1,1,6.0);
		pistaService.update(pista);
		var pistaNova = pistaService.findById(1);
		assertEquals(6.0, pistaNova.getTamanho());
	}
	
	@Test
	@DisplayName("Teste delete pista")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void deletePistaTest() {
		pistaService.delete(1);
		var lista = pistaService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Teste delete pista inexistente")
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void deletePistaNonExistenteTest() {
		pistaService.delete(5);
		var lista = pistaService.listAll();
		assertEquals(4, lista.size());
	}


}
