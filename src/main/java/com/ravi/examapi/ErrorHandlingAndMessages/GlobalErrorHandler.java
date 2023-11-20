package com.ravi.examapi.ErrorHandlingAndMessages;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(QuestionNotFound.class)
    public ResponseEntity<ErrorMessage> questionNotFound(QuestionNotFound ex){
        return this.getErrorObject(ex.getMessage(),"/**",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ErrorMessage> notFound(NotFound ex){
        return this.getErrorObject("Not Found","/**",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequestError.class)
    public ResponseEntity<ErrorMessage> badRequest(BadRequestError ex){
        return this.getErrorObject(ex.getMessage(), "/**", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<ErrorMessage> userAlreadyExist(UserAlreadyExist ex){
        return this.getErrorObject(ex.getMessage(), "/**", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> exception(Exception ex){
        return this.getErrorObject("Internal Server Error","/**",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    //gives errormessage object
    private ResponseEntity<ErrorMessage> getErrorObject(String message,String path,HttpStatus statusCode){
        return ResponseEntity.status(statusCode).body(new ErrorMessage(statusCode,message,new Date(System.currentTimeMillis()),path));
    }
}
