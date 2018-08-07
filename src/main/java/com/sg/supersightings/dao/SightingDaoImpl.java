/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Entity;
import com.sg.supersightings.model.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
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
public class SightingDaoImpl implements SightingDao {

    @Inject
    JdbcTemplate jdbcTemplate;
    @Inject
    LocationDao locationDao;
    @Inject
    EntityDao entityDao;
//    private JdbcTemplate jdbcTemplate;
//

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//
//    private LocationDao locationDao;
//    private EntityDao entityDao;
//
//    @Inject
//    public SightingDaoImpl(LocationDao locationDao, EntityDao entityDao) {
//        this.locationDao = locationDao;
//        this.entityDao = entityDao;
//    }

    //SIGHTINGS
    private static final String SQL_INSERT_SIGHTING
            = "INSERT INTO Sighting "
            + "(LocID, SightingDateTime) "
            + "VALUES (?, ?)";

    private static final String SQL_DELETE_SIGHTING
            = "DELETE FROM Sighting "
            + "WHERE SightingID = ?";

    private static final String SQL_UPDATE_SIGHTING
            = "UPDATE Sighting SET "
            + "LocID = ?, "
            + "SightingDateTime = ? "
            + "WHERE SightingID = ?";

    private static final String SQL_SELECT_SIGHTING
            = "SELECT * FROM Sighting "
            + "WHERE SightingID = ?";

    private static final String SQL_SELECT_ALL_SIGHTINGS
            = "SELECT * FROM Sighting";

    private static final String SQL_SELECT_SIGHTINGS_BY_DATE
            = "SELECT * FROM Sighting "
            + "WHERE Date(SightingDateTime) = ?";

    private static final String SQL_SELECT_SIGHTINGS_BY_LOCATION_ID
            = "SELECT * FROM Sighting "
            + "WHERE LocID = ?";

    //SIGHTING AND ENTITIES
    private static final String SQL_INSERT_SIGHTING_ENTITIES
            = "INSERT INTO SightingEntity "
            + "(SightingID, EntityID) "
            + "VALUES (?, ?)";

    private static final String SQL_DELETE_SIGHTING_ENTITIES
            = "DELETE FROM SightingEntity "
            + "WHERE SightingID = ?";

    private static final String SQL_SELECT_SIGHTINGS_BY_ENTITY_ID
            = "SELECT s.* FROM Sighting s "
            + "JOIN SightingEntity se ON s.SightingID = se.SightingID "
            + "WHERE se.EntityID = ?";

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING, sighting.getLocation().getLocationId(),
                java.sql.Timestamp.valueOf(sighting.getSightingDateTime()));

        sighting.setSightingId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        insertSightingEntities(sighting);

        return sighting;
    }

    @Override
    @Transactional
    public void deleteSighting(int sightingId) {
        jdbcTemplate.update(SQL_DELETE_SIGHTING_ENTITIES, sightingId);

        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingId);
    }

    @Override
    @Transactional
    public Sighting updateSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_UPDATE_SIGHTING, sighting.getLocation().getLocationId(),
                java.sql.Timestamp.valueOf(sighting.getSightingDateTime()), sighting.getSightingId());

        jdbcTemplate.update(SQL_DELETE_SIGHTING_ENTITIES, sighting.getSightingId());
        insertSightingEntities(sighting);

        return sighting;
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        try {
            Sighting sighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING, new SightingMapper(), sightingId);
            sighting.setLocation(locationDao.getLocationBySighting(sighting));
            sighting.setEntities(entityDao.getEntitiesBySightingId(sighting.getSightingId()));
            return sighting;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingsByEntityId(int entityId) {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_ENTITY_ID, new SightingMapper(), entityId);

        return associateLocationAndEntitiesWithSighting(sightingList);
    }

    @Override
    public List<Sighting> getSightingsByLocationId(int locationId) {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_LOCATION_ID, new SightingMapper(), locationId);

        return associateLocationAndEntitiesWithSighting(sightingList);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_DATE, new SightingMapper(), java.sql.Date.valueOf(date));

        return associateLocationAndEntitiesWithSighting(sightingList);
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightingList = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS, new SightingMapper());

        return associateLocationAndEntitiesWithSighting(sightingList);
    }

    private List<Sighting> associateLocationAndEntitiesWithSighting(List<Sighting> sightingList) {
        for (Sighting sig : sightingList) {
            sig.setLocation(locationDao.getLocationBySighting(sig));
            sig.setEntities(entityDao.getEntitiesBySightingId(sig.getSightingId()));
        }

        return sightingList;
    }

    private void insertSightingEntities(Sighting sighting) {
        final int sightingId = sighting.getSightingId();
        final List<Entity> entities = sighting.getEntities();

        for (Entity entity : entities) {
            jdbcTemplate.update(SQL_INSERT_SIGHTING_ENTITIES, sightingId, entity.getEntityId());
        }
    }

    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sig = new Sighting();
            sig.setSightingId(rs.getInt("SightingID"));
            sig.setSightingDateTime(rs.getTimestamp("SightingDateTime").toLocalDateTime());
            return sig;
        }
    }

}
