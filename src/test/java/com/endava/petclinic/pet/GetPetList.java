package com.endava.petclinic.pet;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GetPetList extends TestBaseClass {

    @Test
    public void shouldGetPetList(){
        Owner owner = testDataProvider.getOwner();
        Response responseOwner = ownerClient.createOwner(owner);
        PetType petType = new PetType(3L);
        Pet pet =testDataProvider.getPet(responseOwner.as(Owner.class), petType);
        Response createPetResponse = petClient.createPet(pet);
        createPetResponse.then().statusCode(HttpStatus.SC_CREATED);
        Long petId = createPetResponse.body().jsonPath().getLong("id");

        //WHEN
        Response response = petClient.getPetList();

        //THEN
        //validez foecare field in parte
        response
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("find{ it -> it.id == %s}.name",withArgs(petId), is(pet.getName()));

        // --
//        Pet actualPet = response.body().jsonPath().param("id", petId).getObject("find{ it -> it.id == id}", Pet.class);
//        assertThat(actualPet, is(pet));

//        //pun intr-o lista de obj si verific daca lista contine ceea ce imi trebuie
//        List<Pet> petList = response.body().jsonPath().getList("", Pet.class);
//        assertThat(petList, hasItem(pet));
    }
}
