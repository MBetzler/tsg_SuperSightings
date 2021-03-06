use supersightings;

INSERT INTO `Power`
(PowerDesc)
 VALUES 
 ('Flight'),
 ('Martial Arts'),
 ('Eidetic Memory'),
 ('Superhuman Speed'),
 ('Superhuman Strength'),
 ('Healing Factor'),
 ('Regeneration'),
 ('Precognition'),
 ('Hand-To-Hand Combat'),
 ('Energy Blast'),
 ('Compiance Professional'),
 ('Weather Modification'),
 ('Atmokinesis'),
 ('Shapeshifting'),
 ('Reality Warping'),
 ('Extrasensory Perception'),
 ('Psychokinesis'),
 ('Telepathy'),
 ('Empathy'),
 ('Teleportation'),
 ('Night Vision'),
 ('Magnetism'),
 ('Interkinetic Power'),
 ('Bio-Kinetic Energy'),
 ('Magic'),
 ('Technopathy'),
 ('Power description fifty characters long for check.');

INSERT INTO Entity
(EntityName,EntityDesc,IsHero)
VALUES
('Batman','Dark Knight...Caped Crusader...World''s Greatest Detective.',true),
('Superman','The most powerful being on planet Earth.',true),
('Wolverine','A mutant who possesses animal-keen senses, enhanced physical capabilities, powerful regenerative ability known as a healing factor, and three retractable claws in each hand.',true),
('Spider-Man','Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider.',true),
('Captain America','Vowing to serve his country any way he could, young Steve Rogers took the super soldier serum to become America''s one-man army.',true),
('Iron Man','Wounded, captured and forced to build a weapon by his enemies, billionaire industrialist Tony Stark instead created an advanced suit of armor to save his life and escape captivity.',true),
('Storm','Ororo Monroe is the descendant of an ancient line of African priestesses, all of whom have white hair, blue eyes, and the potential to wield magic.',true),
('Beast','As a founding member of the X-Men, intelligently gifted Hank McCoy has been fighting for the peaceful coexistence of mutants and humans most of his life.',true),
('Thor','As the Norse God of thunder and lightning, Thor wields one of the greatest weapons ever made, the enchanted hammer Mjolnir.',true),
('Hulk','Caught in a gamma bomb explosion while trying to save the life of a teenager, Dr. Bruce Banner was transformed into the incredibly powerful creature called the Hulk. ',true),
('Professor X','A mutant who possesses vast psionic powers, making him arguably the world''s most powerful telepath.',true),
('Jean Grey','The younger daughter of history professor John Grey and his wife Elaine, Jean Grey was 10 years old when her mutant telepathic powers first manifested after experiencing the emotions of a dying friend. ',true),
('Nightcrawler','Born in Bavaria to the blue-skinned shapeshifting mutant later known as Mystique, Kurt Wagner was shunned at birth due to his having blue skin, pointed ears, fangs, and a long pointed tail.',true),
('Magneto','The man that would become known as "Magneto" was born Max Eisenhardt in Germany during the 1920''s to a middle class Jewish family.',false),
('Gambit','Abandoned at birth due to his burning red eyes, the child who would one day become Remy LeBeau was kidnapped from his hospital ward by members of the New Orleans Thieves'' Guild.',true),
('Doctor Strange','Doctor Strange is one of the most powerful sorcerers in existence.',true),
('Doctor Doom','A super-genius, a powerful sorcerer and technologically advanced ruler of the country of Latveria, who seeks to expand his rule over the Earth as well as the universe.',false),
('Sabretooth','A mutant soldier and commando for Weapon X.',false),
('Mystique','A mutant spy and shapechanger, capable of impersonating anyone.',false),
('Entity name fifty characters long for display test','Entity description two hundred and fifty-five characters long for display testing purposes and so on and so on......and so on.....and so on.....etc. desc desc desc asd;lk l lasdfjh ;lkjd . lkajsd;lf ;lkj ;alajeljhf lkj;lafj;e;lkj ;lkj;lkj ;lkj; lkjkljjkj.',true);

