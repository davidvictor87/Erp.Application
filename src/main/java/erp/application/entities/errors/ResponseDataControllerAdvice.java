package erp.application.entities.errors;

import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseDataControllerAdvice extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(UserNotFound.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User Not Fond")
	public ResponseEntity<Object> userNotFound(){
		Map<String, Object> errorDetails = new LinkedHashMap<>();
		errorDetails.put("time stamp", LocalDateTime.now());
		errorDetails.put("Error Message", "user not found");
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AppFailedToStart.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Application Failed to Start")
	public ResponseEntity<Object> appFailedToStart() {
		Map<String, Object> errorDetails = new LinkedHashMap<>();
		errorDetails.put("time stamp", LocalDateTime.now());
		errorDetails.put("Error Message", "application failed to start");
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status){
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("timestamp", LocalDate.now());
        body.put("status", status.value());
        List<String> errorList = exception.getBindingResult().getFieldErrors()
        		.stream().map(ex -> ex.getDefaultMessage()).collect(Collectors.toList());
        body.put("errors found", errorList);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
}
