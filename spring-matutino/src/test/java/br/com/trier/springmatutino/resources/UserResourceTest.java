package br.com.trier.springmatutino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
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
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/usuarios.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.getForEntity(url, UserDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
		});
	}
	
	@Test
	@DisplayName("teste buscar por id")
	public void testGetOk() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("Usuario teste 1", user.getName());
	}

	@Test
	@DisplayName("teste buscar por id inexistente")
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("teste listar todos")
	public void testListAll() {
		ResponseEntity<List<UserDTO>> responseEntity = rest.exchange(
				"/usuarios",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<UserDTO> users = responseEntity.getBody();
	    assertEquals(2, users.size());
	}
	
	@Test
	@DisplayName("teste cadastrar usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/usuarios",
				HttpMethod.POST,
				requestEntity,
				UserDTO.class
		);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("teste update usuário")
	public void testUpdateUser() {
		UserDTO dto = new UserDTO(1, "nome", "email@email.com", "senha");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/usuarios/1",
				HttpMethod.PUT,
				requestEntity,
				UserDTO.class
		);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("teste delete usuario")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testDeleteId() {
		ResponseEntity<Void> responseEntity = rest.exchange(
                "/usuarios/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

}