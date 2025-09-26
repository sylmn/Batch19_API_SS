package com.eurotech.apiTests.day_04;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class C01_HamcrestMatchers {
    @BeforeClass
    public void setUpClass() {
        baseURI = "https://sdettest.eurotechstudy.eu";
    }

    @Test
    public void hamcrestMatchers() {
        /**
         * given accept type is json
         * And path param id is 62
         * When user sends a get request to /sw/api/v1/allusers/getbyid/{id}
         * Then status code should be 200
         * And content type should be "application/json; charset=UTF-8"
         */
        given()
                .accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get("/sw/api/v1/allusers/getbyid/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8");
    }

    @Test
    public void hamcrestMatchers_headers() {

        /**
         *TASK
         * given accept type is JSON
         *And path param id is 111
         * When user send a get request to /sw/api/v1/allusers/getbyid/{id}
         * Then status code 200
         * And content Type application / json;
         charset = UTF - 8
         * And response header Server should be Apache
         * And response header date is not null
         */
        given()
                .accept(ContentType.JSON)
                .pathParam("id", 111)
                .when().log().all()
                .get("/sw/api/v1/allusers/getbyid/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .header("Server", "Apache")
                .header("Date", Matchers.notNullValue())
                .header("Transfer-Encoding", "chunked")
                .log().all();
    }

    @Test
    public void validateBodyWithHamcrest() {

        /**
         * given accept type is json
         * And path param id is 62
         * When user sends a get request to /sw/api/v1/allusers/getbyid/{id}
         * Then status code should be 200
         * And content type should be "application/json; charset=UTF-8"
         * user's id should be "62"
         * user's name should be "Selim Gezer"
         * user's job should be "QA Automation Engineer"
         * User's second skill should be "Selenium"
         * User's third education school name should be "Ankara University"
         * User's email should be "sgezer@gmail.com"
         * User's company should be "KraftTech"
         *
         *
         * Response headers should have "Date" header
         *
         * User's skills should contain Selenium
         * User's skills should contain Selenium and Java
         */
        given()
                .accept(ContentType.JSON)
                .pathParam("id", 62)
                .when()
                .get("/sw/api/v1/allusers/getbyid/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .header("Connection", "Upgrade, Keep-Alive")
                .body("id[0]", Matchers.equalTo(62))
                .body("name[0]", Matchers.equalTo("Selim Gezer"),
                        "job[0]", Matchers.equalTo("QA Automation Engineer"),
                        "skills[0][1]", Matchers.equalTo("Selenium"),
                        "[0].education[2].school", Matchers.equalTo("Ankara University"),
                        "[0].email", Matchers.equalTo("sgezer@gmail.com"),
                        "[0].company", Matchers.equalTo("KraftTech"))
                .headers("Server", Matchers.equalTo("Apache"),
                        "Keep-Alive", Matchers.equalTo("timeout=2, max=1000"));
    }

    @Test
    public void hamcrestMatchers_hasItem() {

        /**
         * 30 user çağıralım pagesize
         * ilk sayfa olsun page
         * end point "/sw/api/v1/allusers/alluser"
         * status code 200
         * content type json
         * second user id : 5
         * emaillerde "mailto:Ramanzi@test.com" var mı bakalım...
         * emaillerde "mailto:Ramanzi@test.com","mailto:sgezer@gmail.com" ve "mailto:jhon@test.com" var mı toplu bakalım..
         * bütün emailler @ içeriyor mu? assert edelim..
         *
         * hem request'i hem response'u loglayalım...
         */
        given()
                .accept(ContentType.JSON)
                .queryParam("pagesize", 30)
                .queryParam("page", 1)
                .when()
                .get("/sw/api/v1/allusers/alluser")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .body("id[1]", Matchers.equalTo(5))
                .body("email", Matchers.hasItem("Ramanzi@test.com"))
                .body("email", Matchers.hasItems("Ramanzi@test.com", "sgezer@gmail.com", "jhon@test.com"))
                .body("email", Matchers.everyItem(Matchers.containsString("@")));
    }

    @Test
    public void hamcrest_Task() {

        /**
         * HAMCREST TASK
         * given accept type is json
         * And query param pagesize is 30
         * And query param page is 1
         * And take the request logs
         * When user sends a get request to "/sw/api/v1/allusers/alluser"
         * Then status code should be 200
         * And content type should be application/json; charset=UTF-8
         * And response header Connection should be Upgrade, Keep-Alive
         * And response headers has Date
         * And json data should have "Selim Gezer","Jhon Nash","zafer" for name
         * And json data should have "QA" for job
         * And json data should have "İTÜ" for the tenth user's education school
         * And json data should have "Junior Developer" for the first user's third experience job
         * And json data should have "gazi" for the last user's first education school
         * And every email should have ".com"
         * Take the response headers log.
         *
         */
        given()
                .accept(ContentType.JSON)
                .queryParam("pagesize", 30)
                .queryParam("page", 1)
                .when().log().all()
                .get("/sw/api/v1/allusers/alluser")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .headers("Connection", Matchers.equalTo("Upgrade, Keep-Alive"),
                        "Date",Matchers.not(Matchers.isEmptyOrNullString()));

    }


}
