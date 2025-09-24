package com.eurotech.apiTests.day_02;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.*;

public class C02_GetRequestWithQueryParam {
    String petStoreBaseURL = "https://petstore.swagger.io";

    @BeforeClass
    public void setUpClass() {
        baseURI = "https://sdettest.eurotechstudy.eu";
    }

    @Test
    public void findByStatus_QueryParam() {

        /** Class Task
         * Given accept type is JSON
         * and query param is status sold
         * base url = "https://petstore.swagger.io"
         * When the user sends GET request to /v2/pet/findByStatus
         * Then verify that status code is 200
         * and verify that body is JSON format :application/json
         * and "sold" should be in response body
         */

        Response response = given()
                .accept(ContentType.JSON)
                .queryParam("status", "sold")
                .when()
                .get(petStoreBaseURL + "/v2/pet/findByStatus");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json");
        Assert.assertTrue(response.asString().contains("sold"));

        response.prettyPrint();
    }

    @Test
    public void queryParamTest_1() {

        /**
         * Given accept type json
         * base url "https://sdettest.eurotechstudy.eu"
         * And query parameter value pagesize 1
         * When user sends GET request to /sw/api/v1/allusers/alluser
         * Then response status code should be 200
         * And response content-type application/json; charset=UTF-8
         * And response body contains "Java" message
         */
        Response pagesize = given()
                .accept(ContentType.JSON)
                .queryParam("pagesize", 1)
                .when()
                .get("/sw/api/v1/allusers/alluser");
        Assert.assertEquals(pagesize.statusCode(), 200);
        Assert.assertEquals(pagesize.contentType(), "application/json; charset=UTF-8");
        Assert.assertTrue(pagesize.asString().contains("Java"));
    }

    @Test
    public void queryParamTest_2() {

        /**
         * Given accept type json
         * And query parameter value name Hasan Gezer
         * And query parameter value Skills Postman
         * And query parameter value pagesize 1
         * And query parameter value page 1
         * When user sends GET request to /sw/api/v1/allusers/alluser
         * Then response status code should be 200
         * And response content-type application/json; charset=UTF-8
         * And response body contains "hgezer@gmail.com" message
         */
        Response response = given()
                .accept(ContentType.JSON)
                .queryParam("pagesize", 1)
                .queryParam("name", "Hasan Gezer")
                .queryParam("skills", "Postman")
                .queryParam("page", 1)
                .when()
                .get("/sw/api/v1/allusers/alluser");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");
        Assert.assertTrue(response.asString().contains("hgezer@gmail.com"));
        response.prettyPrint();

    }
    @Test
    public void queryParamTestWithMap() {

        /**
         * Given accept type json
         * And query parameter value name Hasan Gezer
         * And query parameter value Skills Postman
         * And query parameter value pagesize 1
         * And query parameter value page 1
         * When user sends GET request to /sw/api/v1/allusers/alluser
         * Then response status code should be 200
         * And response content-type application/json; charset=UTF-8
         * And response body contains "hgezer@gmail.com" message
         */
        Map<String, Object>queryMap=new HashMap<>();
        queryMap.put("name", "Hasan Gezer");
        queryMap.put("skills", "Postman");
        queryMap.put("page", 1);
        queryMap.put("pagesize", 1);

        Response response = given()
                .accept(ContentType.JSON)
                .queryParams(queryMap)
                .when().log().all() //requesti nasil gönderdigimi de gösteriyor bu komutlarla
                .get("/sw/api/v1/allusers/alluser");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");
        Assert.assertTrue(response.asString().contains("hgezer@gmail.com"));
        response.prettyPrint();

    }

}
