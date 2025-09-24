package com.eurotech.apiTests.day_02;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class C05_BookStorePathMethod {

    @BeforeClass
    public void setupClass() {
        baseURI = "https://bookstore.toolsqa.com";
    }

    @Test
    public void bookStroreGetTest_PathMethod() {

        /**
         * TASK
         * Given accept type json
         * When user sends a get request to https://bookstore.toolsqa.com/BookStore/v1/Books
         * end point:/BookStore/v1/Books
         * Then status code should be 200
         * And content type should be application/json; charset=utf-8
         *
         * And the first book isbn should be 9781449325862
         * And the first book publisher should be O'Reilly Media
         *
         * And the last book isbn should be "9781593277574"
         * And the last book author's name should be "Nicholas C. Zakas"
         *
         * And the third book isbn should be "9781449337711"
         * And the third book title should be "Designing Evolvable Web APIs with ASP.NET"
         *
         * How to take all isbn numbers
         * And the isbn numbers should have "9781491904244"
         * And the all isbn numbers should have 13 digits
         */
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .get("/BookStore/v1/Books");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8");

        // And the first book isbn should be 9781449325862

        //1.yol
        String firstBookIsbn = response.path("books[0].isbn");
        Assert.assertEquals(firstBookIsbn, "9781449325862");

        //2.yol
        String firstBookIsbn_2 = response.path("books.isbn[0]");
        Assert.assertEquals(firstBookIsbn_2, "9781449325862");


        // And the first book publisher should be O'Reilly Media

        String publisher = response.path("books[5].publisher");
        Assert.assertEquals(publisher, "O'Reilly Media");


        //Açıklama - explanation
        List<String> allISBNs = response.path("books.isbn");
        System.out.println("allISBNs = " + allISBNs);

        System.out.println("----------------");

        Map<String, Object> firstBook = response.path("books[0]");

        System.out.println("firstBook = " + firstBook);

        //* And the last book isbn should be "9781593277574"
        // And the last book author's name should be "Nicholas C. Zakas"
        String lastBookIsbn = response.path("books[-1].isbn");
        Assert.assertEquals(lastBookIsbn, "9781593277574");

        String lastBookAuthor = response.path("books.author[-1]");
        Assert.assertEquals(lastBookAuthor, "Nicholas C. Zakas");

        //And the third book isbn should be "9781449337711"
        //         * And the third book title should be "Designing Evolvable Web APIs with ASP.NET"
        String thirdBookIsbn = response.path("books[2].isbn");
        Assert.assertEquals(thirdBookIsbn, "9781449337711");
        String thirdBookTitle = response.path("books.title[2]");
        Assert.assertEquals(thirdBookTitle, "Designing Evolvable Web APIs with ASP.NET");


        //How to take all isbn numbers
        List<String> allBooksISBNs = response.path("books.isbn");

        //And the isbn numbers should have "9781491904244"
        Assert.assertTrue(allBooksISBNs.contains("9781491904244"));
        Assert.assertTrue(allBooksISBNs.stream().anyMatch(e -> e.equals("9781491904244")));

        //And the all isbn numbers should have 13 digits
        Assert.assertTrue(allBooksISBNs.stream().allMatch(n -> n.length() == 13));
    }
}
