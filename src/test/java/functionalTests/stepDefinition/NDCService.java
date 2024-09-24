package functionalTests.stepDefinition;

import functionalTests.constants.NdcMessages;
import io.cucumber.java.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.springframework.http.HttpStatus;


import static org.junit.Assert.assertEquals;

public class NDCService extends ITBase {
    private Scenario scenario;
    private String ndcCode;

    @Before
    public void Init(Scenario scenario) {
        this.scenario = scenario;
        initConfiguration();
    }

    @Given("^a valid NDC code \"([^\"]*)\"$")
    public void given_valid_ndc_code(String NDCCode) {
        this.ndcCode = NDCCode;
    }

    @When("^I send a request with the \"([^\"]*)\" attribute$")
    public void send_a_request_with_valid_ndc_code_and_the_attribute(String attribute) {
        String requestUrl = RestAssured.baseURI + configuration.getString(NDCMethodProperty) + "?code=" + this.ndcCode + "&responseFields=" + attribute + ",SUBSTANCE_NAME,ACTIVE_NUMERATOR_STRENGTH,ACTIVE_INGRED_UNIT";
        try {
            // Log request URL to scenario
            scenario.log("Request URL: " + requestUrl);
            response = request.get(requestUrl);

            String responseBody = response.prettyPrint();
            // Log response details to scenario
            scenario.log("Response Headers: \n" + response.headers());
            scenario.log("Response Body: \n" + responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            scenario.log("Test failed with exception: " + e.getMessage());
        }
    }

    @Then("^the correct strength is displayed$")
    public void response_shows_the_strength_by_combining_substance_name_active_numerator_strength_and_active_ingred_unit() {
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        String substanceName = JsonPath.with(response.getBody().asString()).getString("data.substanceName");
        String activeNumeratorStrength = JsonPath.with(response.getBody().asString()).getString("data.activeNumeratorStrength");
        String activeIngredUnit = JsonPath.with(response.getBody().asString()).getString("data.activeIngredUnit");
        Assert.assertEquals(substanceName + " " + activeNumeratorStrength + " " + activeIngredUnit,
                JsonPath.with(response.getBody().asString()).getString("data.strength"));
    }

    @Then("^Not found error should be shown$")
    public void notFoundErrorShouldBeShown() {
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
        Assert.assertEquals(NdcMessages.STRENGTH_NOT_FOUND, JsonPath.with(response.getBody().asString()).getString("moreInfo"));
    }

    @When("^I send a invalid request$")
    public void iSendAInvalidRequest() {
        String requestUrl = RestAssured.baseURI + configuration.getString(NDCMethodProperty) + "?code" + this.ndcCode;
        // Log request URL to scenario
        scenario.log("Request URL: " + requestUrl);
        response = request.get(requestUrl);
        String responseBody = response.prettyPrint();
        scenario.log("Response Body: \n" + responseBody);
    }

    @Then("^a Bad Request error is returned$")
    public void aBadRequestErrorIsReturned() {
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @When("^I send a request with the \"([^\"]*)\" attribute by giving extra comma$")
    public void iSendARequestWithTheAttributeByGivingExtraComma(String attribute) {
        String requestUrl = RestAssured.baseURI + configuration.getString(NDCMethodProperty) + "?code=" + this.ndcCode + "&responseFields=" + attribute + ",";
        // Log request URL to scenario
        scenario.log("Request URL: " + requestUrl);
        response = request.get(requestUrl);
        String responseBody = response.prettyPrint();
        scenario.log("Response Body: \n" + responseBody);
    }

    @Then("Internal Server Error is returned")
    public void internalServerErrorIsReturned() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
    }
}
