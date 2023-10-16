package org.example;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class HTTPRequests_Practice {
    int id;
    // To use get request
    @Test
    void getAllUsers(){
    given()
    .when()
            .get("https://reqres.in/api/users?page=2")
    .then()
            .statusCode(200)
            .body("page",equalTo(2))
            .log().all();
    }

    // To use post request
    @Test
    void createUser(){
        HashMap data = new HashMap<>();
        data.put("name","Sowmya");
        data.put("Job","Tester");

        given()
                .contentType("application/json")
                .body(data)
        .when()
                .post("https://reqres.in/api/user")
        .then()
                .statusCode(201)
                .log().all();
    }

    // To use post request and capture response
    @Test
    void createUser_captureResponse(){

        HashMap data = new HashMap<>();
        data.put("name","Sowmya Reddy");
        data.put("Job","Test Engineer");

        id=given()
                .contentType("application/json")
                .body(data)
        .when()
                .post("https://reqres.in/api/user")
                .jsonPath().getInt("id");
    }

    @Test(dependsOnMethods = {"createUser_captureResponse"})
    void updateUser(){
        HashMap data = new HashMap();
        data.put("name","Sow");
        data.put("Job","Trainee");

        given()
                .contentType("application/json")
                .body(data)
        .when()
                .put("https://reqres.in/api/user/"+id)
        .then()
                .statusCode(200)
                .log().all();
    }

    @Test(dependsOnMethods = {"createUser_captureResponse"})
    void deleteUser(){
        given()

        .when()
                .delete("https://reqres.in/api/user/"+id)
        .then()
                .statusCode(204)
                .log().all();
    }

}
