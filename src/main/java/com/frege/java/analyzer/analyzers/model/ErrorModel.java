package com.frege.java.analyzer.analyzers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorModel {
    @JsonProperty("line")
    private final Integer line;
    @JsonProperty("source")
    private final String type;
    @JsonProperty("message")
    private final String message;
}
