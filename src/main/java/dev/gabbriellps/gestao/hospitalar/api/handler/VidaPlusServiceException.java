package dev.gabbriellps.gestao.hospitalar.api.handler;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Tratamento de Erro
 *
 */
@NoArgsConstructor
public class VidaPlusServiceException extends Exception {

	private static final long serialVersionUID = 6376559008232800045L;

	private HttpStatus status;
	
	public VidaPlusServiceException(String message, HttpStatus status) {
		super(message);
		this.status  = status;
	}

	/**
	 * Utilizar para manter a rastreabilidade das exceções - LOG
	 * @param message
	 * @param cause
	 */
	public VidaPlusServiceException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.status  = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	
}