INSERT INTO EntityPower
(EntityID,PowerID)
VALUES
(1,1),
(1,2),
(1,3),
(2,1),
(2,6),
(2,3),
(2,4),
(2,5),
(3,6),
(3,7),
(4,8),
(4,6),
(4,4),
(4,9),
(4,5),
(5,2),
(5,9),
(6,1),
(6,9),
(6,5),
(6,10),
(6,11),
(7,1),
(7,12),
(7,13),
(8,6),
(8,4),
(9,1),
(9,5),
(10,14),
(10,6),
(10,4),
(10,5),
(11,15),
(11,16),
(11,17),
(11,18),
(11,3),
(11,19),
(12,16),
(12,1),
(12,17),
(12,18),
(12,3),
(13,20),
(13,21),
(14,1),
(14,22),
(15,19),
(15,23),
(15,24),
(16,1),
(16,25),
(16,2),
(16,18),
(17,1),
(17,25),
(17,26),
(18,6),
(18,4),
(19,14),
(19,6),
(20,1),
(20,2),
(20,3),
(20,27);

INSERT INTO Location
(LocName, LocDesc, LocAddress, LocLatitude, LocLongitude)
VALUES
('Big Ben','The Great Bell of the clock at the north end of the Palace of Westminster in London.','Westminster, London SW1A 0AA, UK',51.510357,-0.116773),
('Senso-ji','Ancient Buddhist temple.','2 Chome-3-1 Asakusa, Taito, Tokyo 111-0032, Japan',35.689488,139.691706),
('Mount Erebus','Southernmost active volcano on Earth.','Ross Island, Antarctica',-77.529291,167.152311),
('Times Square','Bustling destination in the heart of the Theater District known for bright lights, shopping & shows.','Manhattan, NY 10036',40.758896,-73.985130),
('Great Pyramid at Giza','Oldest of the Seven Wonders of the Ancient World, and the only one to remain largely intact.','Al Haram, Nazlet El-Semman, Al Haram, Giza Governorate, Egypt',29.976480,	31.131302),
('St. Basil''s Cathedral','A cathedral ordered by Ivan the Terrible to mark the 1552 capture of Kazan from Mongol forces.','Red Square, Moskva, Russia, 109012',55.754093,37.620407),
('Frank''s Diner','Home of the Garbage Plate.','508-58th Street, Kenosha, Wisconsin 53140',42.583517,-87.817613),
('Location name fifty characters long for displaying','Location description 255 chars long long long and long and on on on and on and on....and more....and more chars...yet more loc chars....and more. And more. And more. Very long loc desc. Very very very long loc desc. Almost 255...not quite.....almost.','Location address may be 75 chars long...so babble away up to 75 chars..done',-89.999999,-179.999999),
('Unassociated Location','This location is used for nothing - so delete it.','The Moon',19.111111,34.333333);

