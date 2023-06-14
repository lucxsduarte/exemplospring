package br.com.trier.springmatutino.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "/calc")
public class MathResource {
	
	@GetMapping ("/somar")
	public Integer somar(@RequestParam(name = "n1") Integer n1,@RequestParam(name = "n2") Integer n2) {
		return n1 + n2;
	}
	//http://localhost:8080/calc/somar?n1=500&n2=50
	
	@GetMapping ("/subtrair/{n1}/{n2}")
	public Integer subtrair(@PathVariable(name = "n1") Integer n1,@PathVariable(name = "n2") Integer n2) {
		return n1 - n2;
	}
	//http://localhost:8080/calc/subtrair/500/50
	
	@GetMapping ("/multiplicar")
	public Integer multiplicar(@RequestParam(name = "n1") Integer n1,@RequestParam(name = "n2") Integer n2) {
		return n1 * n2;
	}
	//http://localhost:8080/calc/multiplicar?n1=500&n2=50
	
	
	@GetMapping ("/dividir/{n1}/{n2}")
	public Integer dividir(@PathVariable(name = "n1") Integer n1,@PathVariable(name = "n2") Integer n2) {
		return n1 / n2;
	}
	//http://localhost:8080/calc/dividir/500/50
	
}
