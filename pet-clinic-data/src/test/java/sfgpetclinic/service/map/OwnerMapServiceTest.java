package net.frey.sfgpetclinic.service.map;

import net.frey.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class OwnerMapServiceTest {
    private final long OWNER_ID = 1L;
    private final static String LAST_NAME = "Smith";
    private OwnerMapService ownerMapService;
    private long existingId;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        ownerMapService.save(Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();

        assertThat(ownerSet.size(), is(1));
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(OWNER_ID);

        assertThat(owner.getId(), is(OWNER_ID));
    }

    @Test
    void saveExistingId() {
        existingId = 2L;
        Owner owner2 = Owner.builder().id(existingId).build();

        Owner savedOwner = ownerMapService.save(owner2);

        assertThat(savedOwner.getId(), is(existingId));
    }

    @Test
    void saeNoId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().build());

        assertThat(savedOwner, is(notNullValue()));
        assertThat(savedOwner.getId(), is(notNullValue()));
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(OWNER_ID));

        assertThat(ownerMapService.findAll().size(), is(0));
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(OWNER_ID);

        assertThat(ownerMapService.findAll().size(), is(0));
    }

    @Test
    void findByLastName() {
        Owner smith = ownerMapService.findByLastName(LAST_NAME);

        assertThat(smith, is(notNullValue()));
        assertThat(smith.getLastName(), is(LAST_NAME));
    }

    @Test
    void findByLastNameNotFound() {
        Owner smith = ownerMapService.findByLastName("foo");

        assertThat(smith, is(nullValue()));
    }
}