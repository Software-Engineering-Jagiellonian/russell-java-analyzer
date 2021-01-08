package com.frege.java.analyzer.analyzers;

import com.frege.java.analyzer.analyzers.model.Statistics;
import org.springframework.stereotype.Service;

@Service
public class CheckStyleAnalyzer {
    public Statistics analyze(String filePath) {
        //TODO include checkStyle and extract statistics from result
        return new Statistics(filePath);
    }
}
