/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.controller;

import com.sg.supersightings.dao.DataPersistenceException;
import com.sg.supersightings.model.Sighting;
import com.sg.supersightings.model.SightingView;
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
public class SightingController {

    @Inject
    ServiceLayer service;

    @GetMapping(value = "/sighting/{id}")
    public Sighting getSighting(@PathVariable("id") int id) {
        return service.getSightingById(id);
    }

//    @PostMapping(value = "/sighting")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Sighting createSighting(@Valid @RequestBody Sighting sighting) {
//        return service.addSighting(sighting);
//    }
    @PostMapping(value = "/sighting")
    @ResponseStatus(HttpStatus.CREATED)
    public Sighting createSighting(@Valid @RequestBody SightingView sightingView) {
        Sighting sighting = new Sighting(sightingView, service.getEntitiesById(sightingView.getEntityIds()), service.getLocationById(sightingView.getLocationId()));

        return service.addSighting(sighting);
    }

    @DeleteMapping(value = "/sighting/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSighting(@PathVariable("id") int id) throws DataPersistenceException {
        service.deleteSighting(id);
    }

//    @PutMapping(value = "/sighting/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public Sighting updateSighting(@PathVariable("id") int id,
//            @Valid @RequestBody Sighting sighting) {//throws UpdateIntegrityException {
//
////        if (id != location.getLocationId()) {
////            throw new UpdateIntegrityException("Location Id on URL must match Location Id in submitted data.");
////        }
//        return service.updateSighting(sighting);
//    }
    @PutMapping(value = "/sighting/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Sighting updateSighting(@PathVariable("id") int id,
            @Valid @RequestBody SightingView sightingView) {//throws UpdateIntegrityException {
        Sighting sighting = new Sighting(sightingView, service.getEntitiesById(sightingView.getEntityIds()), service.getLocationById(sightingView.getLocationId()));
        sighting.setSightingId(sightingView.getSightingId());
//        if (id != location.getLocationId()) {
//            throw new UpdateIntegrityException("Location Id on URL must match Location Id in submitted data.");
//        }
        return service.updateSighting(sighting);
    }

    @GetMapping(value = "/sightings")
    public List<Sighting> getAllSightings() {
        return service.getAllSightings();
    }

}
