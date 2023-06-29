package br.com.trier.springmatutino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.config.jwt.LoginDTO;
import br.com.trier.springmatutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;

	private HttpHeaders getHeaders(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST, 
				requestEntity, 
				String.class);
		
		String token = responseEntity.getBody();
		System.out.println("**************" + token);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
	
	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("test1@test.com.br", "123")),
				UserDTO.class
				);
	}
	
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("test1@test.com.br", "123")),
				new ParameterizedTypeReference<List<UserDTO>>() {}
				);
	}
	
	@Test
	@DisplayName("teste buscar por id")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testFindById() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/3");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		UserDTO user = response.getBody();
		assertEquals("Usuario1", user.getName());
	}

	@Test
	@DisplayName("teste buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testFindByIdNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/100");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	@DisplayName("teste buscar por nome")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testFindByName() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name/usuario1");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Usuario1", response.getBody().get(0).getName());
	}
	
	
	@Test
	@DisplayName("teste buscar por nome termina")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testFindByNameEndsWith() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name/termina/1");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Usuario1", response.getBody().get(0).getName());
	}
	
	@Test
	@DisplayName("teste listar todos")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testListAll() {
		ResponseEntity<List<UserDTO>> responseEntity = rest.exchange(
				"/usuarios",
                HttpMethod.GET,
                new HttpEntity<>(getHeaders("test1@test.com.br", "123")),
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(2, responseEntity.getBody().size());
	}
	
	@Test
	@DisplayName("teste cadastrar usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
		System.out.println("********INSERT");
		HttpHeaders headers = getHeaders("test1@test.com.br", "123");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/usuarios",
				HttpMethod.POST,
				requestEntity,
				UserDTO.class
		);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("teste update usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testUpdateUser() {
		UserDTO dto = new UserDTO(4, "nome", "email@email.com", "senha", "USER");
		HttpHeaders headers = getHeaders("test1@test.com.br","123");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/usuarios/4",
				HttpMethod.PUT,
				requestEntity,
				UserDTO.class
		);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("teste delete usuario")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testDeleteId() {
		HttpHeaders headers = getHeaders("test1@test.com.br","123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
                "/usuarios/3",
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

}