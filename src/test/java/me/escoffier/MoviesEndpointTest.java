package me.escoffier;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class MoviesEndpointTest {

    @BeforeEach
    @Transactional
    public void setup() {
        Movie.deleteAll();
    }

    @Test
    public void testGetAllInitiallyEmpty() {
        given()
          .when().get("/movies")
          .then()
             .statusCode(200)
             .body("$.size()", is(0));
    }

    @Test
    public void testCreateMovie() {
        given()
          .contentType(ContentType.JSON)
          .body("{\"title\":\"Inception\",\"rating\":5}")
          .when().post("/movies")
          .then()
             .statusCode(200)
             .body("id", notNullValue())
             .body("title", is("Inception"))
             .body("rating", is(5));
    }

    @Test
    public void testGetOneAndNotFound() {
        int id = given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Interstellar\",\"rating\":4}")
            .when().post("/movies")
            .then().extract().path("id");

        given()
            .when().get("/movies/" + id)
            .then()
              .statusCode(200)
              .body("title", is("Interstellar"));

        given()
            .when().get("/movies/9999")
            .then()
              .statusCode(404);
    }

    @Test
    public void testUpdateMovie() {
        int id = given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Old Title\",\"rating\":2}")
            .when().post("/movies")
            .then().extract().path("id");

        given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"New Title\",\"rating\":3}")
            .when().patch("/movies/" + id)
            .then()
              .statusCode(200)
              .body("title", is("New Title"))
              .body("rating", is(3));
    }

    @Test
    public void testUpdateNotFound() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"Doesn't Matter\",\"rating\":1}")
            .when().patch("/movies/9999")
            .then()
              .statusCode(404);
    }

    @Test
    public void testDeleteMovie() {
        int id = given()
            .contentType(ContentType.JSON)
            .body("{\"title\":\"To be deleted\",\"rating\":1}")
            .when().post("/movies")
            .then().extract().path("id");

        given()
            .when().delete("/movies/" + id)
            .then()
              .statusCode(204);

        given()
            .when().get("/movies/" + id)
            .then()
              .statusCode(404);
    }

    @Test
    public void testDeleteNotFound() {
        given()
            .when().delete("/movies/9999")
            .then()
              .statusCode(404);
    }
}