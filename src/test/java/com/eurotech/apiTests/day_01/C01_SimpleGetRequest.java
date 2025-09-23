package com.eurotech.apiTests.day_01;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class C01_SimpleGetRequest {

    String bookStoreBaseURL = "https://bookstore.toolsqa.com";
    String petstoreBaseURL = "https://petstore.swagger.io";

    @Test
    public void basicGetRequest() {
        /**
         *  When user send GET request to url: "https://bookstore.toolsqa.com"
         *  When user send GET request to endpoint: "/BookStore/v1/Books"
         *  Print response status code
         *  Print whole json body (response)
         *  firstly control request and response data from swagger or postman
         */

        Response response = RestAssured.get("https://bookstore.toolsqa.com/BookStore/v1/Books");
        System.out.println("response.statusCode() = " + response.statusCode());
        response.prettyPrint();
    }

    @Test
    public void basicGetRequest_2() {
        /**
         *  When user send GET request to url: "https://bookstore.toolsqa.com"
         *  When user send GET request to endpoint: "/BookStore/v1/Books"
         *  Make base url global variable
         *  Print response status code
         *  Print whole json body
         *  firstly control request and response data from swagger or postman
         */
        Response response = RestAssured.get(bookStoreBaseURL + "/BookStore/v1/Books");
        System.out.println("response.statusCode() = " + response.statusCode());
        response.prettyPrint();
    }

    @Test
    public void basicRequestWithHeaders() {
        /**
         *  When user send GET request to url: "https://bookstore.toolsqa.com"
         *  When user send GET request to endpoint: "/BookStore/v1/Books"
         *  Given accept type is JSON
         *  Then verify that response status code 200
         *  and body is JSON format ("application/json; charset=utf-8")
         *  firstly control request and response data from swagger or postman
         */
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(bookStoreBaseURL + "/BookStore/v1/Books");

        //verify that response status code 200
        Assert.assertEquals(response.statusCode(), 200);

        //verify response content type
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8");
    }

    @Test
    public void basicGetRequestWithHeaders_Task() {
        /**
         *  When user send GET request to url: "https://petstore.swagger.io/v2"
         *  When user send GET request to endpoint: "/v2/store/inventory"
         *  Given accept type is JSON
         *  Then verify that response status code 200
         *  and body is JSON format ("application/json")
         *  firstly control request and response data from swagger or postman
         */
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(petstoreBaseURL + "/v2/store/inventory");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json");
    }

    @Test
    public void getRequestVerificationWithRestAssured() {
        /** Verify test case by using RestAssured Library
         *  When user send GET request to url: "https://bookstore.toolsqa.com"
         *  When user send GET request to endpoint: "/BookStore/v1/Books"
         *  Given accept type is JSON
         *  Then verify that response status code 200
         *  and body is JSON format (application/json; charset=utf-8)
         *  firstly control request and response data from swagger or postman
         */
        ValidatableResponse validatableResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(bookStoreBaseURL + "/BookStore/v1/Books")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json; charset=utf-8");

        //ek bilgi
        validatableResponse.statusCode(200);
        validatableResponse.contentType("application/json; charset=utf-8");
    }

    @Test
    public void getRequestVerificationWithRestAssured_Task() {

        /** Verify test case by using RestAssured Library
         *  When user send GET request to url: "https://petstore.swagger.io/v2"
         *  When user send GET request to endpoint: "/v2/store/inventory"
         *  Given accept type is JSON
         *  Then verify that response status code 200
         *  and body is JSON format ("application/json")
         *  firstly control request and response data from swagger or postman
         */

        ValidatableResponse validatableResponse = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(petstoreBaseURL + "/v2/store/inventory")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType("application/json");
    }

    @Test
    public void validateResponseBodyWithContainsMethod() {

        /**
         * When user send GET request to url: "https://bookstore.toolsqa.com"
         * When user send GET request to endpoint: "/BookStore/v1/Books"
         * Given accept type is JSON
         * Then verify that response status code 200
         * and body is JSON format
         * and json body contains "Axel Rauschmayer" as an author
         *
         * firstly control request and response data from swagger or postman
         */

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("https://bookstore.toolsqa.com/BookStore/v1/Books");

        //verification
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8");

        //body verification
        String wholeBody = response.body().asString();
        String wholeBody2 = response.asString();

        Assert.assertTrue(wholeBody.contains("Axel Rauschmayer"));
        Assert.assertTrue(wholeBody2.contains("Axel Rauschmayer"));

    }

    @Test
    public void validateResponseBodyWithContainsMethod_Task() {
        /**
         *  When user send GET request to url: "https://petstore.swagger.io/v2"
         *  When user send GET request to endpoint: "/v2/store/inventory"
         *  Given accept type is JSON
         *  Then verify that response status code 200
         *  and body is JSON format ("application/json")
         *  and json body contains "available" as category
         *
         *  firstly control request and response data from swagger or postman
         */
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(petstoreBaseURL + "/v2/store/inventory");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json");
        String wholeBody= response.body().asString();
        Assert.assertTrue(wholeBody.contains("available"));

    }
}