INSERT INTO Organization
(OrgName,OrgDesc,OrgAddress,OrgEmail,OrgPhone)
VALUES
('Justice League','Powerful and premier superhero team, a strike force comprised of the world''s mightiest heroes.','The Hall of Justice in The Watchtower on the moon.','justiceleague@email.com','(555) 432-1234'),
('Outsiders','A team of super-heroes not concerned with the public appearance.','Old Batcave II under Gotham City.','outsiders@email.com','(555) 515-5555'),
('X-Men','Feared and hated by humans because they''re different, the X-Men are heroic mutants, individuals born with special powers who''ve sworn to use their gifts to protect mutants as well as humans.','1407 Graymalkin Lane, Salem Center, located in Westchester County, New York','xmen@email.com','(555) 111-1111'),
('Avengers','Earth''s Mightiest Heroes joined forces to take on threats that were too big for any one hero to tackle.','890 Fifth Avenue, Manhattan, New York City','avengers@email.com','(555) 222-5555'),
('Weapon X','Clandestine program dating back to 1945 created by civilian advisor to the military, Thorton, aka the Professor.','Formerly "Neverland"; Weapon X Facility, Alberta, Canada','weaponx@email.com','(666) 666-1666'),
('Secret Avengers','A black-ops sect of Marvel''s premiere super hero team, the Avengers, which operates under the guidance and leadership of Captain Steve Rogers (the former Captain America).','Multiple Safe-houses in New York city','secretavengers@email.com','(001) 111-1122'),
('S.H.I.E.L.D.','The Strategic Homeland Intervention, Enforcement and Logistics Division, better known by its acronym S.H.I.E.L.D., is a United States extra-governmental military counter-terrorism and intelligence agency, tasked with maintaining global security.','219 West 47th Street, New York City, New York, USA','shield@email.com','(010) 111-0000'),
('Gods of Asgard','The Asgardians are an extra-dimensional race of beings who were worshiped as gods by the Scandinavian and Germanic tribes of Western Europe.','Asgardia, orbiting Saturn.','asgardians@email.com','(222) 333-4444'),
('Defenders','Formed by Dr. Strange, a "non-team" with no charter or by-laws, fixed roster, and little affection for each other.','Dr. Strange''s Sanctum Sanctorum, Greenwich Village, New York','defenders@email.com','(444) 333-4343'),
('Cabal','Secret society of supervillains and anti-heroes.','Various worldwide headquarters centralized in Europe.','cabal@email.com','(666) 616-6661'),
('Brotherhood of Mutants','The Brotherhood of Mutants is a group dedicated to the cause of mutant superiority over humans.','Worldwide',null,null),
('Team X','A military black-ops unit division of C.I.A., formed by Weapon X Program in the 1960s.','None; mobile unit','teamx@email.com','(999) 991-1999'),
('X-Factor','Founded by former X-Men, a group of mutants who rescue young mutants to protect and train them.','X-Terminator Complex alongside Hudson River, Manhattan''s West Side','xfactor@email.com','(990) 900-0099'),
('Organization''s name char up to 50 chars long done.','Organization''s description up to 255 chars....goes on and one. And one. And on. And on and on and on..... And keeps going describing the organization. Very long description. Very long. Very very long. Describing the organization in 255 chars or less.','Organization''s address up to 75 chars it''s long, but maybe not too long eh?','Org_email.fakeemailzzz@emailserverdomainserver.com','(612) 1123-4567');

INSERT INTO EntityOrganization
(EntityID,OrgID)
VALUES
(1,1),(1,2),
(2,1),
(3,3),(3,4),(3,5),
(4,4),
(5,6),
(6,7),(6,4),
(7,3),
(8,6),(8,3),
(9,4),(9,8),
(10,4),
(11,3),
(12,3),
(13,3),
(14,3),(14,11),
(15,3),
(16,9),
(17,10),
(18,11),(18,12),
(19,11),(19,13),
(20,14);

INSERT INTO Sighting
(LocID,SightingDateTime)
VALUES
(1,'2013-03-11T12:00:01'),
(2,'2011-01-01T23:45:54'),
(3,'2001-12-31T19:23:01'),
(4,'2018-11-12T01:01:03'),
(5,'2015-06-07T07:06:00'),
(6,'2002-02-20T20:22:02'),
(7,'2018-01-01T00:04:00'),
(8,'2018-01-01T23:59:59');

INSERT INTO SightingEntity
(SightingID,EntityID)
VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(2,8),
(2,9),
(2,10),
(3,11),
(4,12),
(4,13),
(4,14),
(4,15),
(4,16),
(4,17),
(5,18),
(5,19),
(6,19),
(6,18),
(6,17),
(6,16),
(7,1),
(7,2),
(7,3),
(7,4),
(7,5),
(7,6),
(7,7),
(7,8),
(7,9),
(7,10),
(7,11),
(7,12),
(7,13),
(7,14),
(7,15),
(7,16),
(7,17),
(7,18),
(7,19),
(8,20);