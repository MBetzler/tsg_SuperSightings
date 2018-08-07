/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Power;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface PowerDao {

    Power addPower(Power power);

    void deletePower(int powerId) throws DataPersistenceException;

    Power updatePower(Power power);

    Power getPowerById(int powerId);

    List<Power> getPowersByEntityId(int entityId);

    List<Power> getAllPowers();
    
    List<Power> getPowersById(List<Integer> powerIds);
}
