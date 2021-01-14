package com.frege.java.analyzer.analyzers;

import com.frege.java.analyzer.analyzers.model.ErrorModel;
import com.frege.java.analyzer.analyzers.model.Statistic;
import com.frege.java.analyzer.analyzers.model.StatisticCheckStyleModel;
import com.google.gson.Gson;
import com.puppycrawl.tools.checkstyle.Main;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class CheckStyleAnalyzer {
    private static final String CONFIG_COMMAND = "-c";
    private static final String FORMAT_COMMAND = "-f";
    private static final String OUTPUT_COMMAND = "-o";
    private static final String XML_FORMAT = "xml";
    private static final String CONFIG_PATH = "config/config.xml";
    private static final String REPORT_PATH = "config/report.xml";
    private static final String EXTERNAL_TYPE_NAMING = "com.puppycrawl.tools.checkstyle.checks.";

    private final Gson gson;

    @Autowired
    public CheckStyleAnalyzer(Gson gson) {
        this.gson = gson;
    }

    public Stream<Statistic> analyze(String filePath) {
        try {
            Main.main(
                    CONFIG_COMMAND, CONFIG_PATH,
                    FORMAT_COMMAND, XML_FORMAT,
                    OUTPUT_COMMAND, REPORT_PATH,
                    filePath
            );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return convertXmlReport();
    }

    private Stream<Statistic> convertXmlReport() {
        String xml = "";
        try {
            xml = Files.readString(Path.of(REPORT_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StatisticCheckStyleModel statisticCheckStyleModel = gson.fromJson(XML.toJSONObject(xml).toString(), StatisticCheckStyleModel.class);
        String filePath = new File(statisticCheckStyleModel.getCheckstyle().getFile().getName()).getName();
        return statisticCheckStyleModel.getCheckstyle().getFile().getError().stream()
                .map(it -> errorToStatistic(filePath, it));
    }

    private Statistic errorToStatistic(String fileName, ErrorModel error) {
        return new Statistic(
                fileName,
                correctType(error.getType()),
                error.getLine(),
                error.getMessage()
        );
    }

    public String correctType(String type) {
        return type.replace(EXTERNAL_TYPE_NAMING, "");
    }
}
