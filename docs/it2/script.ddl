-- Generado por Oracle SQL Developer Data Modeler 23.1.0.087.0806
--   en:        2023-10-28 23:36:19 COT
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE bares (
    nombre          VARCHAR2(255) NOT NULL,
    horarioapertura DATE,
    horariocierre   DATE,
    capacidad       INTEGER NOT NULL,
    id              INTEGER NOT NULL
)
LOGGING;

ALTER TABLE bares ADD CONSTRAINT bares_pk PRIMARY KEY ( id );

CREATE TABLE consumoder (
    reservasid INTEGER NOT NULL,
    consumoid  INTEGER NOT NULL
)
LOGGING;

ALTER TABLE consumoder ADD CONSTRAINT consumoder_pk PRIMARY KEY ( reservasid,
                                                                  consumoid );

CREATE TABLE consumos (
    id    INTEGER NOT NULL,
    fecha DATE NOT NULL
)
LOGGING;

ALTER TABLE consumos ADD CONSTRAINT consumos_pk PRIMARY KEY ( id );

CREATE TABLE habitacion (
    id        INTEGER NOT NULL,
    capacidad INTEGER NOT NULL,
    tipoid    INTEGER NOT NULL
)
LOGGING;

ALTER TABLE habitacion ADD CONSTRAINT habitacion_pk PRIMARY KEY ( id );

CREATE TABLE planesconsumo (
    tipo            VARCHAR2(255) NOT NULL,
    dtonoche        FLOAT(3),
    descripcion     VARCHAR2(255) NOT NULL,
    descuentobar    NUMBER,
    descuentorest   NUMBER,
    descuentospa    NUMBER,
    limitebebidas   INTEGER,
    descuentolavado NUMBER,
    id              INTEGER NOT NULL
)
LOGGING;

ALTER TABLE planesconsumo ADD CONSTRAINT planesconsumo_pk PRIMARY KEY ( id );

CREATE TABLE platosbebidas (
    id        INTEGER NOT NULL,
    nombre    VARCHAR2(255 BYTE) NOT NULL,
    precio    NUMBER NOT NULL,
    consumoid INTEGER NOT NULL
)
LOGGING;

ALTER TABLE platosbebidas ADD CONSTRAINT platosbebidas_pk PRIMARY KEY ( id );

CREATE TABLE productos (
    nombre      VARCHAR2(255) NOT NULL,
    consumoid   INTEGER NOT NULL,
    id          INTEGER NOT NULL,
    tiendaid    INTEGER,
    smercadoid  INTEGER,
    preciofinal NUMBER
)
LOGGING;

ALTER TABLE productos ADD CONSTRAINT productos_pk PRIMARY KEY ( id );

CREATE TABLE productosbares (
    platosbebidasid INTEGER NOT NULL,
    barid           INTEGER NOT NULL
)
LOGGING;

ALTER TABLE productosbares ADD CONSTRAINT relation_32_pk PRIMARY KEY ( platosbebidasid,
                                                                       barid );

CREATE TABLE productosrest (
    platosbebidasid INTEGER NOT NULL,
    restid          INTEGER NOT NULL
)
LOGGING;

ALTER TABLE productosrest ADD CONSTRAINT relation_34_pk PRIMARY KEY ( platosbebidasid,
                                                                      restid );

CREATE TABLE reservas (
    id            INTEGER NOT NULL,
    fechaentrada  DATE NOT NULL,
    fechasalida   DATE NOT NULL,
    numpersonas   INTEGER NOT NULL,
    estado        CHAR(1) NOT NULL,
    precioreserva NUMBER NOT NULL,
    usuariosid    INTEGER NOT NULL,
    planid        INTEGER NOT NULL,
    habitacionid  INTEGER NOT NULL
)
LOGGING;

ALTER TABLE reservas ADD CONSTRAINT reservas_pk PRIMARY KEY ( id );

CREATE TABLE reservasservicio (
    fechayhorai DATE,
    fechayhoraf DATE,
    consumoid   INTEGER NOT NULL,
    precio      NUMBER NOT NULL,
    descripcion VARCHAR2(400 CHAR),
    id          INTEGER NOT NULL
)
LOGGING;

ALTER TABLE reservasservicio ADD CONSTRAINT reservasservicio_pk PRIMARY KEY ( id );

CREATE TABLE rests (
    nombre          VARCHAR2(255) NOT NULL,
    tipo            VARCHAR2(255),
    horarioapertura DATE,
    horariocierre   DATE,
    capacidad       INTEGER NOT NULL,
    id              INTEGER NOT NULL
)
LOGGING;

ALTER TABLE rests
    ADD CHECK ( tipo IN ( 'COLOMBIANO', 'INTERNACIONAL', 'ITALIANO', 'ORIENTAL' ) );

ALTER TABLE rests ADD CONSTRAINT restaurantes_pk PRIMARY KEY ( id );

CREATE TABLE servicio (
    id          INTEGER NOT NULL,
    descripcion VARCHAR2(400 CHAR) NOT NULL,
    precio      NUMBER NOT NULL,
    consumoid   INTEGER NOT NULL
)
LOGGING;

ALTER TABLE servicio ADD CONSTRAINT servicio_pk PRIMARY KEY ( id );

CREATE TABLE smercados (
    nombre          VARCHAR2(255) NOT NULL,
    horarioapertura DATE,
    horariocierre   DATE,
    capacidad       INTEGER NOT NULL,
    id              INTEGER NOT NULL
)
LOGGING;

