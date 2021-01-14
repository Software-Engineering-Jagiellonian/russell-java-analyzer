package com.frege.java.analyzer.analyzers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Statistic {
    private final String filename;
    private final String type;
    private final Integer line;
    private final String reason;
}
