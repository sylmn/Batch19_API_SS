package com.eurotech.apiTests.day_02;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class C01_GetRequestWithPathParam {

    @BeforeClass
    public void setUpClass() {
        baseURI = "https://sdettest.eurotechstudy.eu";
    }

    @Test
    public void getMethodWithPathParam() {
        /**
         * given accept type is json
         * And path param id is 62
         * When user send GET request to url: "https://sdettest.eurotechstudy.eu"
         * When user send GET request to endpoint: "/sw/api/v1/allusers/getbyid/62"
         * Then status code should be 200
         * And content type should be "application/json; charset=UTF-8"
         * And response body should contains "Selim Gezer"
         */
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/sw/api/v1/allusers/getbyid/62");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        Assert.assertTrue(response.body().asString().contains("Selim Gezer"));
    }

    @Test
    public void getMethodWithPathParam_2() {

        /**
         * given accept type is json
         * And path param id is 62
         * When user send GET request to url: "https://sdettest.eurotechstudy.eu"
         * When user send GET request to endpoint: "/sw/api/v1/allusers/getbyid/62"
         * Then status code should be 200
         * And content type should be "application/json; charset=UTF-8"
         * And response body should contains "Selim Gezer"
         */
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("userID", 62)
                .when()
                .get("/sw/api/v1/allusers/getbyid/{userID}");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        Assert.assertTrue(response.body().asString().contains("Selim Gezer"));
    }

    @Test
    public void getUserByIdNegativeTest() {

        /** negative get user test
         * Given accept type is JSON
         * Ans pathParam id is 55
         * When the user sends a GET request to "/sw/api/v1/allusers/getbyid/{userId}"
         * Then status code should be 404
         * And content-type is "application/json; charset=UTF-8"
         * And "No User Record Found..." message should be in response payload
         **/
        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("userID", 55)
                .when()
                .get("/sw/api/v1/allusers/getbyid/{userID}");
        Assert.assertEquals(response.statusCode(), 404);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        Assert.assertTrue(response.body().asString().contains("No User Record Found..."));

    }
}
