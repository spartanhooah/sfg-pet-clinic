package net.frey.sfgpetclinic.controller;

import net.frey.sfgpetclinic.model.Owner;
import net.frey.sfgpetclinic.model.Pet;
import net.frey.sfgpetclinic.model.PetType;
import net.frey.sfgpetclinic.service.OwnerService;
import net.frey.sfgpetclinic.service.PetService;
import net.frey.sfgpetclinic.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {
    @Mock
    private PetService petService;

    @Mock
    private OwnerService ownerService;

    @Mock
    private PetTypeService petTypeService;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;
    Set<PetType> petTypes;
    private Owner owner;
    private Pet pet;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).build();
        pet = Pet.builder().id(1L).build();

        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().name("Dog").build());
        petTypes.add(PetType.builder().name("Cat").build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(petController)
                .build();

        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdateForm"));
    }

    @Test
    void processCreationForm() throws Exception {
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void initUpdateForm() throws Exception {
        when(petService.findById(anyLong())).thenReturn(pet);

        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdateForm"));
    }

    @Test
    void processUpdateForm() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService).save(any());
    }
}