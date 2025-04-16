package dev.gabbriellps.gestao.hospitalar.api.handler;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tratamento de excecoes geradas
 *
 */
@Slf4j
@ControllerAdvice
@Hidden
public class ApiHandler extends ResponseEntityExceptionHandler implements Serializable {


	private static final long serialVersionUID = 4790038517549729374L;

	public ApiHandler() {
		super();
	}
	
	/**
	 * Tratamento de erro chamando API - rest
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> handle(HttpClientErrorException e){
		try {
			return new ResponseEntity<>(e.getResponseBodyAsString(),getHeaderJson(), e.getStatusCode());
		} catch (Exception ex) { 
			return new ResponseEntity<>(ex.getMessage(),e.getStatusCode()); 
		}
	}
	
	/**
	 * Tratamento de erro
	 * @param e
	 * @return
	 */
	@ExceptionHandler(VidaPlusServiceException.class)
	public ResponseEntity<ErrorResponse> handle(VidaPlusServiceException e){
		if (e.getStatus().equals(HttpStatus.NO_CONTENT)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} 
		return montaMsg(e.getMessage(), e.getStatus()); 
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handle(BadCredentialsException e){

		return montaMsg(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}




	/**
	 * Serviço indisponivel
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ResourceAccessException.class)
	@ResponseStatus(code=HttpStatus.SERVICE_UNAVAILABLE)
	public ResponseEntity<ErrorResponse> handleResourceAccessException(ResourceAccessException e) {
		log.error(String.format("[%s] - %s",HttpStatus.SERVICE_UNAVAILABLE, e.getMessage()));
		return montaMsg("[SERVIÇO INDISPONÍVEL]",HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	/**
	 * Erro geral
	 * @param e
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
		log.error(e.getMessage(),e);
		return montaMsg(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public ResponseEntity<ErrorResponse> handle(AccessDeniedException e){
		return montaMsg(e.getMessage(), HttpStatus.UNAUTHORIZED); 
	}
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code=HttpStatus.CONFLICT)
	public ResponseEntity<ErrorResponse> handleRuntimeException(ConstraintViolationException e) {
		log.error(e.getMessage(),e);
		return montaMsg(e.getMessage(),HttpStatus.CONFLICT);
	}

	private ResponseEntity<ErrorResponse> montaMsg(String msg,HttpStatus status){
		return new ResponseEntity<>(new ErrorResponse(msg, status),	ApiHandler.getHeaderJson(), status);
	}

	public static HttpHeaders getHeaderJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	/*
	 * Usado pelo Bean Validator
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errorList = ex
				.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());

		String msg = ex.getMessage();
		HttpStatus statusRetorno = HttpStatus.valueOf(status.value());
		if (!errorList.isEmpty()) {
			msg = errorList.toString().replace("[", "").replace("]", "");
			statusRetorno = HttpStatus.BAD_REQUEST;
		}
		return handleExceptionInternal(ex, new ErrorResponse(msg, statusRetorno), headers, statusRetorno, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String msg = ex.getMessage();
		return handleExceptionInternal(ex, new ErrorResponse(msg, HttpStatus.CONFLICT), headers, status, request);
	}



	@ExceptionHandler(BindException.class)
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errorList = ex
				.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());

		String msg = ex.getMessage();
		HttpStatus statusRetorno = status;
		if (!errorList.isEmpty()) {
			msg = errorList.toString().replace("[", "").replace("]", "");
			statusRetorno = HttpStatus.BAD_REQUEST;
		}
		return handleExceptionInternal(ex, new ErrorResponse(msg, statusRetorno), headers, statusRetorno, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String msg = ex.getMessage();
		return handleExceptionInternal(ex, new ErrorResponse(msg, HttpStatus.BAD_REQUEST), headers, status, request);
	}

}
