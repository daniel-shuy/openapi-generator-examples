package com.github.daniel.shuy.openapi.generator.examples.spring.util.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@StandardException
public class BadRequestException extends RuntimeException {
}
