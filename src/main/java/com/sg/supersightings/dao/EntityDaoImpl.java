/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Entity;
import com.sg.supersightings.model.Organization;
import com.sg.supersightings.model.Power;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
public class EntityDaoImpl implements EntityDao {

    @Inject
    JdbcTemplate jdbcTemplate;
    @Inject
    PowerDao powerDao;
    @Inject
    OrganizationDao organizationDao;
//    private JdbcTemplate jdbcTemplate;
//

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//
//    private PowerDao powerDao;
//    private OrganizationDao organizationDao;
//
//    @Inject
//    public EntityDaoImpl(PowerDao powerDao, OrganizationDao organizationDao) {
//        this.powerDao = powerDao;
//        this.organizationDao = organizationDao;
//    }

    //ENTITIES
    private static final String SQL_INSERT_ENTITY
            = "INSERT INTO Entity "
            + "(EntityName, EntityDesc, IsHero) "
            + "VALUES (?, ?, ?)";

    private static final String SQL_DELETE_ENTITY
            = "DELETE FROM Entity "
            + "WHERE EntityID = ?";

    private static final String SQL_UPDATE_ENTITY
            = "UPDATE Entity "
            + "SET EntityName = ?, "
            + "EntityDesc = ?, "
            + "IsHero = ? "
            + "WHERE EntityID = ?";

    private static final String SQL_SELECT_ENTITY
            = "SELECT * FROM Entity "
            + "WHERE EntityID = ?";

    private static final String SQL_SELECT_ALL_ENTITIES
            = "SELECT * FROM Entity";

    //ENTITIES AND POWERS
    private static final String SQL_DELETE_ENTITY_POWERS
            = "DELETE FROM EntityPower "
            + "WHERE EntityID = ?";

    private static final String SQL_INSERT_ENTITY_POWERS
            = "INSERT INTO EntityPower "
            + "(EntityID, PowerID) "
            + "VALUES (?, ?)";

    private static final String SQL_SELECT_ENTITIES_BY_POWER_ID
            = "SELECT e.* FROM Entity e "
            + "JOIN EntityPower ep ON e.EntityID = ep.EntityID "
            + "WHERE ep.PowerID = ?";

    //ENTITIES AND ORGANIZATIONS
    private static final String SQL_DELETE_ENTITY_ORGANIZATIONS
            = "DELETE FROM EntityOrganization "
            + "WHERE EntityID = ?";

    private static final String SQL_INSERT_ENTITY_ORGANIZATIONS
            = "INSERT INTO EntityOrganization "
            + "(EntityID, OrgID) "
            + "VALUES (?, ?)";

    private static final String SQL_SELECT_ENTITIES_BY_ORGANIZATION_ID
            = "SELECT e.* FROM Entity e "
            + "JOIN EntityOrganization eo ON e.EntityID = eo.EntityID "
            + "WHERE eo.OrgID = ?";

    //LOCATIONS AND ENTITIES
    private static final String SQL_SELECT_ENTITIES_BY_LOCATION_ID
            = "SELECT DISTINCT e.* FROM Entity e "
            + "JOIN SightingEntity se ON e.EntityID = se.EntityID "
            + "JOIN Sighting s ON se.SightingID = s.SightingID "
            + "WHERE s.LocID = ?";

    //SIGHTING AND ENTITIES
    private static final String SQL_SELECT_ENTITIES_BY_SIGHTING_ID
            = "SELECT e.* FROM Entity e "
            + "JOIN SightingEntity se ON e.EntityID = se.EntityID "
            + "WHERE se.SightingID = ?";

    @Override
    @Transactional
    public Entity addEntity(Entity entity) {
        jdbcTemplate.update(SQL_INSERT_ENTITY,
                entity.getEntityName(),
                entity.getEntityDescription(),
                entity.isIsHero());

        entity.setEntityId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        insertEntityPowers(entity);
        insertEntityOrganizations(entity);

        return entity;
    }

    @Override
    @Transactional
    public void deleteEntity(int entityId) {
        jdbcTemplate.update(SQL_DELETE_ENTITY_POWERS, entityId);

        jdbcTemplate.update(SQL_DELETE_ENTITY_ORGANIZATIONS, entityId);

        jdbcTemplate.update(SQL_DELETE_ENTITY, entityId);
    }

