package com.gfg.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -5917493146993751540L;

  public ProductNotFoundException() {
      super();
  }

  public ProductNotFoundException(String message) {
      super(message);
  }

  public ProductNotFoundException(String message, Throwable t) {
      super(message, t);
  }

  public ProductNotFoundException(Throwable t) {
        super(t);
    }

}
