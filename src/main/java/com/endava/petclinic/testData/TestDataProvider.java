package com.endava.petclinic.testData;

import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import com.github.javafaker.Faker;


import java.text.SimpleDateFormat;

public class TestDataProvider {

    private Faker faker = new Faker();

    public Owner getOwner() {
        Owner owner = new Owner();
        owner.setFirstName(faker.name().firstName());
        owner.setLastName(faker.name().lastName());
        owner.setAddress(faker.address().fullAddress());
        owner.setCity(faker.address().city());
        owner.setTelephone(faker.number().digits(faker.number().numberBetween(1, 11)));

        return owner;
    }

    public Pet getPet(Owner owner, PetType petType) {
        Pet pet = new Pet();
        pet.setName(faker.name().firstName());
        pet.setBirthDate(new SimpleDateFormat("yyyy/MM/dd").format(faker.date().birthday()));
        pet.setOwner(owner);
        pet.setType(petType);

        return pet;
    }

    public PetType getPetType() {
        PetType petType = new PetType();
        petType.setName(faker.name().firstName());

        return petType;
    }

    public String getNumberWithDigits(int min, int max) {
        return faker.number().digits(faker.number().numberBetween(min, max));
    }
}
