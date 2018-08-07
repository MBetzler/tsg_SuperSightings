/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.controller;

import com.sg.supersightings.dao.DataPersistenceException;
import com.sg.supersightings.model.Power;
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
public class PowerController {

    @Inject
    ServiceLayer service;

    @GetMapping(value = "/power/{id}")
    public Power getPower(@PathVariable("id") int id) {
        return service.getPowerById(id);
    }

    @PostMapping(value = "/power")
    @ResponseStatus(HttpStatus.CREATED)
    public Power createPower(@Valid @RequestBody Power power) {
        return service.addPower(power);
    }

    @DeleteMapping(value = "/power/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePower(@PathVariable("id") int id) throws DataPersistenceException {
        service.deletePower(id);
    }

    @PutMapping(value = "/power/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Power updatePower(@PathVariable("id") int id,
            @Valid @RequestBody Power power) {//throws UpdateIntegrityException {

//        if (id != location.getLocationId()) {
//            throw new UpdateIntegrityException("Location Id on URL must match Location Id in submitted data.");
//        }
        return service.updatePower(power);
    }

    @GetMapping(value = "/powers")
    public List<Power> getAllPowers() {
        return service.getAllPowers();
    }

}
