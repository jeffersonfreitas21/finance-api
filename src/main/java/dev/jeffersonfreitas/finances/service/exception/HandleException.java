package dev.jeffersonfreitas.finances.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class HandleException {
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIErrors handleMethodArgumentInvalid(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        return new APIErrors(bindingResult);
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handlerResponseStatusException(ResponseStatusException ex) {
    	return new ResponseEntity(new APIErrors(ex), ex.getStatus());
    }
}
