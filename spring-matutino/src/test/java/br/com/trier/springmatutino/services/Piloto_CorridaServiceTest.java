package br.com.trier.springmatutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Piloto_Corrida;
import jakarta.transaction.Transactional;

@Transactional
public class Piloto_CorridaServiceTest extends BaseTests{
	
	@Autowired 
	Piloto_CorridaService pilotoCorridaService;
	
	@Test
	@DisplayName ("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByIdTest() {
		var piloto_corrida = pilotoCorridaService.findById(1);
		assertTrue(piloto_corrida != null);
		assertEquals(1, piloto_corrida.getPiloto());
	}
	
	@Test
	@DisplayName ("Teste busca por ID inexistente")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByIdNonExistentTest() {
		var piloto_corrida = pilotoCorridaService.findById(7);
		assertTrue(piloto_corrida == null);
	}
	
	@Test
	@DisplayName ("Teste busca por piloto")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByPilotoTest() {
		var lista = pilotoCorridaService.findByPiloto(1);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por piloto inexistente")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByPilotoNonExistentTest() {
		var piloto_corrida = pilotoCorridaService.findById(7);
		assertTrue(piloto_corrida == null);
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoTest() {
		var lista = pilotoCorridaService.findByColocacao(1);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao inexistente")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoNonExistentTest() {
		var lista = pilotoCorridaService.findByColocacao(4);
		assertEquals(0, lista.size());
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao e corrida")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoECorridaTest() {
		var lista = pilotoCorridaService.findByColocacaoAndCorrida(1, 1);
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName ("Teste busca por colocacao entre colocacao na corrida")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoEntreTest() {
		var lista = pilotoCorridaService.findByColocacaoBetweenAndCorrida(1, 2, 1);
		assertEquals(2, lista.size());
		assertEquals(3, lista.get(1).getId());
	}
	
	@Test
	@DisplayName ("Teste busca por colocar maior ou igual na corrida")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoMaiorIgualTest() {
		var lista = pilotoCorridaService.findByColocacaoGreaterThanEqualAndCorrida(2, 1);
		assertEquals(2, lista.size());
		assertEquals(5, lista.get(1).getId());
	}
	
	@Test
	@DisplayName ("Teste busca por colocar menor ou igual na corrida")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByColocacaoMenorIgualTest() {
		var lista = pilotoCorridaService.findByColocacaoLessThanEqualAndCorrida(1, 1);
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName ("Teste cadastra piloto_corrida")
	void cadastraTest() {
		var piloto_corrida = new Piloto_Corrida (null,1, 1, 3);
		pilotoCorridaService.salvar(piloto_corrida);
		var newPilotoCorrida = pilotoCorridaService.findById(1);
		assertEquals(3, newPilotoCorrida.getColocacao());
	}
	
	@Test
	@DisplayName ("Teste update piloto_corrida")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void updateTest() {
		var piloto_corrida = new Piloto_Corrida (1,2, 2, 3);
		pilotoCorridaService.update(piloto_corrida);
		var newPilotoCorrida = pilotoCorridaService.findById(1);
		assertEquals(3, newPilotoCorrida.getColocacao());
	}
	
	@Test
	@DisplayName ("Teste delete piloto_corrida")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void deleteTest() {
		pilotoCorridaService.delete(1);
		var lista = pilotoCorridaService.listAll();
		assertEquals(5, lista.size());
	}
	
	@Test
	@DisplayName ("Teste delete piloto_corrida inexistente")
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void deleteNonExistentTest() {
		pilotoCorridaService.delete(7);
		var lista = pilotoCorridaService.listAll();
		assertEquals(6, lista.size());
	}
	
}
