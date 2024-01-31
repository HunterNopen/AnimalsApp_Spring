package com.example.animals_app;

import com.example.animals_app.model.Animal;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTestAssuredControllerTest {

    private static final String URI="http://localhost:8080/animals";


    @Test
    public void testGetAnimal(){

        Animal animal=when()
                .get(URI+"/get?id=1")
                .then()
                .statusCode(200)
                .extract()
                .as(Animal.class);

        assertEquals(10, animal.getCutenessLvl());
    }

    @Test
    public void testPostAnimal(){
        with()
                .body(new Animal(2L, "Capybara", "Grogggg", 10))
                .contentType("application/json")
                .post(URI+"/registration")
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Test
    public void testEditAnimal(){
        with().body(new Animal(2L, "Capybara", "Grogggg", 10))
                .contentType("application/json")
                .post(URI+"/editBack/1")
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Test
    public void testDeleteAnimal(){
        with()
                .get(URI+"/deleteBack/2")
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Test
    public void testDeleteNone(){
        with()
                .get(URI+"/deleteBack/100")
                .then()
                .statusCode(400)
                .log()
                .body();
    }

    @Test
    public void testEditNone(){
        with().body(new Animal(2L, "Capybara", "Grogggg", 10))
                .contentType("application/json")
                .post(URI+"/editBack/100")
                .then()
                .statusCode(400)
                .log()
                .body();
    }

    @Test
    public void testPostAlreadyHere(){
        with().body(new Animal(4L, "Capybara", "Grogggg", 10))
                .contentType("application/json")
                .post(URI+"/registration")
                .then()
                .statusCode(400)
                .log()
                .body();
    }
}
