package org.springframework.samples.petclinic.owner.port.adaptor;

import java.util.Collection;
import java.util.Map;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.port.OwnerPort;
import org.springframework.validation.BindingResult;

public class OwnerUIAdptor implements OwnerPort {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private String viewName;

    private BindingResult result;

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    private Map<String, Object> model;

    public OwnerUIAdptor(BindingResult result, Map<String, Object> model) {
        super();
        this.result = result;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public void createViewOrUpdateResponse() {
        setViewName(VIEWS_OWNER_CREATE_OR_UPDATE_FORM);

    }

    @Override
    public void createSuccessResponse(String ownerId) {
        setViewName("redirect:/owners/" + ownerId);
    }

    @Override
    public void createNoOwnersFound() {
        result.rejectValue("lastName", "notFound", "not found");
        setViewName("owners/findOwners");
    }

    @Override
    public void matchingOwners(Collection<Owner> results) {
        model.put("selections", results);
        setViewName("owners/ownersList");
    }

}
