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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.config.jwt.JwtUtil;
import br.com.trier.springmatutino.config.jwt.LoginDTO;
import br.com.trier.springmatutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	@Autowired
	private AuthenticationManager auth;
	@Autowired
	private JwtUtil jwtUtil;

	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.getForEntity(url, UserDTO.class);
	}
	
	//public String geraToken() {
	//	Authentication authentication = auth.authenticate(
	//			new UsernamePasswordAuthenticationToken("test1@test.com.br", "123"));
	////	if (authentication.isAuthenticated()) {
	//		return jwtUtil.generateToken("test1@test.com.br");
	//	} else {
	//		throw new UsernameNotFoundException("Usuário inválido");
	//	}
	//
	//}
	
	private String geraToken() {
		LoginDTO loginDTO = new LoginDTO("test1@test.com.br", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", 
				HttpMethod.POST, 
				requestEntity, 
				String.class);
		
		String token = responseEntity.getBody();
		return token;
	}
	
	@Test
	@DisplayName("teste obter token")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void obterToken() {
		LoginDTO loginDTO = new LoginDTO("test1@test.com.br", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", 
				HttpMethod.POST, 
				requestEntity, 
				String.class);
		
		String token = responseEntity.getBody();
	}
	
	@Test
	@DisplayName("teste buscar por id")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testGetOk() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("Usuario teste 1", user.getName());
	}

	@Test
	@DisplayName("teste buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("teste listar todos")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
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
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + geraToken() );
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
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/usuarios.sql")
	public void testUpdateUser() {
		UserDTO dto = new UserDTO(1, "nome", "email@email.com", "senha", "ADMIN");
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