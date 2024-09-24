package functionalTests;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "functionalTests.stepDefinition",
        plugin = {
                "json:target/cucumber/report.json",
                "html:target/cucumber/report.html",
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ITRunner {
    @AfterClass
    public static void tearDown(){
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(){
            public void run(){
                ReportGenerator.generateReport();
            }
        });
    }
}