package student_attendance.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
class ValidationExceptionHandler implements Serializable {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    protected ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        List<String> errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
//        return new ResponseEntity<>(new ValidationResponse(400, errorMessage), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundService.class})
    @ResponseBody
    protected ResponseEntity<?> handleNotFoundException(NotFoundService ex, Locale locale) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ServiceException.class})
    @ResponseBody
    protected ResponseEntity<?> handleServiceException(ServiceException ex, Locale locale) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
