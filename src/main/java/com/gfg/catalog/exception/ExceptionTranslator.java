package com.gfg.catalog.exception;

import com.gfg.catalog.dto.ErrorResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator {

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleApplicationNotFoundException(ProductNotFoundException e, HttpServletRequest r) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse
                .builder()
                .httpStatusCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(e.getMessage())
                .build()
        );
  }
}
