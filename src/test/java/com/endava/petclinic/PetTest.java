package com.endava.petclinic;

import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class PetTest {

    @Test
    public void petTest() {

        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                    //printeaza toate informatiile direct din request
                .when()
                .get("api/pets")
                   //printeaza response
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createPet() {

        Owner owner = new Owner(2L);
        PetType dog = new PetType(1L);
        Pet pet = new Pet("Foxy", "2021/02/20", dog, owner);

        given().baseUri("http://bhdtest.endava.com")
                .port(8080)
                .basePath("petclinic")
                .contentType(ContentType.JSON)
                .body(pet)
                
                .when()
                .post("api/pets")
                
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void createNewPet() {
        //GIVEN
        Owner owner = new Owner(2L);
        PetType dog = new PetType(1L);
        Pet pet = new Pet("Foxy", "2021/02/20", dog, owner);

        //WHEN
        Response response = given().baseUri("http://bhdtest.endava.com")
                .port(8080)
                .basePath("petclinic")
                .contentType(ContentType.JSON)
                .body(pet)
                
                .when()
                .post("api/pets")
                ;

        //THEN
        response.then()
                .statusCode(HttpStatus.SC_CREATED)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("name", is(pet.getName()))
                .body("birthDate", is(pet.getBirthDate()))
                .body("type.id", is(pet.getType().getId().intValue()))
                .body("owner.id", is(pet.getOwner().getId().intValue()))
                .body("visits", empty());

        Pet actualPet = response.as(Pet.class);
        assertThat(actualPet, is(pet));
    }

    @Test
    public void getPetById() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .pathParam("petId", 29)
                
                .when()
                .get("/api/pets/{petId}")
                
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteOwnerById() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .pathParam("petId", 29)
                
                .when()
                .delete("/api/pets/{petId}")
                
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
