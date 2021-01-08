package com.frege.java.analyzer.analyzers;

import com.frege.java.analyzer.analyzers.model.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class AnalyzerService {
    private static final String JAVA_EXTINCTION = ".java";

    private final CheckStyleAnalyzer checkStyleAnalyzer;

    @Autowired
    public AnalyzerService(CheckStyleAnalyzer checkStyleAnalyzer) {
        this.checkStyleAnalyzer = checkStyleAnalyzer;
    }

    public Stream<Statistics> analyze(String path) {
        return filterOutJavaFiles(path)
                .map(this::calculate);
    }

    private Statistics calculate(String filename) {
        return checkStyleAnalyzer.analyze(filename);
    }

    private Stream<String> filterOutJavaFiles(String path) {
        return Arrays.stream(Objects.requireNonNull(new File(path).listFiles((dir, name) -> name.endsWith(JAVA_EXTINCTION))))
                .map(File::getAbsolutePath);
    }
}
