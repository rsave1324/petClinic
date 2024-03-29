package com.endava.petclinic;

import com.endava.petclinic.model.Owner;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OwnerTest {

    @Test
    public void getOwner() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
        .when()
                .get("api/owners")
        .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createOwner() {

        //GIVEN
        Owner owner = new Owner("John", "Doe", "12 Oak Blvd.", "DC", "0744458744");
        System.out.println(owner);

        //WHEN
        Response response = given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .contentType(ContentType.JSON)
                .body(owner)
                
        .when()
                .post("api/owners")
                ;

        //THEN
        response.then()
                .statusCode(HttpStatus.SC_CREATED)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("lastName", is(owner.getLastName()))
                .body("firstName", is(owner.getFirstName()))
                .body("address", is(owner.getAddress()))
                .body("city", is(owner.getCity()))
                .body("telephone", is(owner.getTelephone()))
                .body("pets", empty());

        Owner actualOwner = response.as(Owner.class);
        assertThat(actualOwner, is(owner));
    }

    @Test
    public void getOwnerById() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .pathParam("ownerId", 71)
                
        .when()
                .get("/api/owners/{ownerId}")
                
        .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteOwnerById() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .pathParam("ownerId", 71)
                
        .when()
                .delete("/api/owners/{ownerId}")
                
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
