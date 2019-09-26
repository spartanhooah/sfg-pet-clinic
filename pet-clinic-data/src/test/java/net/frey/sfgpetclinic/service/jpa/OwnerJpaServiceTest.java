package net.frey.sfgpetclinic.service.jpa;

import net.frey.sfgpetclinic.model.Owner;
import net.frey.sfgpetclinic.repository.OwnerRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {
    private static final String LAST_NAME = "Smith";
    private final Long ID = 1L;
    private Owner defaultOwner;

    @InjectMocks
    private OwnerJpaService ownerJpaService;

    @Mock
    private OwnerRepository ownerRepository;

    @BeforeEach
    void setUp() {
        defaultOwner = Owner.builder().id(ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        Mockito.when(ownerJpaService.findByLastName(ArgumentMatchers.any())).thenReturn(defaultOwner);
        Owner smith = ownerJpaService.findByLastName(LAST_NAME);

        MatcherAssert.assertThat(smith.getLastName(), Matchers.is(LAST_NAME));
        Mockito.verify(ownerRepository).findByLastName(ArgumentMatchers.any());
    }

    @Test
    void findAll() {
        Set<Owner> returnOwnerSet = new HashSet<>();
        returnOwnerSet.add(Owner.builder().id(2L).build());
        returnOwnerSet.add(Owner.builder().id(3L).build());

        Mockito.when(ownerRepository.findAll()).thenReturn(returnOwnerSet);

        Set<Owner> owners = ownerJpaService.findAll();

        MatcherAssert.assertThat(owners, Matchers.is(returnOwnerSet));
    }

    @Test
    void findById() {
        Mockito.when(ownerRepository.findById(ID)).thenReturn(Optional.of(defaultOwner));
        Owner returnedOwner = ownerJpaService.findById(ID);

        MatcherAssert.assertThat(returnedOwner, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(returnedOwner, Matchers.is(defaultOwner));
    }

    @Test
    void findByIdNotFound() {
        Mockito.when(ownerRepository.findById(ID)).thenReturn(Optional.empty());
        Owner returnedOwner = ownerJpaService.findById(ID);

        MatcherAssert.assertThat(returnedOwner, Matchers.is(Matchers.nullValue()));
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(2L).build();
        Mockito.when(ownerRepository.save(ArgumentMatchers.any())).thenReturn(defaultOwner);

        Owner savedOwner = ownerJpaService.save(ownerToSave);

        MatcherAssert.assertThat(savedOwner, Matchers.is(Matchers.notNullValue()));
        Mockito.verify(ownerRepository).save(ArgumentMatchers.any());
    }

    @Test
    void delete() {
        ownerJpaService.delete(defaultOwner);

        Mockito.verify(ownerRepository).delete(ArgumentMatchers.any());
    }

    @Test
    void deleteById() {
        ownerJpaService.deleteById(ID);

        Mockito.verify(ownerRepository).deleteById(ArgumentMatchers.anyLong());
    }
}