package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class PilotoServiceTest extends BaseTests{

	@Autowired
	PilotoService pilotoService;
	@Autowired
	PaisService paisService;
	@Autowired
	EquipeService equipeService;
	
	@Test
	@DisplayName ("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByIdTest() {
		var piloto = pilotoService.findById(1);
		assertTrue(piloto != null);
		assertEquals("Jorge", piloto.getName());
	}
	
	@Test
	@DisplayName ("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findById(10));
		assertEquals("Piloto 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por nome")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByNameTest() {
		var lista = pilotoService.findByNameIgnoreCase("Lucas");
		assertEquals(1, lista.size());
		assertEquals("Lotus", lista.get(0).getEquipe().getName());
	}
	
	@Test
	@DisplayName ("Teste busca por nome contem")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByNameContemTest() {
		var lista = pilotoService.findByNameContains("a");
		assertEquals(5, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por nome contem inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByNameContemErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findByNameContains("x"));
		assertEquals("Nenhum piloto encontrado contem no nome x", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por nome inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByNameNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findByNameIgnoreCase("Pablo"));
		assertEquals("Nenhum piloto encontrado com o nome Pablo", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByPaisTest() {
		var lista = pilotoService.findByPais(paisService.findById(1));
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por pais inexisnte")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByPaisErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findByPais(paisService.findById(5)));
		assertEquals("Nenhum piloto encontrado do pais Portugal", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste busca por Equipe")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByEquipeTest() {
		var lista = pilotoService.findByEquipe(equipeService.findById(1));
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por Equipe inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByEquipeErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findByEquipe(equipeService.findById(5)));
		assertEquals("Nenhum piloto encontrado da equipe McLaren", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Lista todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void listAllPaisTest() {
		var lista = pilotoService.listAll();
		assertTrue(lista != null);
		assertEquals(6, lista.size());
	}
	
	@Test
	@DisplayName ("Lista todos vazio")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void listAllPaisErrorTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.listAll());
		assertEquals("Nenhum piloto cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste cadastra piloto")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void cadastraTest() {
		var piloto = new Piloto(null, "Matheus", paisService.findById(1), equipeService.findById(1));
		pilotoService.salvar(piloto);
		var novoPiloto = pilotoService.findById(1);
		assertEquals("Lotus", novoPiloto.getEquipe().getName());
	}
	
	@Test
	@DisplayName ("Teste update piloto")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void updateTest() {
		var piloto = new Piloto(1, "Matheus", paisService.findById(1), equipeService.findById(1));
		pilotoService.update(piloto);
		var novoPiloto = pilotoService.findById(1);
		assertEquals("Matheus", novoPiloto.getName());
	}
	
	@Test
	@DisplayName ("Teste delete piloto")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void deleteTest() {
		pilotoService.delete(1);
		var lista = pilotoService.listAll();
		assertEquals(5, lista.size());
	}
	
	@Test
	@DisplayName ("Teste delete piloto inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void deleteNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.delete(9));
		assertEquals("Piloto 9 não existe", exception.getMessage());
	}
}
