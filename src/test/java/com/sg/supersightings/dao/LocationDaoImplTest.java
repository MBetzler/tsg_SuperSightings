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
public class LocationDaoImplTest {

    EntityDao entityDao;
    LocationDao locationDao;
    OrganizationDao organizationDao;
    PowerDao powerDao;
    SightingDao sightingDao;

    public LocationDaoImplTest() {
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
     * Test of addLocation method, of class LocationDaoImpl.
     */
    @Test
    public void testAddGetLocation() {
        Location loc = new Location();
        loc.setLocationName("Big Building");
        loc.setLocationDescription("It's a big building.");
        loc.setLocationAddress("123 Big Building Ave, Minneapolis, MN 55412");
        loc.setLocationLatitude(new BigDecimal("23.123456"));
        loc.setLocationLongitude(new BigDecimal("321.654321"));

        locationDao.addLocation(loc);

        Location fromDao = locationDao.getLocationById(loc.getLocationId());
        assertEquals(fromDao, loc);
    }

    /**
     * Test of deleteLocation method, of class LocationDaoImpl.
     */
    @Test
    public void testDeleteLocation() throws Exception {
        Location loc = new Location();
        loc.setLocationName("Bigger Building");
        loc.setLocationDescription("It's a big, BIG building.");
        loc.setLocationAddress("987 Bigger Building Ave, Minneapolis, MN 55412");
        loc.setLocationLatitude(new BigDecimal("10.987654"));
        loc.setLocationLongitude(new BigDecimal("202.765432"));

        locationDao.addLocation(loc);

        Location fromDao = locationDao.getLocationById(loc.getLocationId());
        assertEquals(fromDao, loc);
        locationDao.deleteLocation(loc.getLocationId());
        assertNull(locationDao.getLocationById(loc.getLocationId()));

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

        Location loc2 = new Location();
        loc2.setLocationName("Sighting Add Get Test");
        loc2.setLocationDescription("It's the add get test.");
        loc2.setLocationAddress("123 Big Building Ave, Minneapolis, MN 55412");
        loc2.setLocationLatitude(new BigDecimal("22.456123"));
        loc2.setLocationLongitude(new BigDecimal("111.123654"));

        locationDao.addLocation(loc2);

        Sighting sig = new Sighting();
        sig.setLocation(loc2);
        sig.setSightingDateTime(LocalDateTime.parse("2011-11-13T11:30:00", DateTimeFormatter.ISO_DATE_TIME));

        List<Entity> entities = new ArrayList<>();
        entities.add(entity);
        entities.add(entity2);

        sig.setEntities(entities);

        sightingDao.addSighting(sig);

        try {
            locationDao.deleteLocation(loc2.getLocationId());
            fail();
        } catch (DataPersistenceException e) {
        }
    }

    /**
     * Test of updateLocation method, of class LocationDaoImpl.
     */
    @Test
    public void testUpdateLocation() {
        Location loc = new Location();
        loc.setLocationName("Bigger Building");
        loc.setLocationDescription("It's a big, BIG building.");
        loc.setLocationAddress("987 Bigger Building Ave, Minneapolis, MN 55412");
        loc.setLocationLatitude(new BigDecimal("10.987654"));
        loc.setLocationLongitude(new BigDecimal("202.765432"));

        locationDao.addLocation(loc);

        loc.setLocationDescription("Changed The Description");
        loc.setLocationName("New New Location Name");

        locationDao.updateLocation(loc);

        Location fromDao = locationDao.getLocationById(loc.getLocationId());
        assertEquals(fromDao, loc);
    }

    /**
     * Test of getLocationById method, of class LocationDaoImpl.
     */
    //@Test
    public void testGetLocationById() {
        //Tested by testAddGetLocation
    }

    /**
     * Test of getLocationsByEntityId method, of class LocationDaoImpl.
     */
    @Test
    public void testGetLocationsByEntityId() {
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

        assertEquals(2, locationDao.getLocationsByEntityId(entity.getEntityId()).size());
        assertEquals(1, locationDao.getLocationsByEntityId(entity2.getEntityId()).size());
        assertEquals(1, locationDao.getLocationsByEntityId(entity3.getEntityId()).size());
    }

    /**
     * Test of getAllLocations method, of class LocationDaoImpl.
     */
    @Test
    public void testGetAllLocations() {
        Location loc = new Location();
        loc.setLocationName("Big Building");
        loc.setLocationDescription("It's a big building.");
        loc.setLocationAddress("123 Big Building Ave, Minneapolis, MN 55412");
        loc.setLocationLatitude(new BigDecimal("23.123456"));
        loc.setLocationLongitude(new BigDecimal("321.654321"));

        locationDao.addLocation(loc);

        Location loc2 = new Location();
        loc2.setLocationName("Big Building");
        loc2.setLocationDescription("It's a big building.");
        loc2.setLocationAddress("123 Big Building Ave, Minneapolis, MN 55412");
        loc2.setLocationLatitude(new BigDecimal("12.123456"));
        loc2.setLocationLongitude(new BigDecimal("321.654321"));

        locationDao.addLocation(loc2);

        assertEquals(2, locationDao.getAllLocations().size());
    }

    /**
     * Test of getLocationBySighting method, of class LocationDaoImpl.
     */
    @Test
    public void testGetLocationBySighting() {
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

}
