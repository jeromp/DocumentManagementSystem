package com.github.jeromp.documentmanagementsystem.rest.resource.common;

import com.github.jeromp.documentmanagementsystem.rest.dto.ErrorDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ApiResponse(responseCode = "412", description = "Precondition Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)))
    protected ResponseEntity handleMissingServletRequestPart(MissingServletRequestPartException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = exception.getRequestPartName() + " parameter is missing";
        var errorDto = new ErrorDto(HttpStatus.PRECONDITION_FAILED, exception.getLocalizedMessage(), error);
        return prepareResponseEntity(errorDto);
    }

    @Override
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)))
    protected ResponseEntity handleNoHandlerFoundException(NoHandlerFoundException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errorDto = new ErrorDto(HttpStatus.NOT_FOUND, exception.getMessage(), exception.getLocalizedMessage());
        return prepareResponseEntity(errorDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ApiResponse(responseCode = "412", description = "Precondition Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)))
    protected ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException exception) {
        var errorDto = new ErrorDto(HttpStatus.PRECONDITION_FAILED, exception.getMessage(), exception.getLocalizedMessage());
        return prepareResponseEntity(errorDto);
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ApiResponse(responseCode = "412", description = "Precondition Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)))
    protected ResponseEntity<ErrorDto> handleDocumentNotFound(DocumentNotFoundException exception) {
        String error = "Document not found";
        var errorDto = new ErrorDto(HttpStatus.NOT_FOUND, exception.getMessage(), error);
        return prepareResponseEntity(errorDto);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)))
    public ResponseEntity handleAll(Exception exception, WebRequest request) {
        var errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception.getMessage());
        return prepareResponseEntity(errorDto);
    }

    private ResponseEntity<ErrorDto> prepareResponseEntity(ErrorDto errorDto) {
        return ResponseEntity.status(errorDto.getStatus().value()).contentType(MediaType.APPLICATION_JSON)
                .body(errorDto);
    }
}
