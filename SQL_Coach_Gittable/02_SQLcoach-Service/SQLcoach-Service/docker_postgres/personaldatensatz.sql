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
  Gehalt    DECIMAL (8,2)  CHECK (Gehalt >=0),
  rank      integer NOT NULL DEFAULT nextval('seq_personal_rank')
);

create table akte
(
      PersNr    INTEGER  NOT NULL references personal(PersNr),
      Datum     Date       NOT NULL,
      Position  VARCHAR(25),
      Gehalt    DECIMAL(8,2) CHECK (Gehalt >= 0),
      rank      decimal(5) NOT NULL,
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

INSERT INTO personal(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)(PersNr, VNAME, NName, ProjNr, TelefonNr, Gehalt)
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
