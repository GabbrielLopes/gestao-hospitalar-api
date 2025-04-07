package dev.gabbriellps.gestao.hospitalar.api.handler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Classe que pode ser utilizada para encapsular o response do serviço quando
 * haver alguma exceção.
 * 
 */
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"timestamp","status", "error", "message","path"})
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 3064633702216878129L;

    private final LocalDateTime timestamp;
    
    private final int status;

    private final String error;

    private final String message;
    
    private final String path;

    /**
     * Construtor que deve ser utilizado para informar a mensagem do erro e o
     * {@code HTTP} status code.
     * 
     * @param errorDescription A descrição do erro
     * @param status           O {@code HTTP} status code.
     */
    public ErrorResponse(final String message, final HttpStatus status) {
    	this(message,"",status);
    }
    
    
    /**
     * Construtor que deve ser utilizado para informar a mensagem do erro, caminho e o
     * {@code HTTP} status code.
     * @param message
     * @param path
     * @param status
     */
    public ErrorResponse(final String message,final String path, final HttpStatus status) {
    	this.timestamp = LocalDateTime.now();
    	this.message = message;
    	this.error = status.getReasonPhrase();
    	this.status	 = status.value();
    	this.path = path;
    }

    /**
     * Converte o valor do tipo {@code String} para uma instância do tipo
     * {@code ErrorResponse}.
     * 
     * @param body Valor que será convertido para objeto
     * 
     * @return {@code Optional} de {@code ErrorResponse} ou {@code Optional#empty()}
     *         caso não seja possível de converter o valor para objeto.
     */
    public static Optional<ErrorResponse> converterStringToObject(final String body) {
	try {
	    return Optional.ofNullable((ErrorResponse) new ObjectMapper().readerFor(ErrorResponse.class).readValue(body));
	} catch (IOException e) {
	    log.error("NÃO FOI POSSÍVEL CONVERTER A STRING '{}' PARA OBJETO ERROR_RESPONSE", body);
	}
	return Optional.empty();
    }

    public LocalDateTime getTimestamp() {
    	return timestamp;
    }
    
    public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}
	
	public String getPath() {
		return path;
	}

	@Override
    public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
