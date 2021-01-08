package com.frege.java.analyzer.rabbitmq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SentMessage {
    @JsonProperty(value = "repo_id")
    private final String repositoryId;
    @JsonProperty(value = "language_id")
    private final Integer languageId;
}
