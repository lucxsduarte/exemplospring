package br.com.trier.springmatutino.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dados")
public class Dados {

	@GetMapping("/{qnt}/{aposta}")
	public String verificaAposta(@PathVariable(name = "qnt") Integer qnt, @PathVariable(name = "aposta") Integer aposta) {
		String dados = "O numero apostado foi: " + aposta + "\n";
		String resultadoDados = "O resultado dos " + qnt + " dados foram: ";
		String porcentagem = "Percentual em relação ao sorteio: ";
		double porc = 0;
		if (qnt >= 1 && qnt <= 4) {
			if (qnt * 6 >= aposta) {

				List<Integer> resultado = new ArrayList<>();

				for (int i = 1; i <= qnt; i++) {
					Random random = new Random();
					int numeroAleatorio = random.nextInt(6) + 1;
					resultado.add(numeroAleatorio);
					resultadoDados += numeroAleatorio + "   ";
				}
				double soma = resultado.stream().mapToInt(Integer::intValue).sum();
				dados += "A soma dos número sorteados foram: " + soma + "\n";
				porc = (soma / aposta) * 100;
				porcentagem += porc;
				return resultadoDados + "\n" + dados + porcentagem;
			}
			return "O número apostado deve condizer com algum possível resultado";
		}
		return "Escolha no mínimo 1 dado e no máximo 4";
	}

}
