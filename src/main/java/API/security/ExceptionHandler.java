package API.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionHandler {
    
    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    private ResponseEntity<String> handleAccessDeniedException(
        AccessDeniedException ex,
        WebRequest request
    ) {
        
        return new ResponseEntity(
            ex.getMessage(),
            new HttpHeaders(),
            HttpStatus.UNAUTHORIZED
        );
        
    }
    
}
