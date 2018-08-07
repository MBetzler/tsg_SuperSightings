/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.controller;

import com.sg.supersightings.dao.DataPersistenceException;
import com.sg.supersightings.model.Organization;
import com.sg.supersightings.service.ServiceLayer;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author betzler
 */
@CrossOrigin
@RestController
public class OrganizationContoller {

    @Inject
    ServiceLayer service;

    @GetMapping(value = "/organization/{id}")
    public Organization getOrganization(@PathVariable("id") int id) {
        return service.getOrganizationById(id);
    }

    @PostMapping(value = "/organization")
    @ResponseStatus(HttpStatus.CREATED)
    public Organization createOrganization(@Valid @RequestBody Organization organization) {
        return service.addOrganization(organization);
    }

    @DeleteMapping(value = "/organization/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("id") int id) throws DataPersistenceException {
        service.deleteOrganization(id);
    }

    @PutMapping(value = "/organization/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Organization updateOrganization(@PathVariable("id") int id,
            @Valid @RequestBody Organization organization) {//throws UpdateIntegrityException {

//        if (id != organization.getOrganizationId()) {
//            throw new UpdateIntegrityException("Organization Id on URL must match Organization Id in submitted data.");
//        }
        return service.updateOrganization(organization);
    }

    @GetMapping(value = "/organizations")
    public List<Organization> getAllOrganizations() {
        return service.getAllOrganizations();
    }
}
