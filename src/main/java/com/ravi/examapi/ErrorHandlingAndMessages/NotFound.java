package com.ravi.examapi.ErrorHandlingAndMessages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotFound extends RuntimeException{
    private String message;
    private String path;
}
