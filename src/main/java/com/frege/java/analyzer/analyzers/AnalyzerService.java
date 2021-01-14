package com.frege.java.analyzer.analyzers;

import com.frege.java.analyzer.analyzers.model.Statistic;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class AnalyzerService {
    private static final String JAVA_EXTINCTION = ".java";

    private final CheckStyleAnalyzer checkStyleAnalyzer;

    @Autowired
    public AnalyzerService(CheckStyleAnalyzer checkStyleAnalyzer) {
        this.checkStyleAnalyzer = checkStyleAnalyzer;
    }

    public Stream<Statistic> analyze(String path) {
        return filterOutJavaFiles(path)
                .flatMap(this::calculate);
    }

    private Stream<Statistic> calculate(String filename) {
        return checkStyleAnalyzer.analyze(filename);
    }

    @SneakyThrows
    public Stream<String> filterOutJavaFiles(String path) {
        return Files.walk(Paths.get(path))
                .map(Path::toString)
                .filter(it -> new File(it).getName().endsWith(JAVA_EXTINCTION));
    }
}
