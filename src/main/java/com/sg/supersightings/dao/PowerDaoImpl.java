/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Power;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
public class PowerDaoImpl implements PowerDao {

    @Inject
    JdbcTemplate jdbcTemplate;
//    private JdbcTemplate jdbcTemplate;
//

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_POWER
            = "INSERT INTO Power "
            + "(PowerDesc) "
            + "VALUES (?)";

    private static final String SQL_DELETE_POWER
            = "DELETE FROM Power "
            + "WHERE PowerID = ?";

    private static final String SQL_UPDATE_POWER
            = "UPDATE Power SET "
            + "PowerDesc = ? "
            + "WHERE PowerID = ?";

    private static final String SQL_SELECT_POWER
            = "SELECT * FROM Power "
            + "WHERE PowerID = ?";

    private static final String SQL_SELECT_ALL_POWERS
            = "SELECT * FROM Power";

    //POWERS AND ENTITIES
    private static final String SQL_SELECT_ENTITY_POWERS
            = "SELECT p.* FROM Power p "
            + "JOIN EntityPower ep ON p.PowerID = ep.PowerID "
            + "WHERE ep.EntityID = ?";

    @Override
    public List<Power> getPowersByEntityId(int entityId) {
        return jdbcTemplate.query(SQL_SELECT_ENTITY_POWERS, new PowerMapper(), entityId);
    }

    @Override
    @Transactional
    public Power addPower(Power power) {
        jdbcTemplate.update(SQL_INSERT_POWER,
                power.getPowerDescription());

        power.setPowerId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return power;
    }

    @Override
    public void deletePower(int powerId) throws DataPersistenceException {
        try {
            jdbcTemplate.update(SQL_DELETE_POWER, powerId);
        } catch (DataIntegrityViolationException e) {
            throw new DataPersistenceException(
                    "Power associated with an entity(s) may not be deleted.", e);
        }
    }

    @Override
    public Power updatePower(Power power) {
        jdbcTemplate.update(SQL_UPDATE_POWER, power.getPowerDescription(), power.getPowerId());

        return power;
    }

    @Override
    public Power getPowerById(int powerId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_POWER, new PowerMapper(), powerId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        return jdbcTemplate.query(SQL_SELECT_ALL_POWERS, new PowerMapper());
    }

    private static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {
            Power power = new Power();
            power.setPowerId(rs.getInt("PowerID"));
            power.setPowerDescription(rs.getString("PowerDesc"));
            return power;
        }
    }

    @Override
    public List<Power> getPowersById(List<Integer> powerIds) {
        List<Power> powers = new ArrayList<>();
        Map<Integer, Power> powerMap
                = getAllPowers().stream()
                        .collect(Collectors
                                .toMap(Power::getPowerId, Function.identity()));

        for (Integer powerId : powerIds) {
            powers.add(powerMap.get(powerId));
        }
        return powers;
    }
}
