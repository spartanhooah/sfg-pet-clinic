package net.frey.sfgpetclinic.service.jpa;

import lombok.extern.slf4j.Slf4j;
import net.frey.sfgpetclinic.model.Owner;
import net.frey.sfgpetclinic.repository.OwnerRepository;
import net.frey.sfgpetclinic.service.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Profile("jpa")
public class OwnerJpaService implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerJpaService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public Set<Owner> findAllByLastNameContaining(String lastName) {
        System.out.println("Received last name " + lastName);
        return ownerRepository.findAllByLastNameContainingIgnoreCase(lastName.toLowerCase());
    }

    @Override
    public Set<Owner> findAll() {
        Set<Owner> owners = new HashSet<>();
        ownerRepository.findAll().forEach(owners::add);

        return owners;
    }

    @Override
    public Owner findById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }
}
