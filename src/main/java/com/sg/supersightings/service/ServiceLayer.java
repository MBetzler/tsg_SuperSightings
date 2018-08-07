/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.service;

import com.sg.supersightings.dao.DataPersistenceException;
import com.sg.supersightings.model.Entity;
import com.sg.supersightings.model.Location;
import com.sg.supersightings.model.Organization;
import com.sg.supersightings.model.Power;
import com.sg.supersightings.model.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface ServiceLayer {

    //LOCATIONS
    Location addLocation(Location location);

    void deleteLocation(int locationId) throws DataPersistenceException;

    Location updateLocation(Location location);

    Location getLocationById(int locationId);

    List<Location> getLocationsByEntityId(int entityId);

    List<Location> getAllLocations();

    Location getLocationBySighting(Sighting sighting);

    //ORGANIZATIONS
    Organization addOrganization(Organization organization);

    void deleteOrganization(int organizationId) throws DataPersistenceException;

    Organization updateOrganization(Organization organization);

    Organization getOrganizationById(int organizationId);

    List<Organization> getOrganizationsByEntityId(int entityId);

    List<Organization> getAllOrganizations();

    List<Organization> getOrganizationsById(List<Integer> organizationIds);

    //ENTITIES
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

    //POWERS
    Power addPower(Power power);

    void deletePower(int powerId) throws DataPersistenceException;

    Power updatePower(Power power);

    Power getPowerById(int powerId);

    List<Power> getPowersByEntityId(int entityId);

    List<Power> getAllPowers();

    List<Power> getPowersById(List<Integer> powerIds);

    //SIGHTINGS
    Sighting addSighting(Sighting sighting);

    void deleteSighting(int sightingId);

    Sighting updateSighting(Sighting sighting);

    Sighting getSightingById(int sightingId);

    List<Sighting> getSightingsByEntityId(int entityId);

    List<Sighting> getSightingsByLocationId(int locationId);

    List<Sighting> getSightingsByDate(LocalDate date);

    List<Sighting> getAllSightings();
}
