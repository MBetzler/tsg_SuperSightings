/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.supersightings.dao;

import com.sg.supersightings.model.Entity;
import com.sg.supersightings.model.Location;
import com.sg.supersightings.model.Organization;
import com.sg.supersightings.model.Power;
import com.sg.supersightings.model.Sighting;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author betzler
 */
public class PowerDaoImplTest {

    EntityDao entityDao;
    LocationDao locationDao;
    OrganizationDao organizationDao;
    PowerDao powerDao;
    SightingDao sightingDao;

    public PowerDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        entityDao = ctx.getBean("entityDao", EntityDao.class);
        locationDao = ctx.getBean("locationDao", LocationDao.class);
        organizationDao = ctx.getBean("organizationDao", OrganizationDao.class);
        powerDao = ctx.getBean("powerDao", PowerDao.class);
        sightingDao = ctx.getBean("sightingDao", SightingDao.class);

        //DELETE SIGHTINGS
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sig : sightings) {
            sightingDao.deleteSighting(sig.getSightingId());
        }
        //DELETE LOCATIONS
        List<Location> locations = locationDao.getAllLocations();
        for (Location loc : locations) {
            locationDao.deleteLocation(loc.getLocationId());
        }

        //DELETE ENTITIES
        List<Entity> entities = entityDao.getAllEntities();
        for (Entity ent : entities) {
            entityDao.deleteEntity(ent.getEntityId());
        }

        //DELETE POWERS
        List<Power> powers = powerDao.getAllPowers();
        for (Power pow : powers) {
            powerDao.deletePower(pow.getPowerId());
        }

        //DELETE ORGANIZATIONS
        List<Organization> orgs = organizationDao.getAllOrganizations();
        for (Organization org : orgs) {
            organizationDao.deleteOrganization(org.getOrganizationId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPowersByEntityId method, of class PowerDaoImpl.
     */
    @Test
    public void testGetPowersByEntityId() {
        //Same test as for testAddGetEntity in EntityDaoImplTest
        Power power = new Power();
        power.setPowerDescription("Entity's First Power");
        powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerDescription("Entity's Second Power");
        powerDao.addPower(power2);

        Organization org = new Organization();
        org.setOrganizationName("First Org");
        org.setOrganizationDescription("First Org Desc");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Second Org");
        org2.setOrganizationDescription("Second Org Desc");
        org2.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org2.setOrganizationEmail("notsogreat@villains.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        Entity entity = new Entity();
        entity.setEntityName("Superman");
        entity.setEntityDescription("He's just super duper.");
        entity.setIsHero(true);

        List<Power> powers = new ArrayList<>();
        powers.add(power);
        powers.add(power2);

        entity.setPowers(powers);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);
        orgs.add(org2);

        entity.setOrganizations(orgs);

        entityDao.addEntity(entity);

        Entity fromDao = entityDao.getEntityById(entity.getEntityId());
        assertEquals(fromDao, entity);
    }

    /**
     * Test of addPower method, of class PowerDaoImpl.
     */
    @Test
    public void testAddPower() {
        Power power = new Power();
        power.setPowerDescription("Really Bad BO");

        powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);
    }

    /**
     * Test of deletePower method, of class PowerDaoImpl.
     */
    @Test
    public void testDeletePower() throws Exception {
        Power power = new Power();
        power.setPowerDescription("Painfully Slow Flight");

        powerDao.addPower(power);

        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);
        powerDao.deletePower(power.getPowerId());
        assertNull(powerDao.getPowerById(power.getPowerId()));

        Power power1 = new Power();
        power1.setPowerDescription("Entity's First Power");
        powerDao.addPower(power1);

        Power power2 = new Power();
        power2.setPowerDescription("Entity's Second Power");
        powerDao.addPower(power2);

        Organization org = new Organization();
        org.setOrganizationName("First Org");
        org.setOrganizationDescription("First Org Desc");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Second Org");
        org2.setOrganizationDescription("Second Org Desc");
        org2.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org2.setOrganizationEmail("notsogreat@villains.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        Entity entity = new Entity();
        entity.setEntityName("Superman");
        entity.setEntityDescription("He's just super duper.");
        entity.setIsHero(true);

        List<Power> powers = new ArrayList<>();
        powers.add(power1);
        powers.add(power2);

        entity.setPowers(powers);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);
        orgs.add(org2);

        entity.setOrganizations(orgs);

        entityDao.addEntity(entity);

        try {
            powerDao.deletePower(power1.getPowerId());
            fail();
        } catch (DataPersistenceException e) {
        }
    }

    /**
     * Test of updatePower method, of class PowerDaoImpl.
     */
    @Test
    public void testUpdatePower() {
        Power power = new Power();
        power.setPowerDescription("Screeching Whine");

        powerDao.addPower(power);
        power.setPowerDescription("Pathetic Whine");
        powerDao.updatePower(power);
        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);
    }

    /**
     * Test of getPowerById method, of class PowerDaoImpl.
     */
    //@Test
    public void testGetPowerById() {
        //Tested by testAddGetPower
    }

    /**
     * Test of getAllPowers method, of class PowerDaoImpl.
     */
    @Test
    public void testGetAllPowers() {
        Power power = new Power();
        power.setPowerDescription("Annoying Whine");

        powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerDescription("Sketchy Flight");

        powerDao.addPower(power2);

        assertEquals(2, powerDao.getAllPowers().size());
    }

}
