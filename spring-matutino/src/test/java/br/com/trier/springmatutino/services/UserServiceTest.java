package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.User;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{

	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void findByIdTest() {
		var usuario = userService.findById(1);
		assertTrue(usuario != null);
		assertEquals(1, usuario.getId());
		assertEquals("Usuario teste 1", usuario.getName());
		assertEquals("test1@test.com.br", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar usuario por ID inexistente")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void findByIdNonExistentTest() {
		var usuario = userService.findById(10);
		assertTrue(usuario == null);
	}
	
	@Test
	@DisplayName("Teste lista todos")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void listAllTest() {
		var lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	} 
	
	@Test
	@DisplayName("Teste cadastra usuario")
	void insertUserTest()	{
		var usuario = userService.salvar(new User(null, "Lucas", "lucas@gmail.com", "123"));
		var usuario2 = userService.salvar(new User(null, "usu2", "usu2@gmail.com", "123"));
		var lista = userService.listAll();
		assertEquals(1, usuario.getId());
		assertEquals(2, usuario2.getId());
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste altera usuario")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void updateUserTest()	{
		var usuario = new User(1, "altera", "altera", "altera");
		userService.update(usuario);
		var usuarioAlterado = userService.findById(1);
		assertEquals(1, usuario.getId());
		assertEquals("altera", usuarioAlterado.getEmail());
		assertEquals("altera", usuarioAlterado.getName());
		assertEquals("altera", usuarioAlterado.getPassword());
	}
	
	@Test
	@DisplayName("Teste deleta usuario")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void deleteUserTest()	{
		userService.delete(1);
		var lista = userService.listAll();
		assertEquals(1, lista.size());
		assertEquals(2, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste busca usuario por nome")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void findByNameUserTest()	{
		var lista = userService.findByNameIgnoreCase("usuario teste 1");
		assertEquals(1, lista.size());
		lista = userService.findByNameEndsWith("2");
		assertEquals(1, lista.size());
	}
	
}
