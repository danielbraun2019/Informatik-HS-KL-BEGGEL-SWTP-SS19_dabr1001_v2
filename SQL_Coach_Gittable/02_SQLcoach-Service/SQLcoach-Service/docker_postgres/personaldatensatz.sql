drop table if exists personal;

DROP SEQUENCE if exists seq_personal_pk;
DROP SEQUENCE if exists seq_personal_rank;
CREATE SEQUENCE seq_personal_pk START 1;
CREATE SEQUENCE seq_personal_rank START 1;

create table personal
(
  PersNr integer NOT NULL DEFAULT nextval('seq_personal_pk') PRIMARY KEY,
  VNAME  varchar(64)  NOT NULL,
  NName varchar(64)  NOT NULL,
  ProjNr    Decimal(4) NOT NULL,
  TelefonNr DECIMAL (10),
  Gehalt    DECIMAL (8,2)  CHECK (Gehalt >=0),
  rank          integer NOT NULL DEFAULT nextval('seq_personal_rank')
);

insert into personal(vname, nname, projnr, telefonnr)
values ('Peter', 'Braun',123,1234567891);
