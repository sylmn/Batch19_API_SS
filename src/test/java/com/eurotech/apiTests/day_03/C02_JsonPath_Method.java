package com.eurotech.apiTests.day_03;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.*;

public class C02_JsonPath_Method {
    @BeforeClass
    public void setUpClass() {
        baseURI = "https://sdettest.eurotechstudy.eu";
    }

    @Test
    public void getOneUser_JsonPath() {

        /**
         * TASK
         * Given accept type is json
         * And Path param user id is 62
         * When user sends a GET request to /allusers/getbyid/{id}
         * Then the status Code should be 200
         * And Content type json should be "application/json; charset=UTF-8"
         * Use Json Path method
         * And user's name should be Selim Gezer
         * And user's id should be 62
         * And user's email should be sgezer@gmail.com
         * And user's Third skill should be "TestNG"
         * And user's skills should contain "Java","Selenium","TestNG","Cucumber"
         */

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get("/sw/api/v1/allusers/getbyid/{id}");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");
        response.prettyPrint();

        // path method kullnarak yapalım
        String name = "Selim Gezer";
        String actualName = response.path("name[0]");
        List<String> a = response.path("name");
        System.out.println("a = " + a);
        Assert.assertEquals(actualName, name);

        //Json path method ile
        JsonPath jsonPath = response.jsonPath();

        //user name should be Selim Gezer
        Assert.assertEquals(jsonPath.getString("name[0]"), "Selim Gezer");

        //user's id should be 62
        int id = jsonPath.getInt("id[0]");
        System.out.println("id = " + id);
        Assert.assertEquals(id, 62);

        //user's email should be sgezer@gmail.com
        Assert.assertEquals(jsonPath.get("email[0]"), "sgezer@gmail.com");

        //user's Third skill should be "TestNG"
        //  "skills[0][2]"
        // [0].skills[2]
        System.out.println("jsonPath.get(\"[0].skills[2]\") = " + jsonPath.get("[0].skills[2]"));
        System.out.println("jsonPath.get(\"skills[0][2]\") = " + jsonPath.get("skills[0][2]"));

        // [] --> list anlamına gelir --> indexi [] ile gösterilir
        // {} --> map anlamına gelir  --> "." ve "key" değeri gerekir

        Assert.assertEquals(jsonPath.get("skills[0][2]"), "TestNG");

        //user's skills should contain "Java" "Selenium" "TestNG" "Cucumber"
        List<String> expectedSkills = new ArrayList<>(Arrays.asList("Java", "Selenium", "TestNG", "Cucumber"));
        List<String> actualSkills = jsonPath.getList("skills[0]");
        Assert.assertEquals(actualSkills, expectedSkills);   // mutlaka liste eleman sırası aynı olmalıdır
    }

    @Test
    public void testWithJsonPath_2() {

        /**
         * TASK
         * Given accept type is json
         * When user sends a GET request to /sw/api/v1/allusers/alluser
         * query params pagesize=5, page=1
         * Then the status Code should be 200
         * And Content type json should be "application/json; charset=UTF-8"
         */
        Response response = given()
                .accept(ContentType.JSON)
                .queryParam("pagesize", 5)
                .queryParam("page", 1)
                .when()
                .get("/sw/api/v1/allusers/alluser");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        //jsonPath
        JsonPath jsonPath1 = response.jsonPath();

        //ilk elemanin id si 1 mi?
        Assert.assertEquals(jsonPath1.getInt("id[0]"), 1);

        //son elemanin id 33 oldugunu dogrula
        int lastId = jsonPath1.getInt("id[4]");
        int lastId_2 = jsonPath1.getInt("id[-1]");
        System.out.println("jsonPath1.getInt(\"id[-2]\") = " + jsonPath1.getInt("id[-2]"));

        //dördüncü elemanın email i "wilini3845@oncebar.com" doğrula
        Assert.assertEquals(jsonPath1.get("email[3]"), "wilini3845@oncebar.com");
        Assert.assertEquals(jsonPath1.get("email[-2]"), "wilini3845@oncebar.com");

        //ilk elemanin skillerini dogrula
        List<String> actualSkills = jsonPath1.getList("skills[0]");
        List<String> expectedList = new ArrayList<>(Arrays.asList("PHP", "Java"));
        Assert.assertEquals(actualSkills, expectedList);

        //ilk elemanin ilk education scholl ismi "School or Bootcamp" dogrula
        String schoolName = jsonPath1.getString("education[0][0].school");
        System.out.println("schoolName = " + schoolName);
        String schoolName_2 = jsonPath1.getString("[0].education[0].school");
        System.out.println("schoolName_2 = " + schoolName_2);
        String schoolName_3 = jsonPath1.getString("[0].education.school[0]");
        System.out.println("schoolName_3 = " + schoolName_3);
        String schoolName_4 = jsonPath1.getString("education[0].school[0]");
        System.out.println("schoolName_4 = " + schoolName_4);
    }

