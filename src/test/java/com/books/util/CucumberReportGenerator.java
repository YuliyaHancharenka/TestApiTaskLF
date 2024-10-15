package com.books.util;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CucumberReportGenerator {

    public static void generateReport() {
        File reportOutputDirectory = new File("target/enhanced-cucumber-report");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber-json-report/cucumber.json");
        Configuration configuration = new Configuration(reportOutputDirectory, "Book API Tests");
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