ALTER TABLE smercados ADD CONSTRAINT smercados_pk PRIMARY KEY ( id );

CREATE TABLE tiendas (
    nombre          VARCHAR2(255) NOT NULL,
    horarioapertura DATE,
    horariocierre   DATE,
    capacidad       INTEGER NOT NULL,
    id              INTEGER NOT NULL
)
LOGGING;

ALTER TABLE tiendas ADD CONSTRAINT tiendas_pk PRIMARY KEY ( id );

CREATE TABLE tiposhab (
    tipo        VARCHAR2(255) NOT NULL,
    descripcion VARCHAR2(255) NOT NULL,
    id          INTEGER NOT NULL
)
LOGGING;

ALTER TABLE tiposhab
    ADD CHECK ( tipo IN ( 'DOBLE', 'FAMILIAR', 'PRESIDENCIAL', 'SENCILLA', 'SUITE' ) );

ALTER TABLE tiposhab ADD CONSTRAINT tiposhab_pk PRIMARY KEY ( id );

CREATE TABLE tiposusuario (
    id          INTEGER NOT NULL,
    tipo        VARCHAR2(255) NOT NULL,
    descripcion VARCHAR2(400)
)
LOGGING;

ALTER TABLE tiposusuario ADD CONSTRAINT tiposusuario_pk PRIMARY KEY ( id );

CREATE TABLE usuarios (
    id     INTEGER NOT NULL,
    nombre VARCHAR2(255) NOT NULL,
    cedula VARCHAR2(255) NOT NULL,
    tipoid INTEGER NOT NULL
)
LOGGING;

ALTER TABLE usuarios ADD CONSTRAINT usuarios_pk PRIMARY KEY ( id );

ALTER TABLE consumoder
    ADD CONSTRAINT consumoder_consumos_fk FOREIGN KEY ( consumoid )
        REFERENCES consumos ( id )
    NOT DEFERRABLE;

ALTER TABLE consumoder
    ADD CONSTRAINT consumoder_reservas_fk FOREIGN KEY ( reservasid )
        REFERENCES reservas ( id )
    NOT DEFERRABLE;

ALTER TABLE habitacion
    ADD CONSTRAINT habitacion_tiposhab_fk FOREIGN KEY ( tipoid )
        REFERENCES tiposhab ( id )
    NOT DEFERRABLE;

ALTER TABLE platosbebidas
    ADD CONSTRAINT platosbebidas_consumos_fk FOREIGN KEY ( consumoid )
        REFERENCES consumos ( id )
    NOT DEFERRABLE;

ALTER TABLE productos
    ADD CONSTRAINT productos_consumos_fk FOREIGN KEY ( consumoid )
        REFERENCES consumos ( id )
    NOT DEFERRABLE;

ALTER TABLE productos
    ADD CONSTRAINT productos_smercados_fk FOREIGN KEY ( smercadoid )
        REFERENCES smercados ( id )
    NOT DEFERRABLE;

ALTER TABLE productos
    ADD CONSTRAINT productos_tiendas_fk FOREIGN KEY ( tiendaid )
        REFERENCES tiendas ( id )
    NOT DEFERRABLE;

ALTER TABLE productosbares
    ADD CONSTRAINT relation_32_bares_fk FOREIGN KEY ( barid )
        REFERENCES bares ( id )
    NOT DEFERRABLE;

ALTER TABLE productosbares
    ADD CONSTRAINT relation_32_platosbebidas_fk FOREIGN KEY ( platosbebidasid )
        REFERENCES platosbebidas ( id )
    NOT DEFERRABLE;

ALTER TABLE productosrest
    ADD CONSTRAINT relation_34_platosbebidas_fk FOREIGN KEY ( platosbebidasid )
        REFERENCES platosbebidas ( id )
    NOT DEFERRABLE;

ALTER TABLE productosrest
    ADD CONSTRAINT relation_34_rests_fk FOREIGN KEY ( restid )
        REFERENCES rests ( id )
    NOT DEFERRABLE;

ALTER TABLE reservas
    ADD CONSTRAINT reservas_habitacion_fk FOREIGN KEY ( habitacionid )
        REFERENCES habitacion ( id )
    NOT DEFERRABLE;

ALTER TABLE reservas
    ADD CONSTRAINT reservas_planesconsumo_fk FOREIGN KEY ( planid )
        REFERENCES planesconsumo ( id )
    NOT DEFERRABLE;

ALTER TABLE reservas
    ADD CONSTRAINT reservas_usuarios_fk FOREIGN KEY ( usuariosid )
        REFERENCES usuarios ( id )
    NOT DEFERRABLE;

ALTER TABLE reservasservicio
    ADD CONSTRAINT reservasservicio_consumos_fk FOREIGN KEY ( consumoid )
        REFERENCES consumos ( id )
    NOT DEFERRABLE;

ALTER TABLE servicio
    ADD CONSTRAINT servicio_consumos_fk FOREIGN KEY ( consumoid )
        REFERENCES consumos ( id )
    NOT DEFERRABLE;

ALTER TABLE usuarios
    ADD CONSTRAINT usuarios_tiposusuario_fk FOREIGN KEY ( tipoid )
        REFERENCES tiposusuario ( id )
    NOT DEFERRABLE;



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            18
-- CREATE INDEX                             0
-- ALTER TABLE                             37
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
