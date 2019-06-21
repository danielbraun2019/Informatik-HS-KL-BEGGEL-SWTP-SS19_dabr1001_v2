drop table if exists sc_exercise;
drop table if exists sc_group;
drop table if exists sc_scenario;


DROP SEQUENCE if exists seq_sc_scenario_pk;
DROP SEQUENCE if exists seq_sc_scenario_rank;
CREATE SEQUENCE seq_sc_scenario_pk START 1;
CREATE SEQUENCE seq_sc_scenario_rank START 1;


DROP SEQUENCE if exists seq_sc_group_pk;
DROP SEQUENCE if exists seq_sc_group_rank;
CREATE SEQUENCE seq_sc_group_pk START 1;
CREATE SEQUENCE seq_sc_group_rank START 1;


DROP SEQUENCE if exists seq_sc_exercise_pk;
DROP SEQUENCE if exists seq_sc_exercise_rank;
CREATE SEQUENCE seq_sc_exercise_pk START 1;
CREATE SEQUENCE seq_sc_exercise_rank START 1;


create table sc_scenario
(
  scenarioId    integer NOT NULL DEFAULT nextval('seq_sc_scenario_pk') PRIMARY KEY,
  scenarioName  varchar(100)  NOT NULL,
  scenarioOwner varchar(100)  NOT NULL,
  rank          integer NOT NULL DEFAULT nextval('seq_sc_scenario_rank'),
  datasetId     integer NOT NULL
);

create table sc_group
(
  groupId    integer NOT NULL DEFAULT nextval('seq_sc_group_pk') PRIMARY KEY,
  groupName  varchar(100)  NOT NULL,
  scenarioId INTEGER  NOT NULL references sc_scenario(scenarioId),
  rank       integer NOT NULL DEFAULT nextval('seq_sc_group_rank')
);


create table sc_exercise
(
  exerciseId       integer NOT NULL DEFAULT nextval('seq_sc_exercise_pk') PRIMARY KEY,
  exerciseText     varchar(2000) NOT NULL,
  exerciseSolution varchar(2000) NOT NULL,
  groupId          integer NOT NULL REFERENCES sc_group(groupId),
  rank             integer NOT NULL DEFAULT nextval('seq_sc_exercise_rank')
);


--insert scenarios
insert into sc_scenario(scenarioName, scenarioOwner,datasetId )
VALUES ('Personal', 'Prof. Dr. Schiefer',1);


--insert groups
insert into sc_group(groupName, scenarioId)
VALUES ('Einfache Selects', currval('seq_sc_scenario_pk'));
insert into sc_group(groupName, scenarioId)
VALUES ('Aggregatfunktionen', currval('seq_sc_scenario_pk'));
insert into sc_group(groupName, scenarioId)
VALUES ('Join Queries', currval('seq_sc_scenario_pk'));
insert into sc_group(groupName, scenarioId)
VALUES ('Komplexe Queries', currval('seq_sc_scenario_pk'));
insert into sc_group(groupName, scenarioId)
VALUES ('Subqueries', currval('seq_sc_scenario_pk'));
insert into sc_group(groupName, scenarioId)
VALUES ('Data Directories', currval('seq_sc_scenario_pk'));



insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (1, 'Geben Sie den Nach- und Vornamen aller Angestellten aufsteigend sortiert aus!',
        'select nname, vname from personal order by nname asc')
;

insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (1, 'Finden Sie alle alle Angestellten, deren Gehalt zwischen 1500 und 3000 liegt!',
        'select * from Personal where Gehalt between 1500 and 3000')
;

insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (1, 'Alle Angestellen mit ''ei'' im Nachnamen!', 'SELECT * FROM personal WHERE nname LIKE ''%ei%''')
;

insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (1, 'Finde alle mit ''meier'' im Nachnamen!', 'SELECT * FROM personal WHERE UPPER (nname) LIKE ''%MEIER%''')
;

insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (1, 'Finden Sie alle Angestellten, deren Nachnamen mehr als 4 Buchstaben hat!',
        'SELECT * FROM Personal WHERE nname LIKE ''_____%''')
;

insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (1, 'Finden Sie alle Angestellten, die keinen Nachnamen haben!', 'SELECT * FROM Personal WHERE NName IS NULL')
;

insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (1, 'Finden sie alle Akteneinträge, die nach 1998 mit einem Gehalt von über 2800 gemacht wurden!',
        'select * from akte where datum > ''1997-12-31'' and gehalt > 2800')
