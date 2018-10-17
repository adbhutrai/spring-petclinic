package org.springframework.samples.petclinic.owner.core;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.port.OwnerPort;
import org.springframework.validation.BindingResult;

public interface OwnerService {

    public void createOwner(Owner owner, BindingResult result, OwnerPort uiPort);

    public void findOwners(Owner owner, OwnerPort uiPort);
}
