package functionalTests.stepDefinition;
import io.cucumber.java.PendingException;
import io.cucumber.java.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class NDCService extends ITBase {
    private Scenario scenario;

   @Before
    public void Init(Scenario scenario) {
        this.scenario = scenario;
        initConfiguration();
    }

    @When("^send a request with valid NDC code \"([^\"]*)\" and the \"([^\"]*)\" attribute$")
    public void send_a_request_with_valid_ndc_code_and_the_attribute(String NDCCode, String attribute) {
        scenario.log("Request Body: \n" + configuration.getString(NDCMethodProperty) + "?code=" + NDCCode + "&responseFields=" + attribute + "\n");
        System.out.println("Request Body: \n" + RestAssured.baseURI+ configuration.getString(NDCMethodProperty) + "?code=" + NDCCode + "&responseFields=" + attribute + ",SUBSTANCE_NAME,ACTIVE_NUMERATOR_STRENGTH,ACTIVE_INGRED_UNIT"+"\n");
        response = request.get(configuration.getString(NDCMethodProperty) + "?code=" + NDCCode + "&responseFields=" + attribute+ ",SUBSTANCE_NAME,ACTIVE_NUMERATOR_STRENGTH,ACTIVE_INGRED_UNIT");
        scenario.log("Response Headers: \n" + response.headers() + "\n");
        scenario.log("Response Body : \n" + response.prettyPrint() + "\n");
    }
    @Then("^response shows the strength by combining substanceName, activeNumeratorStrength and ACTIVE_INGRED_UNIT$")
    public void response_shows_the_strength_by_combining_substance_name_active_numerator_strength_and_active_ingred_unit() {
        assertEquals(200, response.getStatusCode());
        String substanceName = JsonPath.with(response.getBody().asString()).getString("data.substanceName");
        String activeNumeratorStrength = JsonPath.with(response.getBody().asString()).getString("data.activeNumeratorStrength");
        String activeIngredUnit = JsonPath.with(response.getBody().asString()).getString("data.activeIngredUnit");
        Assert.assertEquals(substanceName+" "+activeNumeratorStrength+" "+activeIngredUnit, JsonPath.with(response.getBody().asString()).getString("data.strength"));
    }

}
