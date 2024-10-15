package com.books.runners;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import com.books.util.CucumberReportGenerator;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.books.steps",
        plugin = {"pretty", "json:target/cucumber-json-report/cucumber.json", "html:target/cucumber-html-report"},
        monochrome = true
)
public class CucumberTestRunner {

    @AfterClass
    public static void generateReport() {
        CucumberReportGenerator.generateReport();
    }
}
