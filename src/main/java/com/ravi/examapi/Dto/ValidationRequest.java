package com.ravi.examapi.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValidationRequest {
    
    private long id;

    @JsonProperty("selected_option")
    private String selectedOption;
}
