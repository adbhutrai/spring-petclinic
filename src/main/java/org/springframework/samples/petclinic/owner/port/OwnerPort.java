package org.springframework.samples.petclinic.owner.port;

import java.util.Collection;

import org.springframework.samples.petclinic.owner.Owner;

public interface OwnerPort {

    public void createViewOrUpdateResponse();

    public void createSuccessResponse(String viewName);

    public void createNoOwnersFound();
    
    public void matchingOwners(Collection<Owner> results);
}
