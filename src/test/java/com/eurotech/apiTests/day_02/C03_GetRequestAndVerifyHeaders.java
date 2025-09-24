package com.eurotech.apiTests.day_02;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class C03_GetRequestAndVerifyHeaders {
    @BeforeClass
    public void setUpClass() {
        baseURI = "https://sdettest.eurotechstudy.eu";
    }

    @Test
    public void headersTest() {

        /** Headers test
         * USING PATH PARAM WITH REQUEST
         * given accept type is json
         * And path param id is 62
         * When user send GET request to url: "https://sdettest.eurotechstudy.eu"
         * When user send GET request to endpoint: "/sw/api/v1/allusers/getbyid/{userID}"
         * Then status code should be 200
         * And content type should be "application/json; charset=UTF-8"
         * And response headers should have followings:
         * Connection = Upgrade, Keep-Alive
         * Content-Type = application/json; charset=UTF-8
         * keep-alive = timeout=2, max=1000
         *
         * Date ---> should be a date header
         */
        Response response = given()
                .accept(ContentType.JSON)       //sadece Json kabul ediyorum
                .contentType(ContentType.JSON) //gönderirsem Json
                .and()
                .pathParam("userID", 62)
                .when().log().all()
                .get("/sw/api/v1/allusers/getbyid/{userID}")
                .prettyPeek();  //response'in detaylarini yaziyor

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        //response headers test
        Assert.assertEquals(response.header("Connection"), "Upgrade, Keep-Alive");
        Assert.assertEquals(response.header("Content-Type"), "application/json; charset=UTF-8");
        Assert.assertEquals(response.header("Keep-Alive"), "timeout=2, max=1000");

        //date header'i olup olmadigini test edelim
        Assert.assertTrue(response.headers().hasHeaderWithName("Date"));

        //date bilgisini alalim
        System.out.println("response.header(\"Date\") = " + response.header("Date"));
        System.out.println("response.headers().getValue(\"Date\") = " + response.headers().getValue("Date"));

    }
}
