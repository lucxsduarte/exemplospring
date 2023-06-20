package br.com.trier.springmatutino.resources.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.trier.springmatutino.services.exceptions.AnoInvalido;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjetoNaoEncontrado.class)
	public ResponseEntity<StandardError> objetoNaoEncontrado (ObjetoNaoEncontrado obj, HttpServletRequest request){
		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), obj.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(ViolacaoIntegridade.class)
	public ResponseEntity<StandardError> violacaoIntegridade (ViolacaoIntegridade vi, HttpServletRequest request) {
		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), vi.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AnoInvalido.class)
	public ResponseEntity<StandardError> anoInvalido (AnoInvalido ai, HttpServletRequest request) {
		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ai.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
}
