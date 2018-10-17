package org.springframework.samples.petclinic.owner.core.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.core.OwnerService;
import org.springframework.samples.petclinic.owner.port.OwnerPort;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public void createOwner(Owner owner, BindingResult result, OwnerPort uiPort) {
        if (result.hasErrors()) {
            uiPort.createViewOrUpdateResponse();
        } else {
            this.ownerRepository.save(owner);
            uiPort.createSuccessResponse(owner.getId()
                .toString());
        }
    }

    @Override
    public void findOwners(Owner owner, OwnerPort uiPort) {
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Owner> results = this.ownerRepository.findByLastName(owner.getLastName());
        if (results.isEmpty()) {
            // no owners found
           uiPort.createNoOwnersFound();
        } else {
            // multiple owners found
            uiPort.matchingOwners(results);
        }
    }

}
