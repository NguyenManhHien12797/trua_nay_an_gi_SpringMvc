package trua_nay_an_gi.exception;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
   
    @ExceptionHandler({Exception.class, HandleException.class})
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }
    

}