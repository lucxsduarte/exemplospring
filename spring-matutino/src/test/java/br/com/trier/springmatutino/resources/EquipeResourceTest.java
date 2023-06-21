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

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.domain.Equipe;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	@Test
	@DisplayName("teste Buscar por id")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/equipe.sql")
	public void testFindByID() {
		
		ResponseEntity<Equipe> responseEntity = rest.getForEntity(
				"/equipe/1",
				Equipe.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("teste Buscar por name")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/equipe.sql")
	public void testFindByName() {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		String nomeEquipe = "Lotus";
		ResponseEntity<List<Equipe>> responseEntity = rest.exchange(
				"/equipe/name/" + nomeEquipe,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Equipe>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<Equipe> equipe = responseEntity.getBody();
	    assertEquals(1, equipe.size());
	}
	
	@Test
	@DisplayName("teste Buscar por name contem")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/equipe.sql")
	public void testFindByNameContains() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		String contem = "a";
		ResponseEntity<List<Equipe>> responseEntity = rest.exchange(
				"/equipe/name/contem/" + contem,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Equipe>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<Equipe> equipe = responseEntity.getBody();
	    assertEquals(1, equipe.size());
	}
	 
	@Test
	@DisplayName("teste Buscar por id inexistente")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/equipe.sql")
	public void testFindByNotFoundID() {
		ResponseEntity<Equipe> responseEntity = rest.getForEntity(
				"/equipe/100",
				Equipe.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("teste Listar todos")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/equipe.sql")
	public void testListAll() {
		ResponseEntity<List<Equipe>> responseEntity = rest.exchange(
				"/equipe",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Equipe>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<Equipe> equipe = responseEntity.getBody();
	    assertEquals(4, equipe.size());
	}
	
	@Test
	@DisplayName("teste cadastra equipe")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	public  void testCreatePais() {
		Equipe dto = new Equipe(null, "Equipe nova");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(dto, headers);
		
		ResponseEntity<Equipe> responseEntity = rest.exchange(
				"/equipe",
                HttpMethod.POST,
                requestEntity,
                Equipe.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Equipe equipe = responseEntity.getBody();
	    assertEquals("Equipe nova", equipe.getName());
	}
	
	@Test
	@DisplayName("teste update equipe")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/equipe.sql")
	public  void testUpdatePais() {
		Equipe dto = new Equipe(1, "Equipe nova");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(dto, headers);
		
		ResponseEntity<Equipe> responseEntity = rest.exchange(
				"/equipe/1",
                HttpMethod.PUT,
                requestEntity,
                Equipe.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Equipe equipe = responseEntity.getBody();
	    assertEquals("Equipe nova", equipe.getName());
	}
	
	@Test
	@DisplayName("teste delete equipe")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/equipe.sql")
	public void testDeletePais() {
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/equipe/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("teste delete equipe inexistente")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/equipe.sql")
	public void testDeleteNotFoundPais() {
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/equipe/5",
                HttpMethod.DELETE,
                null,
                Void.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
}
