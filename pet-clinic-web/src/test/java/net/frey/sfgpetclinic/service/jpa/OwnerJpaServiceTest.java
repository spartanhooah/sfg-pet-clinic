package net.frey.sfgpetclinic.service.jpa;

import net.frey.sfgpetclinic.model.Owner;
import net.frey.sfgpetclinic.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {
    private static final String LAST_NAME = "Smith";
    private final Long ID = 1L;
    Owner defaultOwner;

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
        when(ownerJpaService.findByLastName(any())).thenReturn(defaultOwner);
        Owner smith = ownerJpaService.findByLastName(LAST_NAME);

        assertThat(smith.getLastName(), is(LAST_NAME));
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> returnOwnerSet = new HashSet<>();
        returnOwnerSet.add(Owner.builder().id(2L).build());
        returnOwnerSet.add(Owner.builder().id(3L).build());

        when(ownerRepository.findAll()).thenReturn(returnOwnerSet);

        Set<Owner> owners = ownerJpaService.findAll();

        assertThat(owners, is(returnOwnerSet));
    }

    @Test
    void findById() {
        when(ownerRepository.findById(ID)).thenReturn(Optional.of(defaultOwner));
        Owner returnedOwner = ownerJpaService.findById(ID);

        assertThat(returnedOwner, is(notNullValue()));
        assertThat(returnedOwner, is(defaultOwner));
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(ID)).thenReturn(Optional.empty());
        Owner returnedOwner = ownerJpaService.findById(ID);

        assertThat(returnedOwner, is(nullValue()));
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(2L).build();
        when(ownerRepository.save(any())).thenReturn(defaultOwner);

        Owner savedOwner = ownerJpaService.save(ownerToSave);

        assertThat(savedOwner, is(notNullValue()));
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerJpaService.delete(defaultOwner);

        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerJpaService.deleteById(ID);

        verify(ownerRepository).deleteById(anyLong());
    }
}