    @Test
    public void testWithFindAll() {
        /**
         * Given accept type json
         * And query parameter value pagesize 50
         * And query parameter value page 1
         * When user sends GET request to /allusers/alluser
         * Then response status code should be 200
         * And response content-type application/json; charset=UTF-8
         *
         * skillerinden herhangi biri java olanların id.lerini bir liste alalım
         * skillerinden ikincisi java olanların name.lerini bir liste alalım
         * education bölümü boş olanların namelerini bir liste alalım
         */
        Response response = given()
                .accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 1)
                .when()
                .get("/sw/api/v1/allusers/alluser");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=UTF-8");

        JsonPath jsonPath = response.jsonPath();

        //skillerinden herhangi biri java olanların id.lerini bir liste alalım
        List<Integer> list = jsonPath.getList("findAll{it.skills.contains('Java')}.id");
        System.out.println("list = " + list);

        //skillerinden ikincisi java olanların name lerini bir liste al
        List<Integer> names = jsonPath.getList("findAll { it.skills[1] == 'Java' }.name");
        System.out.println("names = " + names);

        //education olmayanların email lerini liste alalım
        List<Integer> emails = jsonPath.getList("findAll {!it.education}.email");
        System.out.println("emails = " + emails);

        //aynısı pathMethod da  da geçerlidir
        List<Integer> list_2 = response.path("findAll {it.skills.contains('Java')}.id");
        System.out.println("list_2 = " + list_2);
    }

    @Test
    public void jsonPath_Task() {
        /** TASK
         *  Given accept type is json
         *  When user sends a GET request to sw/api/v1/allusers/alluser
         *  query params pagesize=50, page=56
         *  Then the status Code should be 200
         *  And Content type json should be "application/json; charset=UTF-8"
         *
         *  Make following verification by using jsonPath method..
         *  third user userId should be 3393
         *  third user second skill should be "Selenium"
         *  third user email should be "sld@gmail.com"
         *
         *  how many user do we have? it should be 23
         *
         * sixth user first education record's school should be "Bilkent"
         * sixth user first experience record's location should be "Olsonville"
         * sixth user second experience record's id should be 2609
         * sixth user id should be 3396
         */
        ValidatableResponse validatableResponse = given()
                .accept(ContentType.JSON)
                .queryParam("pagesize", 50)
                .queryParam("page", 56)
                .when()
                .get("sw/api/v1/allusers/alluser")
                .then()
                .assertThat().statusCode(200)
                .and()
                .assertThat().contentType("application/json; charset=UTF-8");

        JsonPath jsonPath = validatableResponse.extract().jsonPath();

        //third user userId should be 3393
        Assert.assertEquals(jsonPath.getInt("id[2]"), 3393);
        Assert.assertEquals(jsonPath.get("skills[2][1]"), "Selenium");
        Assert.assertEquals(jsonPath.get("email[2]"), "sld@gmail.com");

        List<Object> names = jsonPath.getList("name");
        int userCount= names.size();
        Assert.assertEquals(userCount, 23);

        Assert.assertEquals(jsonPath.get("education[5].school[0]"), "Bilkent");
        Assert.assertEquals(jsonPath.get("experience[5].location[0]"),"Olsonville");
        Assert.assertEquals(jsonPath.getInt("experience[5].id[1]"),2609);



    }
}
