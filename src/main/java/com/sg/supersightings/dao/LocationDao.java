/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Location;
import com.sg.supersightings.model.Sighting;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface LocationDao {

    Location addLocation(Location location);

    void deleteLocation(int locationId) throws DataPersistenceException;

    Location updateLocation(Location location);

    Location getLocationById(int locationId);

    List<Location> getLocationsByEntityId(int entityId);

    List<Location> getAllLocations();
    
    Location getLocationBySighting(Sighting sighting);
}
