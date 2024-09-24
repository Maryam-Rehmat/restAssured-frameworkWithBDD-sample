package functionalTests;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    private static final String TARGET = "target/allure-results";

    // Generate Cucumber report
    public static void generateReport() {
        // Generate Cucumber Report
        File reportOutputDirectory = new File("target");
        List<String> jsonFiles = new ArrayList<>();
        File folder = new File(TARGET);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.getName().endsWith(".json")) {
                    jsonFiles.add(System.getProperty("user.dir") + File.separator + TARGET + File.separator + file.getName());
                }
            }
        }
        String projectName = "platform-service";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
