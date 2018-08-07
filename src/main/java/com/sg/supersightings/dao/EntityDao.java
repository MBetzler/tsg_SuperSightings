/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Entity;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface EntityDao {

    Entity addEntity(Entity entity);

    void deleteEntity(int entityId);

    Entity updateEntity(Entity entity);

    Entity getEntityById(int entityId);

    List<Entity> getEntitiesByOrganizationId(int organizationId);

    List<Entity> getEntitiesByPowerId(int powerId);

    List<Entity> getEntitiesBySightingId(int sightingId);

    List<Entity> getEntitiesByLocationId(int locationId);

    List<Entity> getAllEntities();
    
    List<Entity> getEntitiesById(List<Integer> entityIds);
}
