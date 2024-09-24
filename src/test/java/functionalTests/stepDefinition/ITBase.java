package functionalTests.stepDefinition;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITBase {
    protected static final Logger logger = LoggerFactory.getLogger(ITBase.class);
    protected final static String PlatformUrlProperty = "platform.url";
    protected final static String NDCMethodProperty = "NDC.method";
    protected final static String NDCStrengthMethodProperty = "NDC.strengthMethod";
    private static final String TESTING_ENVIRONMENT_QA = "qa";
    protected Response response;
    protected RequestSpecification request;
    protected static String username;
    protected static String password;

    protected CompositeConfiguration configuration;


    protected void addHeaders() {
        request.header("Content-Type", "application/json");
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
            username = System.getenv("Redis_userName");
            password = System.getenv("Redis_Pass");
            RestAssured.baseURI = configuration.getString(PlatformUrlProperty);
            request = RestAssured.given().auth()
                    .preemptive()
                    .basic(username, password);
            addHeaders();

        } catch (ConfigurationException ex) {
            logger.error(ITBase.class.getName(), ex);
        }
    }


}
