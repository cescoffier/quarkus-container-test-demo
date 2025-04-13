package me.escoffier;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

@QuarkusIntegrationTest
class MoviesEndpointIT extends MoviesEndpointTest {
    // Execute the same tests but in packaged mode.


    @Override
    @BeforeEach
    public void setup() {
        System.out.println(">>>>>>>>> " + RestAssured.baseURI);
        RestAssured.delete("/movies");
    }
}
