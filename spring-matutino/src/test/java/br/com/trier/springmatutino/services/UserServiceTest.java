package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
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
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findById(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
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
	@DisplayName("Teste lista todos vazio")
	void listAllNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.listAll());
		assertEquals("Nenhum usuário encontrado", exception.getMessage());
	} 
	
	@Test
	@DisplayName("Teste cadastra usuario")
	void insertUserTest()	{
		var usuario = new User(null, "Lucas", "lucas@gmail.com", "123", "ADMIN");
		userService.salvar(usuario);
		var lista = userService.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste cadastra usuario email repetido")
	void insertUserEmailErrorTest()	{
		userService.salvar(new User(null, "Lucas", "lucas@gmail.com", "123", "ADMIN"));
		
		var exception = assertThrows(ViolacaoIntegridade.class, () -> userService.salvar(new User(null, "usu2", "lucas@gmail.com", "123", "ADMIN")));
		assertEquals("Email já cadastrado: lucas@gmail.com", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste altera usuario")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void updateUserTest()	{
		var usuario = new User(1, "altera", "altera", "altera", "ADMIN");
		userService.update(usuario);
		var usuarioAlterado = userService.findById(1);
		assertEquals(1, usuario.getId());
		assertEquals("altera", usuarioAlterado.getEmail());
		assertEquals("altera", usuarioAlterado.getName());
		assertEquals("altera", usuarioAlterado.getPassword());
	}
	
	@Test
	@DisplayName("Teste altera usuario com email repetido")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void updateUserEmailErrorTest()	{
		var exception = assertThrows(ViolacaoIntegridade.class, () -> userService.update(new User(2, "Usuario teste 2", "test1@test.com.br", "123", "ADMIN")));
		assertEquals("Email já cadastrado: test1@test.com.br", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste altera usuario inexistente")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void updateUserNonExistentTest()	{
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.update(new User(4, "Usuario teste 4", "test4@test.com.br", "123", "ADMIN")));
		assertEquals("Usuário 4 não encontrado", exception.getMessage());
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
	@DisplayName("Teste deleta usuario inexistente")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void deleteUserNonExistentTest()	{
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.delete(10));
		var lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca usuario por nome")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void findByNameUserTest()	{
		var lista = userService.findByNameIgnoreCase("usuario teste 1");
		assertEquals(1, lista.size());
		
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findByNameIgnoreCase("luca"));
		assertEquals("Nenhum usuário se chama luca", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca usuario por nome termina com")
	@Sql({"classpath:/resources/sqls/usuarios.sql"})
	void findByNameUserEndsWithTest()	{
		var lista = userService.findByNameEndsWith("1");
		assertEquals(1, lista.size());
		
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findByNameEndsWith("luca"));
		assertEquals("Nenhum usuário termina com luca", exception.getMessage());
	}
	
}