;


insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (2, 'Wieviel Gehalt wird für alle Angestellten ausbezahlt?', 'SELECT sum(gehalt) gehaltssumme FROM personal')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (2, 'Bestimmen Sie die Anzahl der Mitarbeiter pro Projekt absteigend sortiert nach der Mitarbeiterzahl!',
        'SELECT projnr, count(*) as anzahl FROM personal GROUP BY projnr  ORDER BY anzahl')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (2, 'Wie viele Projekte führen die Abteilungen im Mittel aus?',
        'SELECT avg(projcnt) Mittlere_Projekt_Anzahl FROM (SELECT count(*) AS projcnt FROM projekt GROUP BY abtnr)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (2, 'Finden Sie alle Telefonnummern, die mehr als 1mal vergeben wurden!',
        'SELECT telefonnr, count(*) Anzahl FROM personal  GROUP BY  telefonnr having count(*) > 1')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (2,
        'Wieviel Gehalt wird pro Abteilung ausbezahlt? - Sortieren Sie nach der AbtNr - HINWEIS: Es brauchen nur die 	  Abteilungen, die Mitarbeiter haben, ausgegeben werden!',
        'SELECT a.abtnr, SUM(gehalt) FROM abteilung a, projekt p, personal pe WHERE a.abtnr = p.abtnr AND p.projnr = pe.projnr GROUP BY a.abtnr')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (2,
        'Finden Sie alle Projekte, die mehr als 5 Mitarbeiter haben ODER für ihre Mitarbeiter zusammen mehr als 3000 Euro Gehalt zahlen müssen!',
        'SELECT projnr, sum(gehalt) gehalt, count(*)  anzahl FROM personal GROUP BY projnr HAVING count(*) >  5 OR SUM(gehalt) > 3000')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (2, 'Wie viele Mitarbeiter haben die Projekte im Mittel?',
        'SELECT AVG(mp) FROM (SELECT COUNT(*) mp FROM Personal GROUP BY projnr)')
;


insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (3,
        'Erstellen Sie eine Telefonliste der Abteilungsleiter mit Abteilungsname, NNamen, VName und Telefonnummer, sortiert nach dem Abteilungsnamen!',
        'SELECT DISTINCT abtname, nname, vname, telefonnr FROM Personal p JOIN Abteilung a ON p.persnr = a.persnr ORDER BY abtname')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (3, 'Geben Sie für jeden Angestellten aus: persnr, vname, nname sowie die Akteneinträge sofern vorhanden!',
        'Select p.persnr, vname, nname, a.* from personal p left outer join akte a on (p.persnr = a.persnr)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (3, 'Ermitteln Sie die Daten aller Untergebenen von Donald!',
        'SELECT  m.* FROM personal m JOIN projekt p ON m.projnr=p.projnr JOIN abteilung a ON p.abtnr = a.abtnr JOIN personal c ON a.persnr = c.persnr WHERE c.vname = ''Donald''')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (3, 'Ermitteln Sie den Chef von Obelix!',
        'SELECT c.* FROM personal m JOIN projekt p ON m.projnr=p.projnr JOIN abteilung a ON p.abtnr = a.abtnr JOIN personal c ON a.persnr = c.persnr WHERE m.vname = ''Obelix''')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (3, 'Geben Sie alle 12 Abteilungen aus mit der Anzahl der ihnen zugeordneten Projekte!',
        'SELECT a.abtnr, a.abtname, COUNT(projnr) FROM Abteilung a LEFT JOIN Projekt p ON (a.abtnr = p.abtnr) GROUP BY a.abtnr, a.abtname')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (3,
        'Geben Sie alle Projekte aus und zu jedem Projekt den Namen des Abteilungsleiters, falls dieser dem Projekt zugeordnet ist!',
        'SELECT p.projnr, p.abtnr, vname FROM projekt p JOIN abteilung a ON p.abtnr = a.abtnr LEFT JOIN personal pe ON pe.persnr = a.persnr AND pe.projnr = p.projnr ORDER BY p.projnr')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (3,
        'Finden Sie alle Angestellte, deren Gehalt sich um mindestens 1000 seit ihrem ersten Akteneintrag erhöht hat!',
        'SELECT DISTINCT a.persnr from Akte a JOIN Akte b ON a.persnr = b.persnr and a.datum < b.datum AND b.gehalt-a.gehalt > 1000')
;



insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (4, 'Bestimmen Sie die Abteilungen mit den meisten Projekten, sowie die Anzahl der Projekte!',
        'SELECT abtnr, panz FROM (SELECT abtnr, count(*) panz FROM projekt p GROUP BY abtnr) WHERE panz >= ALL (SELECT count(*) FROM projekt p GROUP BY abtnr)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (4, 'Ermitteln Sie die durchschnittliche Anzahl von Projekten pro Abteilung!',
        'SELECT avg(panz) FROM (SELECT abtnr, COUNT(*) panz FROM projekt p GROUP BY abtnr)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (4,
        'Geben Sie ALLE Angestellten sowie die Anzahl ihrer Akteneinträge aus (Sortiert nach persnr)! ACHTUNG: Nicht alle haben Akteneinträge!',
        'SELECT p.persnr, p.nname, p.vname,COUNT(a.persnr) anzahl FROM akte a RIGHT JOIN personal p on a.persnr = p.persnr GROUP BY p.persnr, p.nname, p.vname')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (4,
        'Ermitteln Sie alle Angestellte, die Akteneinträge vor dem 1993-01-01 haben, sowie die Anzahl dieser Einträge!',
        'SELECT p.persnr, nname, vname, anzahl FROM personal p JOIN (SELECT persnr, count(*) anzahl FROM Akte WHERE datum < ''1993-01-01'' GROUP BY persnr) a ON (p.persnr = a.persnr)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (4, 'Ermitteln Sie die Projekte mit dem höchsten Durchschnittsgehalt der beteiligten Personen!',
        'SELECT projnr, avg (gehalt) FROM personal GROUP BY projnr having avg (gehalt) = (select MAX (avggehalt) from ( select AVG (gehalt) avggehalt from personal group by projnr))')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (4, 'Geben Sie für den Angestellten mit der persnr 94 alle Gehaltserhöhungen mit dem jeweiligen Datum aus!',
        'SELECT b.gehalt-a.gehalt erhoehung, b.datum FROM Akte a JOIN Akte b ON a.persnr=b.persnr AND a.datum<b.datum WHERE a.persnr=94 AND NOT EXISTS (SELECT datum FROM Akte WHERE persnr=94 AND datum>a.datum AND datum<b.datum)')
;



insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (5, 'Geben Sie die Personaldaten aller Angestellten aus, die keinen Akteneintrag haben!',
        'SELECT * FROM personal WHERE persnr NOT IN (SELECT persnr FROM akte)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (5,
        'Geben Sie alle Abteilungen (abtname,budget) nach dem Budget sortiert aus, für die es noch eine weitere gibt, die über das gleiche Budget verfügt!',
        'select a.abtname, a.budget from abteilung a where exists (select * from abteilung b where b.budget = a.budget and a.abtnr <> b.abtnr) order by budget')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (5, 'Geben Sie alle Mitarbeiter mit gleichen Namen aus! Verwenden Sie dazu: EXISTS',
        'select persnr, nname, vname from personal p where exists ( select * from personal where nname = p.nname and vname = p.vname and persnr <> p.persnr )')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (5, 'Ermitteln Sie den Angestellten mit dem längsten Nachnamen!',
        'SELECT nname, length(nname) FROM personal where length(nname) = (SELECT MAX(length(nname)) FROM personal)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (5, 'Geben Sie die Nummern aller Projekte an denen kein Meier mitarbeitet in aufsteigender Reihenfolge aus!',
        'SELECT projnr FROM Projekt WHERE projnr NOT IN ( SELECT projnr from Personal WHERE nname = ''Meier'' ) ORDER BY projnr')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (5, 'Geben Sie die Angestellten mit dem geringsten Gehalt aus!',
        'select * from personal where gehalt = (select min(gehalt) from personal)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (5, 'Geben Sie den neusten Akteneintrag aus!', 'SELECT * FROM akte WHERE datum=(SELECT MAX(datum) FROM akte)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (5, 'Finden Sie alle Angestellten, die nicht am oberen oder unteren Ende mit ihrem Gehalt liegen!',
        'SELECT * FROM Personal WHERE gehalt>(SELECT MIN(gehalt)FROM Personal) AND gehalt<(SELECT MAX(gehalt)FROM Personal)')
;
insert into sc_exercise(groupId, exerciseText, exerciseSolution)
VALUES (5, 'Finden Sie alle Angestellten, die überdurchschnittlich viel verdienen!',
        'SELECT * FROM Personal WHERE gehalt>(SELECT AVG(gehalt)FROM Personal)')
;


