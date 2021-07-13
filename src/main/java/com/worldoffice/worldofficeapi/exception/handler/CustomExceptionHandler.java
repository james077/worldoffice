package com.worldoffice.worldofficeapi.exception.handler;

import com.worldoffice.worldofficeapi.constants.ErrorMessages;
import com.worldoffice.worldofficeapi.exception.InvalidOrderException;
import com.worldoffice.worldofficeapi.exception.NoContentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

/**
 * @author James Martinez
 */
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {NoContentException.class, InvalidOrderException.class, DataIntegrityViolationException.class,
            ConstraintViolationException.class,  NullPointerException.class})
    public final ResponseEntity handleConflict(RuntimeException ex) {

        if (ex instanceof InvalidOrderException) {
            log.info(ErrorMessages.SAVE_ERROR, ex);
            return new ResponseEntity(((InvalidOrderException)ex).getCustomError(),HttpStatus.CONFLICT);
        }
       if (ex instanceof DataIntegrityViolationException || ex instanceof ConstraintViolationException) {
           log.error(ErrorMessages.SQL_TRANSACTION, ex);
           return new ResponseEntity(ErrorMessages.SQL_TRANSACTION, HttpStatus.CONFLICT);
       }
        if (ex instanceof NoContentException) {
            NoContentException _ex = (NoContentException) ex;
            log.error(String.format("El item %s not exist ", _ex.getId()));
            return ResponseEntity.noContent().build();
        }
        log.error(ErrorMessages.NOT_CONTROLLED_ERROR, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

}
