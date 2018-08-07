/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface SightingDao {

    Sighting addSighting(Sighting sighting);

    void deleteSighting(int sightingId);

    Sighting updateSighting(Sighting sighting);

    Sighting getSightingById(int sightingId);

    List<Sighting> getSightingsByEntityId(int entityId);

    List<Sighting> getSightingsByLocationId(int locationId);

    List<Sighting> getSightingsByDate(LocalDate date);

    List<Sighting> getAllSightings();

}
