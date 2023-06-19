package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
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
	@DisplayName ("Lista todos vazio")
	void listAllPaisNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.listAll());
		assertEquals("Nenhum pais encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste cadastra pais")
	void salvarPaisTest() {
		paisService.salvar(new Pais(null,"Argentina"));
		var lista = paisService.listAll();
		var paisCadastrado = paisService.findById(1);
		assertEquals(1, lista.size());
		assertEquals("Argentina", paisCadastrado.getName());
		assertEquals(1, paisCadastrado.getId());
	}
	
	@Test
	@DisplayName ("Teste cadastra pais nome repetido")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void salvarPaisNameErrorTest() {
		var exception = assertThrows(ViolacaoIntegridade.class, () -> paisService.salvar(new Pais(null,"Brasil")));
		assertEquals("Pais já cadastrado: Brasil", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste update pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updatePaisTest() {
		var pais = paisService.update(new Pais(1,"Chile"));
		paisService.update(pais);
		var paisNovo = paisService.findById(1);
		assertEquals(1, paisNovo.getId());
		assertEquals("Chile", paisNovo.getName());
	}
	
	@Test
	@DisplayName ("Teste update pais para nome ja existente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updatePaisNameErrorTest() {
		var exception = assertThrows(ViolacaoIntegridade.class, () -> paisService.update(new Pais(2,"Brasil")));
		assertEquals("Pais já cadastrado: Brasil", exception.getMessage());
	}
	
	@Test
	@DisplayName ("Teste update pais inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updatePaisNonExistentrTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.update(new Pais(4,"Equador")));
		assertEquals("Pais 4 não encontrado", exception.getMessage());
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
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.delete(10));
		var lista = paisService.listAll();
		assertEquals(2, lista.size());
		assertEquals("Pais 10 não encontrado", exception.getMessage());
	}
}
