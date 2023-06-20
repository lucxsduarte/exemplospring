package br.com.trier.springmatutino.services.exceptions;

public class AnoInvalido extends RuntimeException{

	public AnoInvalido(String mensagem) {
		super(mensagem);
	}
}
