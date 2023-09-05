package com.github.daniel.shuy.openapi.generator.examples.spring.util.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@StandardException
public class NotFoundException extends RuntimeException {
}
