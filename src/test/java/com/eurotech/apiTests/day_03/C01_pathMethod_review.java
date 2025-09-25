package com.eurotech.apiTests.day_03;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.dynalink.linker.LinkerServices;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class C01_pathMethod_review {

    String gorestURL = "https://gorest.co.in";

    @Test
    public void gorest_pathMethod() {

        /**
         CT D03
         Given accept type json
         When user sends a get request to https://gorest.co.in/public/v2/users
         Then status code should be 200
         And content type should be application/json; charset=utf-8
         And the second user id should be 8145361
         And the third user name should be "Aayushman Kocchar"
         */
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get(gorestURL + "/public/v2/users");
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8");

        // verify the second user id should be 8145361
        int secondID = 8145361;
        int actualID = response.path("id[1]");
        Assert.assertEquals(actualID, secondID);

        // verify the third username should be "Aayushman Kocchar"
        String thirdName = "Aayushman Kocchar";
        String actualName = response.path("name[2]");
        Assert.assertEquals(actualName, thirdName);

        //how many users has?
        List<String> names = response.path("name");
        System.out.println("names = " + names);
        int userCount = names.size();
        System.out.println("userCount = " + userCount);
    }

}
