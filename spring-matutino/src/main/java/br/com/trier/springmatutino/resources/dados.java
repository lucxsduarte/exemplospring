package br.com.trier.springmatutino.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "/dados")
public class dados {

	List<Integer> resultado = new ArrayList<>();
	
	@GetMapping ("/{qnt}/{aposta}")
	public String resultadoFinal(@PathVariable(name = "qnt") Integer qnt, @PathVariable(name = "aposta") Integer aposta) {
		String controle = "O numero apostado foi: " + aposta +"/n";
		Integer dado = qnt;
		if(dado >= 1 && dado <= 4) {
			
			for (int i = 0; i <= dado; i++) {
				Random random = new Random();
			    int numeroAleatorio = random.nextInt(6) + 1;
			    resultado.add(numeroAleatorio);
			    return controle += "O número sorteado no dado " + i + " foi: " + numeroAleatorio;
			}
			   return controle += "O número sorteado no dado foi: ";
		} 
		return "erro";
	}
		    
				
	
}
	
	
	
	
	
	
	

