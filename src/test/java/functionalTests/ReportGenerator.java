package functionalTests;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;


import net.masterthought.cucumber.presentation.PresentationMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    private static final String TARGET = "target";

    public static void generateReport() {
        File reportOutputDirectory = new File(TARGET);
        List<String> jsonFiles = new ArrayList<>();
        File folder = new File(TARGET);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.getName().endsWith(".json")) {
                jsonFiles.add(System.getProperty("user.dir") + File.separator+ TARGET + File.separator + file.getName());
            }
            String projectName = "platform-service";
            Configuration configuration = new Configuration(reportOutputDirectory, projectName);
            configuration.containsPresentationMode(PresentationMode.RUN_WITH_JENKINS);
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();
        }
    }
}
