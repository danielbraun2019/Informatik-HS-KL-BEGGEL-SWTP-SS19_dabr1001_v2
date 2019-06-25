drop table if exists personal;
drop table if exists akte;
drop table if exists abteilung;
drop table if exists projekt;

create table personal
(
  PersNr decimal(5) NOT NULL PRIMARY KEY,
  VNAME  varchar(64)  NOT NULL,
  NName varchar(64)  NOT NULL,
  ProjNr    Decimal(4) NOT NULL,
  TelefonNr Decimal (10),
  Gehalt    DECIMAL (8,2)  CHECK (Gehalt >=0)
);

create table akte
(
      PersNr    INTEGER  NOT NULL references personal(PersNr),
      Datum     Date       NOT NULL,
      Position  VARCHAR(25),
      Gehalt    DECIMAL(8,2) CHECK (Gehalt >= 0)
      PRIMARY KEY (PersNr,Datum)
);

create table abteilung (
      AbtNr     Decimal(4) NOT NULL PRIMARY KEY ,
      AbtName   VARCHAR2(15) NOT NULL,
      Budget    DECIMAL(7) CHECK (Budget >= 0),
      ChefNr    INTEGER  NOT NULL references personal(PersNr)
);
create table projekt (
      ProjNr    DECIMAL(4) NOT NULL PRIMARY KEY ,
      Budget    DECIMAL(7) CHECK (Budget >= 0),
      AbtNr     INTEGER  NOT NULL references abteilung(AbtNr)
);

INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (1, 'Donald',	'Duck',	1, 1201, 1000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (2, 'Dagobert',	'Duck',	1, 1202, 200);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (3, 'Zita',		'Delle',	1, 1203, 50);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (4, 'Axel', 	'Nässe',	1, 1203, 50);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (5, 'Anna',  	'Bolika',	1, 1203, 50);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (6, 'Gustav',	'Gans',	23, 2201, 2000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (7, 'Gitta',	'Gans',	23, 2202, 0);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (8, 'Daniel',	'Duesentrieb',	2, 2203, 5000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (10,'Klaas',	'Klever',	1, 1204, 50);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (21,'Klaus',	'Trophobie',	3, 3201, 2000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (71,'Micky',	'Maus',	3, 3201, 3000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (72,'Anna',	        'Nass',	3, 3202, 1000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (73,'Minni',	'Maus',	3, 3203, 2000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (51,'Rainer',	'Wein',	6, 6202, 100);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (52,'Rainer',	'Zufall',	6, 6203, 100);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (81,'Tick', 	NULL,	7, 5201, 1500);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (82,'Trick',	NULL,	7, 5201, 1500);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (83,'Franz',	'Gans',	7, 5205, 2000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (84,'Track',        NULL,	4, 4201, 2000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (90,'Klaus',	'Trophobie',	4, 4201, 2000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (91,'Axel', 	'Schweiss',	4, 4201, 2000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (93,'Fenn',	        'Sterputz',	4, 4203, 2000);
INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
VALUES (94,'Heinz',  	'Elmann',	4, 4204, 7000);

INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (1, 'Buchhaltung',	10000,	1);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (2, 'FuE',		15000,	71);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (3, 'Immobilien',	4000,	91);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (4, 'Marketing',	50000,	81);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (5, 'Controling',	20000,	94);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (6, 'Hausmeister', 1000,	94);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (7, 'Kantine',	1000,	94);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (8, 'Vorstand',	100000,	94);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (9, 'Fuhrpark',	7000,	94);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (10, 'Service',	30000,	71);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (11, 'Einkauf',	15000,	1);
INSERT INTO abteilung(abtnr, abtname, budget, chefnr)
VALUES (12, 'Verkauf',	30000,	1);


INSERT INTO projekt(projnr, budget, abtnr)
VALUES (1, 5000,	1);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (2, 1000,	1);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (3, 2200,	10);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (4, 100,	3);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (5, 500,	4);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (6, 1000,	1);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (7, 3000,	4);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (8, 1000,	8);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (9, 1000,	4);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (10, 1000,	7);
INSERT INTO projekt(projnr, budget, abtnr)
VALUES (23, 5500,	3);

INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (1, '2009-04-01', 'Oberbuchhalter',	1000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (1, '2006-01-01', 'Hilfsbuchhalter',	100);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (1, '2007-09-01', 'Buchhalter',		500);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (2, '2007-11-01', 'Buchhalter',		200);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (2, '2007-01-01', 'Hilfsuchhalter',	90);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (3, '2007-01-01', 'Hilfsbuchhalter',	90);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
ALUES (4, '2007-01-01', 'Hilfsbuchhalter',	90);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (5, '2007-01-01', 'Hilfsbuchhalter',	90);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (6, '2003-01-01', 'Angestellter',		2000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (7, '2009-01-01', 'Praktikant',		0);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (8, '2008-01-01', 'Tüftler',		2000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (8, '2008-06-01', 'Erfinder',		3000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (8, '2009-04-01', 'Cheferfinder',	5000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (10, '2008-01-01', 'Hilfsbuchhalter',	50);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (94, '1982-03-01', 'Schuhputzer',	50);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (94, '1982-09-01', 'Tellerwäscher',	100);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (94, '1983-05-01', 'Tellerstapler',	200);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (94, '1988-05-01', 'Küchenchef',	1000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (94, '1999-05-01', 'Abteilungsleiter',	3000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (94, '2008-05-01', 'Hauptabteilungsleiter', 7000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (91, '1997-05-01', 'Angestellter',	1000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (91, '2007-05-01', 'Abteilungsleiter',	2000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (71, '2002-01-01', 'Junior Berater',	1000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (71, '2003-04-01', 'Berater',		2000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (71, '2008-04-01', 'Senior Berater',	3000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (21, '2001-01-01', 'Junior Berater',	800);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (21, '2002-04-01', 'Berater',		1200);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (21, '2007-04-01', 'Senior Berater',	2000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (90, '2008-01-01', 'Angestellter',	2000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (91, '2003-01-01', 'Praktikant',	        20);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (91, '2003-05-01', 'Angestellter',	2000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (93, '2003-05-01', 'Angestellter',	2000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (94, '2004-01-01', 'Angestellter',	2000);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (81, '2008-01-01', 'Berater',	1500);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (82, '2008-01-01', 'Berater',	1500);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (84, '2008-01-01', 'Berater',	1500);
INSERT INTO akte(PersNr, Datum, "Position", Gehalt)
VALUES (84, '2009-01-01', 'Berater',	2000);