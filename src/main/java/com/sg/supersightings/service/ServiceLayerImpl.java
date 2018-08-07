/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.service;

import com.sg.supersightings.dao.DataPersistenceException;
import com.sg.supersightings.dao.EntityDao;
import com.sg.supersightings.dao.LocationDao;
import com.sg.supersightings.dao.OrganizationDao;
import com.sg.supersightings.dao.PowerDao;
import com.sg.supersightings.dao.SightingDao;
import com.sg.supersightings.model.Entity;
import com.sg.supersightings.model.Location;
import com.sg.supersightings.model.Organization;
import com.sg.supersightings.model.Power;
import com.sg.supersightings.model.Sighting;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

/**
 *
 * @author betzler
 */
@Component
public class ServiceLayerImpl implements ServiceLayer {

    @Inject
    EntityDao entityDao;
    @Inject
    LocationDao locationDao;
    @Inject
    OrganizationDao organizationDao;
    @Inject
    PowerDao powerDao;
    @Inject
    SightingDao sightingDao;

    //LOCATIONS
    @Override
    public Location addLocation(Location location) {
        return locationDao.addLocation(location);
    }

    @Override
    public void deleteLocation(int locationId) throws DataPersistenceException {
        locationDao.deleteLocation(locationId);
    }

    @Override
    public Location updateLocation(Location location) {
        return locationDao.updateLocation(location);
    }

    @Override
    public Location getLocationById(int locationId) {
        return locationDao.getLocationById(locationId);
    }

    @Override
    public List<Location> getLocationsByEntityId(int entityId) {
        return locationDao.getLocationsByEntityId(entityId);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location getLocationBySighting(Sighting sighting) {
        return locationDao.getLocationBySighting(sighting);
    }

    //ORGANIZATIONS
    @Override
    public Organization addOrganization(Organization organization) {
        return organizationDao.addOrganization(organization);
    }

    @Override
    public void deleteOrganization(int organizationId) throws DataPersistenceException {
        organizationDao.deleteOrganization(organizationId);
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        return organizationDao.updateOrganization(organization);
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        return organizationDao.getOrganizationById(organizationId);
    }

    @Override
    public List<Organization> getOrganizationsByEntityId(int entityId) {
        return organizationDao.getOrganizationsByEntityId(entityId);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationDao.getAllOrganizations();
    }

    @Override
    public Entity addEntity(Entity entity) {
        return entityDao.addEntity(entity);
    }

    //ENTITIES
    @Override
    public void deleteEntity(int entityId) {
        entityDao.deleteEntity(entityId);
    }

    @Override
    public Entity updateEntity(Entity entity) {
        return entityDao.updateEntity(entity);
    }

    @Override
    public Entity getEntityById(int entityId) {
        return entityDao.getEntityById(entityId);
    }

    @Override
    public List<Entity> getEntitiesByOrganizationId(int organizationId) {
        return entityDao.getEntitiesByOrganizationId(organizationId);
    }

    @Override
    public List<Entity> getEntitiesByPowerId(int powerId) {
        return entityDao.getEntitiesByPowerId(powerId);
    }

    @Override
    public List<Entity> getEntitiesBySightingId(int sightingId) {
        return entityDao.getEntitiesBySightingId(sightingId);
    }

    @Override
    public List<Entity> getEntitiesByLocationId(int locationId) {
        return entityDao.getEntitiesByLocationId(locationId);
    }

    @Override
    public List<Entity> getAllEntities() {
        return entityDao.getAllEntities();
    }

    @Override
    public List<Entity> getEntitiesById(List<Integer> entityIds) {
        return entityDao.getEntitiesById(entityIds);
    }

    @Override
    public List<Organization> getOrganizationsById(List<Integer> organizationIds) {
        return organizationDao.getOrganizationsById(organizationIds);
    }

    //POWERS
    @Override
    public Power addPower(Power power) {
        return powerDao.addPower(power);
    }

    @Override
    public void deletePower(int powerId) throws DataPersistenceException {
        powerDao.deletePower(powerId);
    }

    @Override
    public Power updatePower(Power power) {
        return powerDao.updatePower(power);
    }

    @Override
    public Power getPowerById(int powerId) {
        return powerDao.getPowerById(powerId);
    }

    @Override
    public List<Power> getPowersByEntityId(int entityId) {
        return powerDao.getPowersByEntityId(entityId);
    }

    @Override
    public List<Power> getAllPowers() {
        return powerDao.getAllPowers();
    }

    @Override
    public List<Power> getPowersById(List<Integer> powerIds) {
        return powerDao.getPowersById(powerIds);
    }

    //SIGHTINGS
    @Override
    public Sighting addSighting(Sighting sighting) {
        return sightingDao.addSighting(sighting);
    }

    @Override
    public void deleteSighting(int sightingId) {
        sightingDao.deleteSighting(sightingId);
    }

    @Override
    public Sighting updateSighting(Sighting sighting) {
        return sightingDao.updateSighting(sighting);
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        return sightingDao.getSightingById(sightingId);
    }

    @Override
    public List<Sighting> getSightingsByEntityId(int entityId) {
        return sightingDao.getSightingsByEntityId(entityId);
    }

    @Override
    public List<Sighting> getSightingsByLocationId(int locationId) {
        return sightingDao.getSightingsByLocationId(locationId);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        return sightingDao.getSightingsByDate(date);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }
}
