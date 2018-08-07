/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Location;
import com.sg.supersightings.model.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author betzler
 */
@Component//change
public class LocationDaoImpl implements LocationDao {
@Inject
JdbcTemplate jdbcTemplate;
//    private JdbcTemplate jdbcTemplate;
//
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_LOCATION
            = "INSERT INTO Location "
            + "(LocName, LocDesc, LocAddress, LocLatitude, LocLongitude) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_LOCATION
            = "DELETE FROM Location "
            + "WHERE LocID = ?";

    private static final String SQL_UPDATE_LOCATION
            = "UPDATE Location SET "
            + "LocName = ?, "
            + "LocDesc = ?, "
            + "LocAddress = ?, "
            + "LocLatitude = ?, "
            + "LocLongitude = ? "
            + "WHERE LocID = ?";

    private static final String SQL_SELECT_LOCATION
            = "SELECT * FROM Location "
            + "WHERE LocID = ?";

    private static final String SQL_SELECT_ALL_LOCATIONS
            = "SELECT * FROM Location";

    //LOCATIONS AND ENTITIES
    private static final String SQL_SELECT_LOCATIONS_BY_ENTITY_ID
            = "SELECT DISTINCT l.* FROM Location l "
            + "JOIN Sighting s ON l.LocID = s.LocID "
            + "JOIN SightingEntity se ON s.SightingID = se.SightingID "
            + "WHERE se.EntityID = ?";

    //LOCATIONS AND SIGHTINGS
    private static final String SQL_SELECT_LOCATION_BY_SIGHTING_ID
            = "SELECT l.* FROM Location l "
            + "JOIN Sighting s ON s.LocID = l.LocID "
            + "WHERE s.SightingID = ?";

    @Override
    @Transactional
    public Location addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION, location.getLocationName(), location.getLocationDescription(), location.getLocationAddress(),
                location.getLocationLatitude(), location.getLocationLongitude());

        location.setLocationId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        
        return location;
    }

    @Override
    public void deleteLocation(int locationId) throws DataPersistenceException {
        try {
            jdbcTemplate.update(SQL_DELETE_LOCATION, locationId);
        } catch (DataIntegrityViolationException e) {
            throw new DataPersistenceException(
                    "A Location associated with a Sighting(s) may not be deleted.", e);
        }
    }

    @Override
    public Location updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION, location.getLocationName(), location.getLocationDescription(), location.getLocationAddress(),
                location.getLocationLatitude(), location.getLocationLongitude(), location.getLocationId());
        
        return location;
    }

    @Override
    public Location getLocationById(int locationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION, new LocationMapper(), locationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getLocationsByEntityId(int entityId) {
        return jdbcTemplate.query(SQL_SELECT_LOCATIONS_BY_ENTITY_ID, new LocationMapper(), entityId);
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public Location getLocationBySighting(Sighting sighting) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTING_ID, new LocationMapper(), sighting.getSightingId());
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location loc = new Location();
            loc.setLocationId(rs.getInt("LocID"));
            loc.setLocationName(rs.getString("LocName"));
            loc.setLocationDescription(rs.getString("LocDesc"));
            loc.setLocationAddress(rs.getString("LocAddress"));
            loc.setLocationLatitude(rs.getBigDecimal("LocLatitude"));
            loc.setLocationLongitude(rs.getBigDecimal("LocLongitude"));
            return loc;
        }
    }
}
