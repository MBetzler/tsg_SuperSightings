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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
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
public class EntityDaoImplTest {

//    @Inject
    EntityDao entityDao;
//    @Inject
    LocationDao locationDao;
//    @Inject
    OrganizationDao organizationDao;
//    @Inject
    PowerDao powerDao;
//    @Inject
    SightingDao sightingDao;

    public EntityDaoImplTest() {
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
     * Test of addEntity method, of class EntityDaoImpl.
     */
    @Test
    public void testAddGetEntity() {
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
     * Test of deleteEntity method, of class EntityDaoImpl.
     */
    @Test
    public void testDeleteEntity() {
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
        entityDao.deleteEntity(entity.getEntityId());
        assertNull(entityDao.getEntityById(entity.getEntityId()));
        assertEquals(0, powerDao.getPowersByEntityId(entity.getEntityId()).size());
        assertEquals(0, organizationDao.getOrganizationsByEntityId(entity.getEntityId()).size());
    }

    /**
     * Test of updateEntity method, of class EntityDaoImpl.
     */
    @Test
    public void testUpdateEntity() {
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

        entity.setPowers(powers);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        entity.setOrganizations(orgs);

        entityDao.addEntity(entity);

        powers.add(power2);
        orgs.add(org2);

        entity.setPowers(powers);
        entity.setOrganizations(orgs);

        entityDao.updateEntity(entity);

        Entity fromDao = entityDao.getEntityById(entity.getEntityId());
        assertEquals(fromDao, entity);
    }

    /**
     * Test of getEntityById method, of class EntityDaoImpl.
     */
    //@Test
    public void testGetEntityById() {
        //Tested by testAddGetEntity
    }

    /**
     * Test of getEntitiesByOrganizationId method, of class EntityDaoImpl.
     */
    @Test
    public void testGetEntitiesByOrganizationId() {
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

        Entity entity2 = new Entity();
        entity2.setEntityName("Superman's Nemesis");
        entity2.setEntityDescription("He's just super duper BAD.");
        entity2.setIsHero(false);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        entity.setPowers(powers);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        entity.setOrganizations(orgs);

        entityDao.addEntity(entity);

        List<Power> powers2 = new ArrayList<>();
        List<Organization> orgs2 = new ArrayList<>();
        orgs2.add(org);
        powers2.add(power);
        orgs2.add(org2);
        powers2.add(power2);

        entity2.setPowers(powers2);
        entity2.setOrganizations(orgs2);

        entityDao.addEntity(entity2);

        assertEquals(2, entityDao.getEntitiesByOrganizationId(org.getOrganizationId()).size());
        assertEquals(1, entityDao.getEntitiesByOrganizationId(org2.getOrganizationId()).size());
    }

    /**
     * Test of getEntitiesByPowerId method, of class EntityDaoImpl.
     */
    @Test
    public void testGetEntitiesByPowerId() {
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

        Entity entity2 = new Entity();
        entity2.setEntityName("Superman's Nemesis");
        entity2.setEntityDescription("He's just super duper BAD.");
        entity2.setIsHero(false);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        entity.setPowers(powers);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        entity.setOrganizations(orgs);

        entityDao.addEntity(entity);

        List<Power> powers2 = new ArrayList<>();
        List<Organization> orgs2 = new ArrayList<>();
        orgs2.add(org);
        powers2.add(power);
        orgs2.add(org2);
        powers2.add(power2);

        entity2.setPowers(powers2);
        entity2.setOrganizations(orgs2);

        entityDao.addEntity(entity2);

        assertEquals(2, entityDao.getEntitiesByPowerId(power.getPowerId()).size());
        assertEquals(1, entityDao.getEntitiesByPowerId(power2.getPowerId()).size());
    }

    /**
     * Test of getEntitiesBySightingId method, of class EntityDaoImpl.
     */
    @Test
    public void testGetEntitiesBySightingId() {
        //Same test as for testAddGetSighting in SightingDaoImplTest
        Power power = new Power();
        power.setPowerDescription("Add Sighting Entity's First Power");
        powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerDescription("Add Sighting Entity's Second Power");
        powerDao.addPower(power2);

        Organization org = new Organization();
        org.setOrganizationName("Add Sighting First Org");
        org.setOrganizationDescription("First Org Desc");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Add Sighting Second Org");
        org2.setOrganizationDescription("Second Org Desc");
        org2.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org2.setOrganizationEmail("notsogreat@villains.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        Entity entity = new Entity();
        entity.setEntityName("Add Sighting Superman");
        entity.setEntityDescription("He's just super duper.");
        entity.setIsHero(true);

        Entity entity2 = new Entity();
        entity2.setEntityName("Add Sighting Superman's Nemesis");
        entity2.setEntityDescription("He's just super duper BAD.");
        entity2.setIsHero(false);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        entity.setPowers(powers);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        entity.setOrganizations(orgs);

        entityDao.addEntity(entity);

        List<Power> powers2 = new ArrayList<>();
        List<Organization> orgs2 = new ArrayList<>();
        orgs2.add(org);
        powers2.add(power);
        orgs2.add(org2);
        powers2.add(power2);

        entity2.setPowers(powers2);
        entity2.setOrganizations(orgs2);

        entityDao.addEntity(entity2);

        Location loc = new Location();
        loc.setLocationName("Sighting Add Get Test");
        loc.setLocationDescription("It's the add get test.");
        loc.setLocationAddress("123 Big Building Ave, Minneapolis, MN 55412");
        loc.setLocationLatitude(new BigDecimal("22.456123"));
        loc.setLocationLongitude(new BigDecimal("111.123654"));

        locationDao.addLocation(loc);

        Sighting sig = new Sighting();
        sig.setLocation(loc);
        sig.setSightingDateTime(LocalDateTime.parse("2011-11-13T11:30:00", DateTimeFormatter.ISO_DATE_TIME));

        List<Entity> entities = new ArrayList<>();
        entities.add(entity);
        entities.add(entity2);

        sig.setEntities(entities);

        sightingDao.addSighting(sig);

        Sighting fromDao = sightingDao.getSightingById(sig.getSightingId());

        assertEquals(fromDao, sig);
    }

    /**
     * Test of getEntitiesByLocationId method, of class EntityDaoImpl.
     */
    @Test
    public void testGetEntitiesByLocationId() {
        Power power = new Power();
        power.setPowerDescription("Sighting By Entity's First Power");
        powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerDescription("Sighting By Entity's Second Power");
        powerDao.addPower(power2);

        Organization org = new Organization();
        org.setOrganizationName("Sighting By First Org");
        org.setOrganizationDescription("First Org Desc");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Sighting By Second Org");
        org2.setOrganizationDescription("Second Org Desc");
        org2.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org2.setOrganizationEmail("notsogreat@villains.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        Entity entity = new Entity();
        entity.setEntityName("Sighting By Superman");
        entity.setEntityDescription("He's just super duper.");
        entity.setIsHero(true);

        Entity entity2 = new Entity();
        entity2.setEntityName("Sighting By Superman's Nemesis");
        entity2.setEntityDescription("He's just super duper BAD.");
        entity2.setIsHero(false);

        Entity entity3 = new Entity();
        entity3.setEntityName("Sighting By Superman's Other Nemesis");
        entity3.setEntityDescription("He's just an other super duper BAD.");
        entity3.setIsHero(false);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        entity.setPowers(powers);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        entity.setOrganizations(orgs);

        entityDao.addEntity(entity);

        List<Power> powers2 = new ArrayList<>();
        List<Organization> orgs2 = new ArrayList<>();
        orgs2.add(org);
        powers2.add(power);
        orgs2.add(org2);
        powers2.add(power2);

        entity2.setPowers(powers2);
        entity2.setOrganizations(orgs2);

        entityDao.addEntity(entity2);

        List<Power> powers3 = new ArrayList<>();
        List<Organization> orgs3 = new ArrayList<>();
        orgs3.add(org);
        powers3.add(power);
        orgs3.add(org2);

        entity3.setPowers(powers3);
        entity3.setOrganizations(orgs3);

        entityDao.addEntity(entity3);

        Location loc1 = new Location();
        loc1.setLocationName("Location Sighting By 1");
        loc1.setLocationDescription("Describe Sighting By Location 1");
        loc1.setLocationAddress("111 Big Building Ave, Minneapolis, MN 55412");
        loc1.setLocationLatitude(new BigDecimal("11.456123"));
        loc1.setLocationLongitude(new BigDecimal("111.123654"));

        locationDao.addLocation(loc1);

        Location loc2 = new Location();
        loc2.setLocationName("Location Sighting By 2");
        loc2.setLocationDescription("Describe Sighting By Location 2");
        loc2.setLocationAddress("222 Big Building Ave, Minneapolis, MN 55412");
        loc2.setLocationLatitude(new BigDecimal("22.456123"));
        loc2.setLocationLongitude(new BigDecimal("222.123654"));

        locationDao.addLocation(loc2);

        List<Entity> entities1 = new ArrayList<>();
        entities1.add(entity);

        List<Entity> entities2 = new ArrayList<>();
        entities2.add(entity2);

        List<Entity> entities3 = new ArrayList<>();
        entities3.add(entity);
        entities3.add(entity2);
        entities3.add(entity3);

        Sighting sig1 = new Sighting();
        sig1.setLocation(loc1);
        sig1.setSightingDateTime(LocalDateTime.parse("2001-01-01T01:01:01", DateTimeFormatter.ISO_DATE_TIME));
        sig1.setEntities(entities1);

        sightingDao.addSighting(sig1);

        Sighting sig2 = new Sighting();
        sig2.setLocation(loc2);
        sig2.setSightingDateTime(LocalDateTime.parse("2002-02-02T02:02:02", DateTimeFormatter.ISO_DATE_TIME));
        sig2.setEntities(entities2);

        sightingDao.addSighting(sig2);

        Sighting sig3 = new Sighting();
        sig3.setLocation(loc2);
        sig3.setSightingDateTime(LocalDateTime.parse("2001-01-01T03:03:03", DateTimeFormatter.ISO_DATE_TIME));
        sig3.setEntities(entities3);

        sightingDao.addSighting(sig3);

        assertEquals(1, entityDao.getEntitiesByLocationId(loc1.getLocationId()).size());
        assertEquals(3, entityDao.getEntitiesByLocationId(loc2.getLocationId()).size());
    }

    /**
     * Test of getAllEntities method, of class EntityDaoImpl.
     */
    @Test
    public void testGetAllEntities() {
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

        Entity entity2 = new Entity();
        entity2.setEntityName("Superman's Nemesis");
        entity2.setEntityDescription("He's just super duper BAD.");
        entity2.setIsHero(false);

        List<Power> powers = new ArrayList<>();
        powers.add(power);

        entity.setPowers(powers);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(org);

        entity.setOrganizations(orgs);

        entityDao.addEntity(entity);

        List<Power> powers2 = new ArrayList<>();
        List<Organization> orgs2 = new ArrayList<>();
        orgs2.add(org);
        powers2.add(power);
        orgs2.add(org2);
        powers2.add(power2);

        entity2.setPowers(powers2);
        entity2.setOrganizations(orgs2);

        entityDao.addEntity(entity2);

        Entity fromDao = entityDao.getEntityById(entity.getEntityId());
        Entity fromDao2 = entityDao.getEntityById(entity2.getEntityId());

        assertEquals(2, entityDao.getAllEntities().size());
        assertEquals(entity, fromDao);
        assertEquals(entity2, fromDao2);
        assertEquals(1, entityDao.getEntityById(entity.getEntityId()).getPowers().size());
        assertEquals(2, entityDao.getEntityById(entity2.getEntityId()).getPowers().size());
        assertEquals(1, entityDao.getEntityById(entity.getEntityId()).getOrganizations().size());
        assertEquals(2, entityDao.getEntityById(entity2.getEntityId()).getOrganizations().size());
    }

}
