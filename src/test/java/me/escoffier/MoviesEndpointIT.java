package me.escoffier;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;

@QuarkusIntegrationTest
class MoviesEndpointIT extends MoviesEndpointTest {
    // Execute the same tests but in packaged mode.


    @Override
    public void setup() {
        RestAssured.delete("/movies");
    }
}
