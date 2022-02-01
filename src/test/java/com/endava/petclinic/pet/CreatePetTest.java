package com.endava.petclinic.pet;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreatePetTest extends TestBaseClass {

    @Test
    public void shouldCreatePetGivenValidData() {
        //GIVEN
        Owner owner = testDataProvider.getOwner();
        Response responseOwner = ownerClient.createOwner(owner);
        PetType petType = new PetType(3L);
        Pet pet =testDataProvider.getPet(responseOwner.as(Owner.class), petType);

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", is(notNullValue()));
    }

    @Test
    public void shouldFailCreatePetGivenEmptyName() {
        //GIVEN
        Owner owner = testDataProvider.getOwner();
        Response responseOwner = ownerClient.createOwner(owner);
        PetType petType = new PetType(3L);
        Pet pet =testDataProvider.getPet(responseOwner.as(Owner.class), petType);
        pet.setName("");

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
