package net.frey.sfgpetclinic.service;

import net.frey.sfgpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);

    Set<Owner> findAllByLastNameLike(String lastName);
}
