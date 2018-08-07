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
public class OrganizationDaoImplTest {

    EntityDao entityDao;
    LocationDao locationDao;
    OrganizationDao organizationDao;
    PowerDao powerDao;
    SightingDao sightingDao;

    public OrganizationDaoImplTest() {
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
     * Test of addOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testAddGetOrganization() {
        Organization org = new Organization();
        org.setOrganizationName("Not So Super Heroes");
        org.setOrganizationDescription("Blundering Wanna Be's");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getOrganizationId());
        assertEquals(fromDao, org);
    }

    /**
     * Test of deleteOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testDeleteOrganization() throws Exception {
        Organization org = new Organization();
        org.setOrganizationName("Not So Super Villains");
        org.setOrganizationDescription("Evil Wanna Be's");
        org.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org.setOrganizationEmail("notsogreat@villains.com");
        org.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org);
        Organization fromDao = organizationDao.getOrganizationById(org.getOrganizationId());
        assertEquals(fromDao, org);
        organizationDao.deleteOrganization(org.getOrganizationId());
        assertNull(organizationDao.getOrganizationById(org.getOrganizationId()));

        Power power1 = new Power();
        power1.setPowerDescription("Entity's First Power");
        powerDao.addPower(power1);

        Power power2 = new Power();
        power2.setPowerDescription("Entity's Second Power");
        powerDao.addPower(power2);

        Organization org1 = new Organization();
        org1.setOrganizationName("First Org");
        org1.setOrganizationDescription("First Org Desc");
        org1.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org1.setOrganizationEmail("notsogreat@heroes.com");
        org1.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org1);

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
        orgs.add(org1);
        orgs.add(org2);

        entity.setOrganizations(orgs);

        entityDao.addEntity(entity);

        try {
            organizationDao.deleteOrganization(org2.getOrganizationId());
            fail();
        } catch (DataPersistenceException e) {
        }
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testUpdateOrganization() {
        Organization org = new Organization();
        org.setOrganizationName("Trying To Be Super Heroes");
        org.setOrganizationDescription("Under Achievers");
        org.setOrganizationAddress("444 Trying Ave, Minneapolis, MN 55411, USA");
        org.setOrganizationEmail("hapless@heroes.com");
        org.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org);

        org.setOrganizationDescription("Trying Harder");
        org.setOrganizationEmail("new@email.com");

        organizationDao.updateOrganization(org);
        Organization fromDao = organizationDao.getOrganizationById(org.getOrganizationId());
        assertEquals(fromDao, org);
    }

    /**
     * Test of getOrganizationById method, of class OrganizationDaoImpl.
     */
    //@Test
    public void testGetOrganizationById() {
        //Tested by testAddGetOrganization
    }

    /**
     * Test of getOrganizationsByEntityId method, of class OrganizationDaoImpl.
     */
    @Test
    public void testGetOrganizationsByEntityId() {
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
     * Test of getAllOrganizations method, of class OrganizationDaoImpl.
     */
    @Test
    public void testGetAllOrganizations() {
        Organization org = new Organization();
        org.setOrganizationName("Not So Super Heroes");
        org.setOrganizationDescription("Blundering Wanna Be's");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Trying To Be Super Heroes");
        org2.setOrganizationDescription("Under Achievers");
        org2.setOrganizationAddress("444 Trying Ave, Minneapolis, MN 55411, USA");
        org2.setOrganizationEmail("hapless@heroes.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        assertEquals(2, organizationDao.getAllOrganizations().size());
    }

}
