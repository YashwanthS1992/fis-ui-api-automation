package com.coindesk.api.tests;  // Package declaration

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoinDeskApiTest {

    @Test
    public void testCurrency() {
        // Send the GET request
        Response response = RestAssured.get("https://api.coindesk.com/v1/bpi/currentprice.json");

        // Verify that the response status code is 200 (OK)
        assertEquals(200, response.getStatusCode(), "Status code should be 200");

        // Parsing the response body as a JSON object
        // Verify the 3 BPIs: USD, GBP, EUR
        String bpiContent = response.jsonPath().getString("bpi");
        assertNotNull(bpiContent, "BPI data should not be null");

        // Verify that three keys USD, GBP, EUR
        assertTrue(bpiContent.contains("USD"), "BPI should have USD");
        assertTrue(bpiContent.contains("GBP"), "BPI should have GBP");
        assertTrue(bpiContent.contains("EUR"), "BPI should have EUR");

        // Check if the 'description' for GBP is "British Pound Sterling"
        String gbpDescription = response.jsonPath().getString("bpi.GBP.description");
        assertEquals("British Pound Sterling", gbpDescription);
        System.out.println("Test case passed successfully!");
    }
}
