/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.controller;

import com.sg.supersightings.dao.DataPersistenceException;
import com.sg.supersightings.model.Location;
import com.sg.supersightings.service.ServiceLayer;
import java.util.ArrayList;
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
public class LocationController {

    @Inject
    ServiceLayer service;

    @GetMapping(value = "/location/{id}")
    public Location getLocation(@PathVariable("id") int id) {
        return service.getLocationById(id);
    }

    @PostMapping(value = "/location")
    @ResponseStatus(HttpStatus.CREATED)
    public Location createLocation(@Valid @RequestBody Location location) {
        return service.addLocation(location);
    }

    @DeleteMapping(value = "/location/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") int id) throws DataPersistenceException {
        service.deleteLocation(id);
    }

    //With UpdateIntegrityException
    @PutMapping(value = "/location/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Location updateLocation(@PathVariable("id") int id,
            @Valid @RequestBody Location location) throws UpdateIntegrityException {

        if (id != location.getLocationId()) {
            throw new UpdateIntegrityException("The Location ID on URL must match Location ID in submitted data.");
        }
        return service.updateLocation(location);
    }

    //Without UpdateIntegrityException
//    @PutMapping(value = "/location/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public Location updateLocation(@PathVariable("id") int id,
//            @Valid @RequestBody Location location) {
//
//        return service.updateLocation(location);
//    }
    @GetMapping(value = "/locations")
    public List<Location> getAllLocations() {
        return service.getAllLocations();
    }
}
