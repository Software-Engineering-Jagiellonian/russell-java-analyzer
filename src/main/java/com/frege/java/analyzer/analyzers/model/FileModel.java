package com.frege.java.analyzer.analyzers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FileModel {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("error")
    private final List<ErrorModel> error;
}
