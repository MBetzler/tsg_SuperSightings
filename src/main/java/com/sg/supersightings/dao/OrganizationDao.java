/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Organization;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface OrganizationDao {

    Organization addOrganization(Organization organization);

    void deleteOrganization(int organizationId) throws DataPersistenceException;

    Organization updateOrganization(Organization organization);

    Organization getOrganizationById(int organizationId);

    List<Organization> getOrganizationsByEntityId(int entityId);

    List<Organization> getAllOrganizations();
    
    List<Organization> getOrganizationsById(List<Integer> organizationIds);

}
