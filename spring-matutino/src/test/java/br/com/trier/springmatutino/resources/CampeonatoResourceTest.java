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
import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampeonatoResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	@Test
	@DisplayName("teste Buscar por id")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public void testFindByID() {
		
		ResponseEntity<CampeonatoDTO> responseEntity = rest.getForEntity(
				"/camp/1",
				CampeonatoDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("teste Buscar por desc")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public void testFindByName() {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		String descCamp = "Campeonato 1";
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/camp/desc/" + descCamp,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> camp = responseEntity.getBody();
	    assertEquals(1, camp.size());
	}
	
	@Test
	@DisplayName("teste Buscar por desc contem")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public void testFindByNameContains() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		String contem = "a";
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/camp/desc/contem/" + contem,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> camp = responseEntity.getBody();
	    assertEquals(4, camp.size());
	}
	
	@Test
	@DisplayName("teste Buscar por descricao e ano")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public void testFindByDescEAno() {
		String descCamp = "Campeonato 1";
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/camp/ano/desc/" + descCamp +"/" + 1995,
				HttpMethod.GET,
                null,
				new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
		List<CampeonatoDTO> camp = responseEntity.getBody();
	    assertEquals(1, camp.size());
	    assertEquals("Campeonato 1", camp.get(0).getDescription());
	}
	
	@Test
	@DisplayName("teste Buscar por ano")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public void testFindByAno() {
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/camp/ano/" + 2020,
				HttpMethod.GET,
                null,
				new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
	}
	
	@Test
	@DisplayName("teste Buscar por entre ano")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public void testFindByAnoEntreAno() {
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/camp/ano/entre/" + 2005 +"/" + 2020,
				HttpMethod.GET,
                null,
				new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
		List<CampeonatoDTO> camp = responseEntity.getBody();
	    assertEquals(3, camp.size());
	}
	
	
	@Test
	@DisplayName("teste Listar todos")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public void testListAll() {
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/camp",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> camp = responseEntity.getBody();
	    assertEquals(4, camp.size());
	}
	
	@Test
	@DisplayName("teste cadastra campeonato")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	public  void testCreatePais() {
		CampeonatoDTO dto = new CampeonatoDTO(null, "Campeonato Novo", 2020);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CampeonatoDTO> requestEntity = new HttpEntity<>(dto, headers);
		
		ResponseEntity<CampeonatoDTO> responseEntity = rest.exchange(
				"/camp",
                HttpMethod.POST,
                requestEntity,
                CampeonatoDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		CampeonatoDTO camp = responseEntity.getBody();
	    assertEquals("Campeonato Novo", camp.getDescription());
	}
	
	@Test
	@DisplayName("teste update campeonato")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public  void testUpdatePais() {
		CampeonatoDTO dto = new CampeonatoDTO(1, "Campeonato Novo", 2020);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CampeonatoDTO> requestEntity = new HttpEntity<>(dto, headers);
		
		ResponseEntity<CampeonatoDTO> responseEntity = rest.exchange(
				"/camp/1",
                HttpMethod.PUT,
                requestEntity,
                CampeonatoDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		CampeonatoDTO camp = responseEntity.getBody();
	    assertEquals("Campeonato Novo", camp.getDescription());
	}
	
	@Test
	@DisplayName("teste delete campeonato")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public void testDeletePais() {
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/camp/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("teste delete campeonato inexistente")
	@Sql(scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts="classpath:/resources/sqls/camp.sql")
	public void testDeleteNotFoundPais() {
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/camp/5",
                HttpMethod.DELETE,
                null,
                Void.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
