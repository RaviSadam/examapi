package com.ravi.examapi.ErrorHandlingAndMessages;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class QuestionNotFound extends RuntimeException{
    public String message;
    public String getMessage(){
        return message;
    }
}
