package com.ravi.examapi.ErrorHandlingAndMessages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BadRequestError extends RuntimeException {
    private String message;
}
