package functionalTests.stepDefinition;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;

public class ITBase {
    protected static final Logger logger = LoggerFactory.getLogger(ITBase.class);
    protected final static String PlatformUrlProperty = "platform.url";
    protected final static String NDCMethodProperty = "NDC.method";
    protected final static String NDCStrengthMethodProperty="NDC.strengthMethod";
    private static final String TESTING_ENVIRONMENT_QA = "qa";
    protected Response response;
    protected RequestSpecification request;
    protected CompositeConfiguration configuration;


    protected void addHeaders() {
        request.header("Content-Type", "application/json");
        request.header("Authorization"," Basic cmVkaXNfdXNlcjpyZWRpc0AyMDIz");
    }

    /**
     * Initiates the request
     */
    protected void initConfiguration() {
        try {
            configuration = new CompositeConfiguration();
            configuration.addConfiguration(new SystemConfiguration());
            configuration.addConfiguration(new Configurations()
                    .properties(System.getProperty("env", TESTING_ENVIRONMENT_QA)
                            + "-configuration.properties"));
            RestAssured.baseURI = configuration.getString(PlatformUrlProperty);
            request = RestAssured.given();
            addHeaders();

        } catch (ConfigurationException ex) {
            logger.error(ITBase.class.getName(), ex);
        }
    }

    protected File getJSONFile(String fileName) {
        String module = System.getProperty("module", configuration.getString("module"));
        String root = System.getProperty("user.dir");
        if (module != null && !root.contains(module)) {
            root += File.separator + System.getProperty("module", configuration.getString("module"));
        }
        return Paths.get(root, "src", "test","java", "functionalTests", "resources", "templates", fileName).toFile();
    }

    protected JSONObject getJsonObjectFromJsonFile(String mockResponseObject, String fileName) {
        return functionalTests.stepDefinition.Utils.getJsonObjectFromFile(mockResponseObject, getJSONFile(fileName));
    }


}
