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
import java.time.LocalDate;
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
public class SightingDaoImplTest {

    EntityDao entityDao;
    LocationDao locationDao;
    OrganizationDao organizationDao;
    PowerDao powerDao;
    SightingDao sightingDao;

    public SightingDaoImplTest() {
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
     * Test of addSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testAddGetSighting() {
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
     * Test of deleteSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testDeleteSighting() {
        Power power = new Power();
        power.setPowerDescription("Add Delete Sighting Entity's First Power");
        powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerDescription("Add Delete Sighting Entity's Second Power");
        powerDao.addPower(power2);

        Organization org = new Organization();
        org.setOrganizationName("Add Delete Sighting First Org");
        org.setOrganizationDescription("First Org Desc");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Add Delete Sighting Second Org");
        org2.setOrganizationDescription("Second Org Desc");
        org2.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org2.setOrganizationEmail("notsogreat@villains.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        Entity entity = new Entity();
        entity.setEntityName("Add Delete Sighting Superman");
        entity.setEntityDescription("He's just super duper.");
        entity.setIsHero(true);

        Entity entity2 = new Entity();
        entity2.setEntityName("Add Delete Sighting Superman's Nemesis");
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

        Location loc1 = new Location();
        loc1.setLocationName("Location 1");
        loc1.setLocationDescription("Describe Delete Location 1");
        loc1.setLocationAddress("111 Big Building Ave, Minneapolis, MN 55412");
        loc1.setLocationLatitude(new BigDecimal("11.456123"));
        loc1.setLocationLongitude(new BigDecimal("111.123654"));

        locationDao.addLocation(loc1);

        Location loc2 = new Location();
        loc2.setLocationName("Location 2");
        loc2.setLocationDescription("Describe Delete Location 2");
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
        sig3.setSightingDateTime(LocalDateTime.parse("2003-03-03T03:03:03", DateTimeFormatter.ISO_DATE_TIME));
        sig3.setEntities(entities3);

        sightingDao.addSighting(sig3);

        assertEquals(3, sightingDao.getAllSightings().size());
        sightingDao.deleteSighting(sig1.getSightingId());
        assertEquals(2, sightingDao.getAllSightings().size());
        assertNull(sightingDao.getSightingById(sig1.getSightingId()));

        sightingDao.deleteSighting(sig2.getSightingId());
        assertEquals(1, sightingDao.getAllSightings().size());
        assertNull(sightingDao.getSightingById(sig2.getSightingId()));

        sightingDao.deleteSighting(sig3.getSightingId());
        assertEquals(0, sightingDao.getAllSightings().size());
        assertNull(sightingDao.getSightingById(sig3.getSightingId()));
    }

    /**
     * Test of updateSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testUpdateSighting() {
        Power power = new Power();
        power.setPowerDescription("Update Sighting Entity's First Power");
        powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerDescription("Update Sighting Entity's Second Power");
        powerDao.addPower(power2);

        Organization org = new Organization();
        org.setOrganizationName("Update Sighting First Org");
        org.setOrganizationDescription("First Org Desc");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Update Sighting Second Org");
        org2.setOrganizationDescription("Second Org Desc");
        org2.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org2.setOrganizationEmail("notsogreat@villains.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        Entity entity = new Entity();
        entity.setEntityName("Update Sighting Superman");
        entity.setEntityDescription("He's just super duper.");
        entity.setIsHero(true);

        Entity entity2 = new Entity();
        entity2.setEntityName("Update Sighting Superman's Nemesis");
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
        loc.setLocationName("Sighting Update Test");
        loc.setLocationDescription("It's the Update test.");
        loc.setLocationAddress("123 Big Building Ave, Minneapolis, MN 55412");
        loc.setLocationLatitude(new BigDecimal("22.456123"));
        loc.setLocationLongitude(new BigDecimal("111.123654"));

        locationDao.addLocation(loc);

        Sighting sig = new Sighting();
        sig.setLocation(loc);
        sig.setSightingDateTime(LocalDateTime.parse("2011-11-13T11:30:00", DateTimeFormatter.ISO_DATE_TIME));

        List<Entity> entities = new ArrayList<>();
        entities.add(entity);

        sig.setEntities(entities);

        sightingDao.addSighting(sig);

        entities.add(entity2);
        sig.setSightingDateTime(LocalDateTime.parse("2010-10-10T10:30:00", DateTimeFormatter.ISO_DATE_TIME));
        loc.setLocationName("Update Name Sighting Update Test");
        locationDao.updateLocation(loc);

        sightingDao.updateSighting(sig);

        Sighting fromDao = sightingDao.getSightingById(sig.getSightingId());

        assertEquals(fromDao, sig);

    }

    /**
     * Test of getSightingById method, of class SightingDaoImpl.
     */
    //@Test
    public void testGetSightingById() {
        //Tested by testAddGetSighting
    }

    /**
     * Test of getSightingsByEntityId method, of class SightingDaoImpl.
     */
    @Test
    public void testGetSightingsByEntityId() {
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

        assertEquals(1, sightingDao.getSightingsByEntityId(entity3.getEntityId()).size());
        assertEquals(2, sightingDao.getSightingsByEntityId(entity.getEntityId()).size());
        assertEquals(2, sightingDao.getSightingsByEntityId(entity2.getEntityId()).size());
    }

    /**
     * Test of getSightingsByLocationId method, of class SightingDaoImpl.
     */
    @Test
    public void testGetSightingsByLocationId() {
        Power power = new Power();
        power.setPowerDescription("Add Location Sighting Entity's First Power");
        powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerDescription("Add Location Sighting Entity's Second Power");
        powerDao.addPower(power2);

        Organization org = new Organization();
        org.setOrganizationName("Add Location Sighting First Org");
        org.setOrganizationDescription("First Org Desc");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Add Location Sighting Second Org");
        org2.setOrganizationDescription("Second Org Desc");
        org2.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org2.setOrganizationEmail("notsogreat@villains.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        Entity entity = new Entity();
        entity.setEntityName("Add Location Sighting Superman");
        entity.setEntityDescription("He's just super duper.");
        entity.setIsHero(true);

        Entity entity2 = new Entity();
        entity2.setEntityName("Add Location Sighting Superman's Nemesis");
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

        Location loc1 = new Location();
        loc1.setLocationName("Location Location 1");
        loc1.setLocationDescription("Describe Location 1");
        loc1.setLocationAddress("111 Big Building Ave, Minneapolis, MN 55412");
        loc1.setLocationLatitude(new BigDecimal("11.456123"));
        loc1.setLocationLongitude(new BigDecimal("111.123654"));

        locationDao.addLocation(loc1);

        Location loc2 = new Location();
        loc2.setLocationName("Location Location 2");
        loc2.setLocationDescription("Describe Location 2");
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
        sig3.setSightingDateTime(LocalDateTime.parse("2003-03-03T03:03:03", DateTimeFormatter.ISO_DATE_TIME));
        sig3.setEntities(entities3);

        sightingDao.addSighting(sig3);

        assertEquals(1, sightingDao.getSightingsByLocationId(loc1.getLocationId()).size());
        assertEquals(2, sightingDao.getSightingsByLocationId(loc2.getLocationId()).size());
    }

    /**
     * Test of getSightingsByDate method, of class SightingDaoImpl.
     */
    @Test
    public void testGetSightingsByDate() {
        Power power = new Power();
        power.setPowerDescription("Add Date Sighting Entity's First Power");
        powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerDescription("Add Date Sighting Entity's Second Power");
        powerDao.addPower(power2);

        Organization org = new Organization();
        org.setOrganizationName("Add Date Sighting First Org");
        org.setOrganizationDescription("First Org Desc");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Add Date Sighting Second Org");
        org2.setOrganizationDescription("Second Org Desc");
        org2.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org2.setOrganizationEmail("notsogreat@villains.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        Entity entity = new Entity();
        entity.setEntityName("Add Date Sighting Superman");
        entity.setEntityDescription("He's just super duper.");
        entity.setIsHero(true);

        Entity entity2 = new Entity();
        entity2.setEntityName("Add Date Sighting Superman's Nemesis");
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

        Location loc1 = new Location();
        loc1.setLocationName("Location Date 1");
        loc1.setLocationDescription("Describe Delete Location 1");
        loc1.setLocationAddress("111 Big Building Ave, Minneapolis, MN 55412");
        loc1.setLocationLatitude(new BigDecimal("11.456123"));
        loc1.setLocationLongitude(new BigDecimal("111.123654"));

        locationDao.addLocation(loc1);

        Location loc2 = new Location();
        loc2.setLocationName("Location Date 2");
        loc2.setLocationDescription("Describe Delete Location 2");
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

        assertEquals(1, sightingDao.getSightingsByDate(LocalDate.parse("2002-02-02", DateTimeFormatter.ISO_DATE)).size());
        assertEquals(2, sightingDao.getSightingsByDate(LocalDate.parse("2001-01-01", DateTimeFormatter.ISO_DATE)).size());
    }

    /**
     * Test of getAllSightings method, of class SightingDaoImpl.
     */
    @Test
    public void testGetAllSightings() {
        Power power = new Power();
        power.setPowerDescription("Add GetAll Sighting Entity's First Power");
        powerDao.addPower(power);

        Power power2 = new Power();
        power2.setPowerDescription("Add GetAll Sighting Entity's Second Power");
        powerDao.addPower(power2);

        Organization org = new Organization();
        org.setOrganizationName("Add GetAll Sighting First Org");
        org.setOrganizationDescription("First Org Desc");
        org.setOrganizationAddress("123 Fake It Ave, Minneapolis, MN 55419, USA");
        org.setOrganizationEmail("notsogreat@heroes.com");
        org.setOrganizationPhone("(612) 423-7516");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setOrganizationName("Add GetAll Sighting Second Org");
        org2.setOrganizationDescription("Second Org Desc");
        org2.setOrganizationAddress("321 Evil(ish) Ave, Minneapolis, MN 55401, USA");
        org2.setOrganizationEmail("notsogreat@villains.com");
        org2.setOrganizationPhone("(651) 423-7516");

        organizationDao.addOrganization(org2);

        Entity entity = new Entity();
        entity.setEntityName("Add GetAll Sighting Superman");
        entity.setEntityDescription("He's just super duper.");
        entity.setIsHero(true);

        Entity entity2 = new Entity();
        entity2.setEntityName("Add GetAll Sighting Superman's Nemesis");
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

        Location loc1 = new Location();
        loc1.setLocationName("Location GetAll 1");
        loc1.setLocationDescription("Describe Location 1");
        loc1.setLocationAddress("111 Big Building Ave, Minneapolis, MN 55412");
        loc1.setLocationLatitude(new BigDecimal("11.456123"));
        loc1.setLocationLongitude(new BigDecimal("111.123654"));

        locationDao.addLocation(loc1);

        Location loc2 = new Location();
        loc2.setLocationName("Location GetAll 2");
        loc2.setLocationDescription("Describe Location 2");
        loc2.setLocationAddress("222 Big Building Ave, Minneapolis, MN 55412");
        loc2.setLocationLatitude(new BigDecimal("22.456123"));
        loc2.setLocationLongitude(new BigDecimal("222.123654"));

        locationDao.addLocation(loc2);

        Location loc3 = new Location();
        loc3.setLocationName("Location 3");
        loc3.setLocationDescription("Describe Location 3");
        loc3.setLocationAddress("333 Big Building Ave, Minneapolis, MN 55412");
        loc3.setLocationLatitude(new BigDecimal("33.456123"));
        loc3.setLocationLongitude(new BigDecimal("333.123654"));

        locationDao.addLocation(loc3);

        List<Entity> entities1 = new ArrayList<>();
        entities1.add(entity);

        List<Entity> entities2 = new ArrayList<>();
        entities2.add(entity2);

        List<Entity> entities3 = new ArrayList<>();
        entities3.add(entity);
        entities3.add(entity2);

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
        sig3.setLocation(loc3);
        sig3.setSightingDateTime(LocalDateTime.parse("2003-03-03T03:03:03", DateTimeFormatter.ISO_DATE_TIME));
        sig3.setEntities(entities3);

        sightingDao.addSighting(sig3);

        Sighting fromDao1 = sightingDao.getSightingById(sig1.getSightingId());
        Sighting fromDao2 = sightingDao.getSightingById(sig2.getSightingId());
        Sighting fromDao3 = sightingDao.getSightingById(sig3.getSightingId());

        assertEquals(3, sightingDao.getAllSightings().size());
        assertEquals(sig1, fromDao1);
        assertEquals(sig2, fromDao2);
        assertEquals(sig3, fromDao3);
    }

}
