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
import br.com.trier.springmatutino.domain.dto.PaisDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaisResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	@Test
	@DisplayName("teste Buscar por id")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/pais.sql")
	public void testFindByID() {
		ResponseEntity<PaisDTO> responseEntity = rest.getForEntity(
				"/pais/1",
                PaisDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/pais.sql")
	@Test
	@DisplayName("teste Buscar por name")
	public void testFindByName() {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		String nomePais = "Brasil";
		ResponseEntity<List<PaisDTO>> responseEntity = rest.exchange(
				"/pais/name/" + nomePais,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<PaisDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<PaisDTO> pais = responseEntity.getBody();
	    assertEquals(1, pais.size());
	}
	
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/pais.sql")
	@Test
	@DisplayName("teste Buscar por name contem")
	public void testFindByNameContains() {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		String contem = "a";
		ResponseEntity<List<PaisDTO>> responseEntity = rest.exchange(
				"/pais/name/contem/" + contem,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<PaisDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<PaisDTO> pais = responseEntity.getBody();
	    assertEquals(2, pais.size());
	}
	 
	@Test
	@DisplayName("teste Buscar por id inexistente")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/pais.sql")
	public void testFindByNotFoundID() {
		ResponseEntity<PaisDTO> responseEntity = rest.getForEntity(
				"/pais/100",
                PaisDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("teste Listar todos")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/pais.sql")
	public void testListAll() {
		ResponseEntity<List<PaisDTO>> responseEntity = rest.exchange(
				"/pais",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PaisDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<PaisDTO> pais = responseEntity.getBody();
	    assertEquals(2, pais.size());
	}
	
	@Test
	@DisplayName("teste cadastra pais")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	public  void testCreatePais() {
		PaisDTO dto = new PaisDTO(null, "Pais Novo");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaisDTO> requestEntity = new HttpEntity<>(dto, headers);
		
		ResponseEntity<PaisDTO> responseEntity = rest.exchange(
				"/pais",
                HttpMethod.POST,
                requestEntity,
                PaisDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		PaisDTO pais = responseEntity.getBody();
	    assertEquals("Pais Novo", pais.getName());
	}
	
	@Test
	@DisplayName("teste update pais")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/pais.sql")
	public  void testUpdatePais() {
		PaisDTO dto = new PaisDTO(1, "Pais Novo");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaisDTO> requestEntity = new HttpEntity<>(dto, headers);
		
		ResponseEntity<PaisDTO> responseEntity = rest.exchange(
				"/pais/1",
                HttpMethod.PUT,
                requestEntity,
                PaisDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		PaisDTO pais = responseEntity.getBody();
	    assertEquals("Pais Novo", pais.getName());
	}

	@Test
	@DisplayName("teste delete pais")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/pais.sql")
	public void testDeletePais() {
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/pais/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("teste delete pais inexistente")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/pais.sql")
	public void testDeleteNotFoundPais() {
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/pais/4",
                HttpMethod.DELETE,
                null,
                Void.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
}
