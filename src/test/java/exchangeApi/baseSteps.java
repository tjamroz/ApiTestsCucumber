package exchangeApi;

import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class baseSteps implements En {
    private RequestSpecification request;
    private Response response;
    private final Tools tools = new Tools();

    public baseSteps() {
        Given("I have basic URL", this::setBasicURL);

        Given("^I want to set Base to (.*)$", this::setBase);

        Given("^I want to get rates for currency (.*)$", this::setSymbols);

        When("I ask latest endpoint", this::getLatestRates);

        When("I ask incomplete endpoint", this::getResponse);
        //todo duplications
        When("I get correct error response for incomplete url", this::verifyErrorResponseForUrl);

        When("I get correct error response for old date", this::verifyErrorResponseForDate);

        Then("^I get correct (.*) JSON schema with currencies$", this::compareSchema);

        And("^I get (\\d+) HTTP code$", this::compareStatusCode);

        When("^I ask for rates from (\\d+)-(\\d+)-(\\d+)$", this::getHistoricalRates);

        And("^I get correct (.*) set$", this::verifyBaseCurrencyResponse);

        And("^Response contains correct date (\\d+)-(\\d+)-(\\d+)$", this::verifyDate);

        And("^Response contains actual data$", () -> {
            LocalDate date= LocalDate.now();
            //assuming that tests will be run before 16:00
            verifyDate(date.getYear(),date.getMonthValue(), (date.getDayOfMonth()-1));
        });

        And("^I get correct error response for not supported base$", this::verifyBaseError);

        And("^I get only specified (.*) in response$", this::verifyResponseOnlyCurrency);
    }

    private void getHistoricalRates(Integer year, Integer month, Integer day) {
        response = request.when().get(year + "-" + month + "-" + day);
    }

    private void verifyDate(Integer year, Integer month, Integer day) {
        String dateToCheck = year + "-" + tools.setLeadingZero(month) + "-" + tools.setLeadingZero(day);
        String dayNamed = tools.getNameOfWeekDay(dateToCheck);
        if (dayNamed.equals("Sat")) {
            dateToCheck = year + "-" + tools.setLeadingZero(month) + "-" + tools.setLeadingZero(day - 1);
        } else if (dayNamed.equals("Sun")) {
            dateToCheck = year + "-" + tools.setLeadingZero(month) + "-" + tools.setLeadingZero(day - 2);
        }
        assertEquals(dateToCheck, response.jsonPath().get("date"));
    }

    private void getLatestRates() {
        response = request.when().get("latest");
    }

    private void getResponse() {
        response = request.when().get();
    }

    private void verifyErrorResponseForUrl() {
        assertEquals("{\"error\":\"time data 'api' does not match format '%Y-%m-%d'\"}", response.getBody().asString());
    }

    private void verifyErrorResponseForDate() {
        assertEquals("{\"error\":\"There is no data for dates older then 1999-01-04.\"}", response.getBody().asString());
    }

    private void verifyBaseError() {
        assertEquals("{\"error\":\"Base 'randomstring' is not supported.\"}", response.getBody().asString());
    }

    private void compareSchema(String schema) {
        String fileName = null;
        if (schema.equalsIgnoreCase("latest")) {
            fileName = "latestSchema.json";
        } else if (schema.equalsIgnoreCase("basic")) {
            fileName = "basicSchema.json";
        }
        response.then().body(matchesJsonSchemaInClasspath(fileName));
    }

    private void compareStatusCode(int code) {
        response.then().statusCode(code);
    }

    private void setBasicURL() {
        RestAssured.baseURI = tools.getUrl();
        request = given();
    }

    private void setBase(String baseCurrency) {
        request = request.queryParam("base", baseCurrency);
    }

    private void verifyResponseOnlyCurrency(String currSymbol) {
        LinkedHashMap rates = response.jsonPath().get("rates");
        assertEquals(1, rates.size());
        assertTrue(rates.containsKey(currSymbol));
    }

    private void setSymbols(String currencySymb) {
        request = request.queryParam("symbols", currencySymb);
    }

    private void verifyBaseCurrencyResponse(String baseCurrency) {
        assertEquals(baseCurrency, response.jsonPath().get("base"));
    }

}