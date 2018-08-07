/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.controller;

import com.sg.supersightings.dao.DataPersistenceException;
import com.sg.supersightings.dao.EntityDao;
import com.sg.supersightings.model.Entity;
import com.sg.supersightings.model.EntityView;
import com.sg.supersightings.service.ServiceLayer;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
public class EntityController {

    @Inject
    ServiceLayer service;

    @GetMapping(value = "/entity/{id}")
    public Entity getEntity(@PathVariable("id") int id) {
        return service.getEntityById(id);
    }

//    @PostMapping(value = "/entity")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Entity createEntity(@Valid @RequestBody Entity entity) {
//        return service.addEntity(entity);
//    }
    @PostMapping(value = "/entity")
    @ResponseStatus(HttpStatus.CREATED)
    public Entity createEntity(@Valid @RequestBody EntityView entityView) {
        Entity entity = new Entity(entityView, service.getPowersById(entityView.getPowerIds()), service.getOrganizationsById(entityView.getOrganizationIds()));

        return service.addEntity(entity);
    }

    @DeleteMapping(value = "/entity/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntity(@PathVariable("id") int id) throws DataPersistenceException {
        service.deleteEntity(id);
    }

//    @PutMapping(value = "/entity/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public Entity updateEntity(@PathVariable("id") int id,
//            @Valid @RequestBody Entity entity) {//throws UpdateIntegrityException {
//
////        if (id != entity.getEntityId()) {
////            throw new UpdateIntegrityException("Entity Id on URL must match Entity Id in submitted data.");
////        }
//        return service.updateEntity(entity);
//    }
    @PutMapping(value = "/entity/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Entity updateEntity(@PathVariable("id") int id,
            @Valid @RequestBody EntityView entityView) {//throws UpdateIntegrityException {
        Entity entity = new Entity(entityView, service.getPowersById(entityView.getPowerIds()), service.getOrganizationsById(entityView.getOrganizationIds()));
        entity.setEntityId(entityView.getEntityId());
//        if (id != entity.getEntityId()) {
//            throw new UpdateIntegrityException("Entity Id on URL must match Entity Id in submitted data.");
//        }
        return service.updateEntity(entity);
    }

    @GetMapping(value = "/entities")
    public List<Entity> getAllEntities() {
        return service.getAllEntities();
    }

}
