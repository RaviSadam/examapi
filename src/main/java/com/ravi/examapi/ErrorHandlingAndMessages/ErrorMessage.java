package com.ravi.examapi.ErrorHandlingAndMessages;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorMessage {
    @JsonProperty("error_code")
    private HttpStatus code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("time")
    private Date date;
    private String path;
}
