package com.github.jeromp.documentmanagementsystem.rest.common;

import com.github.jeromp.documentmanagementsystem.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMissingServletRequestPart(MissingServletRequestPartException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = exception.getRequestPartName() + " parameter is missing";
        var errorDto = new ErrorDto(HttpStatus.PRECONDITION_FAILED, exception.getLocalizedMessage(), error);
        return new ResponseEntity<ErrorDto>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    protected ResponseEntity handleDocumentNotFound(DocumentNotFoundException exception) {
        String error = "Document not found";
        var errorDto = new ErrorDto(HttpStatus.NOT_FOUND, exception.getMessage(), error);
        return new ResponseEntity<ErrorDto>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }
}