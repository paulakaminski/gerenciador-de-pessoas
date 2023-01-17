package tech.attornatus.pessoas.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.attornatus.pessoas.exception.BadRequestException;
import tech.attornatus.pessoas.exception.NotFoundException;
import tech.attornatus.pessoas.exception.ServerSideException;
import tech.attornatus.pessoas.padrao.DefaultErrorResponse;

@ControllerAdvice
public class GlobalHandler {

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultErrorResponse> tratarBadRequestException(BadRequestException badRequestException) {
        return new ResponseEntity<DefaultErrorResponse>(
                new DefaultErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        badRequestException.getMessage()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultErrorResponse> tratarNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<DefaultErrorResponse>(
                new DefaultErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        notFoundException.getMessage()
                ), HttpStatus.NOT_FOUND
        );
    }

    @ResponseBody
    @ExceptionHandler(ServerSideException.class)
    public ResponseEntity<DefaultErrorResponse> tratarServerSideException(ServerSideException serverSideException) {
        return new ResponseEntity<DefaultErrorResponse>(
                new DefaultErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        serverSideException.getMessage()
                ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
