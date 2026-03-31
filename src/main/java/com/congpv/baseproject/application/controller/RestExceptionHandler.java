package com.congpv.baseproject.application.controller;

import com.congpv.baseproject.application.response.BaseResponse;
import com.congpv.baseproject.application.response.FindProductResponse;
import com.congpv.baseproject.application.response.PostResponse;
import com.congpv.baseproject.application.response.PriceCheckResponse;
import com.congpv.baseproject.infrastructure.shared.exception.EmptyRequestException;
import com.congpv.baseproject.infrastructure.shared.exception.PostNotFoundException;
import com.congpv.baseproject.infrastructure.shared.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

  private static final String MSG_PRODUCT_NOT_FOUND = "Your product is not found";
  private static final String MSG_EMPTY_REQUEST = "Your request body is empty";
  private static final String MSG_POST_NOT_FOUND = "No posts found";

  @ExceptionHandler(value = ProductNotFoundException.class)
  public ResponseEntity<FindProductResponse> handleProductNotFoundException() {
    return new ResponseEntity<>(
        FindProductResponse.builder().message(MSG_PRODUCT_NOT_FOUND).success(false).build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = EmptyRequestException.class)
  public ResponseEntity<PriceCheckResponse> handleEmptyRequestException() {
    return new ResponseEntity<>(
        PriceCheckResponse.builder().message(MSG_EMPTY_REQUEST).success(false).build(),
        HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<BaseResponse> handleUnknownException(Exception exception) {
    log.error("> ERROR ", exception);
    return new ResponseEntity<>(
        BaseResponse.builder().message("Internal Server Error").success(false).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = PostNotFoundException.class)
  public ResponseEntity<PostResponse> handlePostNotFoundException() {
    return new ResponseEntity<>(
        PostResponse.builder().message(MSG_POST_NOT_FOUND).success(false).build(),
        HttpStatus.NOT_FOUND
    );
  }
}
