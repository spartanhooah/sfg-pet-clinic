package net.frey.sfgpetclinic.service;

import net.frey.sfgpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
