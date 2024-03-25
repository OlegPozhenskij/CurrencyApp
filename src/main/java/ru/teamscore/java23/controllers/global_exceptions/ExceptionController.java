package ru.teamscore.java23.controllers.global_exceptions;

import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(RollbackException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleRollbackException(RollbackException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.entry("message", e.getMessage()));
    }

    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleNoResultException(NoResultException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.entry("message", e.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.entry("message", e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.entry("message", e.getMessage()));
    }
}
