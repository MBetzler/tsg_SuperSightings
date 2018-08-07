/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class OrganizationDaoImpl implements OrganizationDao {

    @Inject
    JdbcTemplate jdbcTemplate;
//    private JdbcTemplate jdbcTemplate;
//

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_ORGANIZATION
            = "INSERT INTO Organization "
            + "(OrgName, OrgDesc, OrgAddress, OrgEmail, OrgPhone) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_ORGANIZATION
            = "DELETE FROM Organization "
            + "WHERE OrgID = ?";

    private static final String SQL_UPDATE_ORGANIZATION
            = "UPDATE Organization SET "
            + "OrgName = ?, "
            + "OrgDesc = ?, "
            + "OrgAddress = ?, "
            + "OrgEmail = ?, "
            + "OrgPhone = ? "
            + "WHERE OrgID = ?";

    private static final String SQL_SELECT_ORGANIZATION
            = "SELECT * FROM Organization "
            + "WHERE OrgID = ?";

    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "SELECT * FROM Organization";

    //ORGANIZATIONS AND ENTITIES
    private static final String SQL_SELECT_ENTITY_ORGANIZATIONS
            = "SELECT o.* FROM Organization o "
            + "JOIN EntityOrganization a ON o.OrgID = a.OrgID "
            + "WHERE a.EntityID = ?";

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION, organization.getOrganizationName(), organization.getOrganizationDescription(),
                organization.getOrganizationAddress(), organization.getOrganizationEmail(), organization.getOrganizationPhone());

        organization.setOrganizationId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));

        return organization;
    }

    @Override
    public void deleteOrganization(int organizationId) throws DataPersistenceException {
        try {
            jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
        } catch (DataIntegrityViolationException e) {
            throw new DataPersistenceException(
                    "Organization associated with an entity(s) may not be deleted.", e);
        }
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION, organization.getOrganizationName(), organization.getOrganizationDescription(),
                organization.getOrganizationAddress(), organization.getOrganizationEmail(), organization.getOrganizationPhone(),
                organization.getOrganizationId());

        return organization;
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION, new OrganizationMapper(), organizationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getOrganizationsByEntityId(int entityId) {
        return jdbcTemplate.query(SQL_SELECT_ENTITY_ORGANIZATIONS, new OrganizationMapper(), entityId);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization org = new Organization();
            org.setOrganizationId(rs.getInt("OrgID"));
            org.setOrganizationName(rs.getString("OrgName"));
            org.setOrganizationDescription(rs.getString("OrgDesc"));
            org.setOrganizationAddress(rs.getString("OrgAddress"));
            org.setOrganizationEmail(rs.getString("OrgEmail"));
            org.setOrganizationPhone(rs.getString("OrgPhone"));
            return org;
        }
    }

    @Override
    public List<Organization> getOrganizationsById(List<Integer> organizationIds) {
        List<Organization> organizations = new ArrayList<>();
        Map<Integer, Organization> organizationMap
                = getAllOrganizations().stream()
                        .collect(Collectors
                                .toMap(Organization::getOrganizationId, Function.identity()));

        for (Integer powerId : organizationIds) {
            organizations.add(organizationMap.get(powerId));
        }
        return organizations;
    }
}