    @Override
    @Transactional
    public Entity updateEntity(Entity entity) {
        jdbcTemplate.update(SQL_UPDATE_ENTITY,
                entity.getEntityName(),
                entity.getEntityDescription(),
                entity.isIsHero(),
                entity.getEntityId());

        jdbcTemplate.update(SQL_DELETE_ENTITY_POWERS, entity.getEntityId());
        insertEntityPowers(entity);

        jdbcTemplate.update(SQL_DELETE_ENTITY_ORGANIZATIONS, entity.getEntityId());
        insertEntityOrganizations(entity);

        return entity;
    }

    @Override
    public Entity getEntityById(int entityId) {
        try {
            Entity entity = jdbcTemplate.queryForObject(SQL_SELECT_ENTITY, new EntityMapper(), entityId);
            entity.setPowers(powerDao.getPowersByEntityId(entity.getEntityId()));
            entity.setOrganizations(organizationDao.getOrganizationsByEntityId(entity.getEntityId()));
            return entity;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Entity> getEntitiesByOrganizationId(int organizationId) {
        List<Entity> entityList = jdbcTemplate.query(SQL_SELECT_ENTITIES_BY_ORGANIZATION_ID, new EntityMapper(), organizationId);

        return associatePowersAndOrganizationsWithEntity(entityList);
    }

    @Override
    public List<Entity> getEntitiesByPowerId(int powerId) {
        List<Entity> entityList = jdbcTemplate.query(SQL_SELECT_ENTITIES_BY_POWER_ID, new EntityMapper(), powerId);

        return associatePowersAndOrganizationsWithEntity(entityList);
    }

    @Override
    public List<Entity> getEntitiesBySightingId(int sightingId) {
        List<Entity> entityList = jdbcTemplate.query(SQL_SELECT_ENTITIES_BY_SIGHTING_ID, new EntityMapper(), sightingId);

        return associatePowersAndOrganizationsWithEntity(entityList);
    }

    @Override
    public List<Entity> getEntitiesByLocationId(int locationId) {
        List<Entity> entityList = jdbcTemplate.query(SQL_SELECT_ENTITIES_BY_LOCATION_ID, new EntityMapper(), locationId);

        return associatePowersAndOrganizationsWithEntity(entityList);
    }

    @Override
    public List<Entity> getAllEntities() {
        List<Entity> entityList = jdbcTemplate.query(SQL_SELECT_ALL_ENTITIES, new EntityMapper());

        return associatePowersAndOrganizationsWithEntity(entityList);
    }

    private List<Entity> associatePowersAndOrganizationsWithEntity(List<Entity> entityList) {
        for (Entity entity : entityList) {
            entity.setPowers(powerDao.getPowersByEntityId(entity.getEntityId()));
            entity.setOrganizations(organizationDao.getOrganizationsByEntityId(entity.getEntityId()));
        }

        return entityList;
    }

    private void insertEntityPowers(Entity entity) {
        final int entityId = entity.getEntityId();
        final List<Power> powers = entity.getPowers();

        for (Power power : powers) {
            jdbcTemplate.update(SQL_INSERT_ENTITY_POWERS, entityId, power.getPowerId());
        }
    }

    private void insertEntityOrganizations(Entity entity) {
        final int entityId = entity.getEntityId();
        final List<Organization> orgs = entity.getOrganizations();

        for (Organization org : orgs) {
            jdbcTemplate.update(SQL_INSERT_ENTITY_ORGANIZATIONS, entityId, org.getOrganizationId());
        }
    }

    private static final class EntityMapper implements RowMapper<Entity> {

        @Override
        public Entity mapRow(ResultSet rs, int i) throws SQLException {
            Entity ent = new Entity();
            ent.setEntityId(rs.getInt("EntityId"));
            ent.setEntityName(rs.getString("EntityName"));
            ent.setEntityDescription(rs.getString("EntityDesc"));
            ent.setIsHero(rs.getBoolean("IsHero"));
            return ent;
        }
    }

    @Override
    public List<Entity> getEntitiesById(List<Integer> entityIds) {
        List<Entity> entities = new ArrayList<>();
        Map<Integer, Entity> entityMap
                = getAllEntities().stream()
                        .collect(Collectors
                                .toMap(Entity::getEntityId, Function.identity()));

        for (Integer entityId : entityIds) {
            entities.add(entityMap.get(entityId));
        }

        return entities;
    }

}
