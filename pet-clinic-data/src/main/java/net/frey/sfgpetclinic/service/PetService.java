package net.frey.sfgpetclinic.service;

import net.frey.sfgpetclinic.model.Pet;

import java.util.Set;

public interface PetService {
    Pet findById(Long id);

    Pet save(Pet owner);

    Set<Pet> findAll();
}
