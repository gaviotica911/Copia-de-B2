-- Generado por Oracle SQL Developer Data Modeler 23.1.0.087.0806
--   en:        2023-10-16 20:33:30 COT
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE barbebida (
    bares_nombre   VARCHAR2(255) NOT NULL,
    bebidas_nombre VARCHAR2(255) NOT NULL
);

ALTER TABLE barbebida ADD CONSTRAINT barbebida_pk PRIMARY KEY ( bares_nombre,
                                                                bebidas_nombre );

CREATE TABLE bares (
    nombre          VARCHAR2(255) NOT NULL,
    horarioapertura DATE,
    horariocierre   DATE,
    capacidad       INTEGER NOT NULL
);

ALTER TABLE bares ADD CONSTRAINT bares_pk PRIMARY KEY ( nombre );

CREATE TABLE bebidas (
    nombre             VARCHAR2(255) NOT NULL,
    precio             NUMBER NOT NULL,
    consumos_idconsumo INTEGER NOT NULL
);

ALTER TABLE bebidas ADD CONSTRAINT bebidas_pk PRIMARY KEY ( nombre );

CREATE TABLE consumoder (
    reservas_id_reserva INTEGER NOT NULL,
    consumos_idconsumo  INTEGER NOT NULL
);

ALTER TABLE consumoder ADD CONSTRAINT consumoder_pk PRIMARY KEY ( reservas_id_reserva,
                                                                  consumos_idconsumo );

CREATE TABLE consumos (
    idconsumo INTEGER NOT NULL
);

ALTER TABLE consumos ADD CONSTRAINT consumos_pk PRIMARY KEY ( idconsumo );

CREATE TABLE gimnasios (
    nombre             VARCHAR2(255) NOT NULL,
    horarioapertura    DATE,
    horariocierre      DATE,
    capacidad          INTEGER NOT NULL,
    consumos_idconsumo INTEGER,
    preciofinal        NUMBER
);

ALTER TABLE gimnasios
    ADD CHECK ( consumos_idconsumo IS NULL
                AND preciofinal IS NULL );

ALTER TABLE gimnasios ADD CONSTRAINT gimnasios_pk PRIMARY KEY ( nombre );

CREATE TABLE habitaciones (
    id_habitacion       INTEGER NOT NULL,
    capacidad           INTEGER NOT NULL,
    reservas_id_reserva INTEGER,
    tiposhab_id         INTEGER NOT NULL
);

ALTER TABLE habitaciones ADD CONSTRAINT habitaciones_pk PRIMARY KEY ( id_habitacion );

CREATE TABLE internets (
    capacidad          INTEGER NOT NULL,
    consumos_idconsumo INTEGER NOT NULL,
    id                 INTEGER NOT NULL,
    preciofinal        NUMBER
);

ALTER TABLE internets ADD CONSTRAINT internets_pk PRIMARY KEY ( consumos_idconsumo,
                                                                id );

CREATE TABLE lavanderias (
    numprendas         INTEGER NOT NULL,
    numzapatos         INTEGER NOT NULL,
    tipolavado         VARCHAR2(255) NOT NULL,
    costo              NUMBER(13, 2) NOT NULL,
    idlavanderia       INTEGER NOT NULL,
    consumos_idconsumo INTEGER,
    preciofinal        NUMBER
);

ALTER TABLE lavanderias
    ADD CHECK ( consumos_idconsumo IS NULL
                AND preciofinal IS NULL );

ALTER TABLE lavanderias ADD CONSTRAINT lavanderias_pk PRIMARY KEY ( idlavanderia );

CREATE TABLE maquinas (
    nombre           VARCHAR2(255) NOT NULL,
    gimnasios_nombre VARCHAR2(255) NOT NULL,
    id               INTEGER NOT NULL
);

ALTER TABLE maquinas ADD CONSTRAINT maquinas_pk PRIMARY KEY ( id,
                                                              gimnasios_nombre );

CREATE TABLE piscinas (
    profundidad        INTEGER NOT NULL,
    horarioapertura    DATE,
    horariocierre      DATE,
    nombre             VARCHAR2(255) NOT NULL,
    consumos_idconsumo INTEGER NOT NULL,
    preciofinal        NUMBER
);

ALTER TABLE piscinas ADD CONSTRAINT piscinas_pk PRIMARY KEY ( nombre );

CREATE TABLE planesconsumo (
    tipo            VARCHAR2(255) NOT NULL,
    dtonoche        FLOAT(3),
    descripcion     VARCHAR2(255) NOT NULL,
    descuentobar    NUMBER,
    descuentorest   NUMBER,
    descuentospa    NUMBER,
    limitebebidas   INTEGER,
    descuentolavado NUMBER
);

ALTER TABLE planesconsumo ADD CONSTRAINT planesconsumo_pk PRIMARY KEY ( tipo );

CREATE TABLE platos (
    nombre             VARCHAR2(255) NOT NULL,
    precio             NUMBER NOT NULL,
    consumos_idconsumo INTEGER NOT NULL,
    preciofinal        NUMBER
);

ALTER TABLE platos ADD CONSTRAINT platos_pk PRIMARY KEY ( nombre );

CREATE TABLE productos (
    nombre             VARCHAR2(255) NOT NULL,
    precio             NUMBER NOT NULL,
    tiendas_nombre     VARCHAR2(255),
    smercados_nombre   VARCHAR2(255),
    consumos_idconsumo INTEGER NOT NULL,
    preciofinal        NUMBER
);

ALTER TABLE productos ADD CONSTRAINT productos_pk PRIMARY KEY ( nombre );

CREATE TABLE reservas (
    id_reserva         INTEGER NOT NULL,
    fechaentrada       DATE NOT NULL,
    fechasalida        DATE NOT NULL,
    numpersonas        INTEGER NOT NULL,
    estado             CHAR(1) NOT NULL,
    precioreserva      NUMBER NOT NULL,
    usuarios_id        INTEGER NOT NULL,
    planesconsumo_tipo VARCHAR2(255) NOT NULL
);

ALTER TABLE reservas ADD CONSTRAINT reservas_pk PRIMARY KEY ( id_reserva );

CREATE TABLE reservasservicio (
    idreservas         INTEGER NOT NULL,
    fechayhorai        DATE,
    fechayhoraf        DATE,
    spas_nombre        VARCHAR2(255),
    salones_nombre     VARCHAR2(255),
    consumos_idconsumo INTEGER NOT NULL,
    utensilios_id      INTEGER NOT NULL,
    preciofinal        NUMBER
);

ALTER TABLE reservasservicio ADD CONSTRAINT reservasservicio_pk PRIMARY KEY ( idreservas );

CREATE TABLE restbebida (
    rests_nombre   VARCHAR2(255) NOT NULL,
    bebidas_nombre VARCHAR2(255) NOT NULL
);

ALTER TABLE restbebida ADD CONSTRAINT restbebida_pk PRIMARY KEY ( rests_nombre,
                                                                  bebidas_nombre );

CREATE TABLE restplato (
    rests_nombre  VARCHAR2(255) NOT NULL,
    platos_nombre VARCHAR2(255) NOT NULL
);

ALTER TABLE restplato ADD CONSTRAINT restplato_pk PRIMARY KEY ( rests_nombre,
                                                                platos_nombre );

CREATE TABLE rests (
    nombre          VARCHAR2(255) NOT NULL,
    tipo            VARCHAR2(255),
    horarioapertura DATE,
    horariocierre   DATE,
    capacidad       INTEGER NOT NULL
);

ALTER TABLE rests
    ADD CHECK ( tipo IN ( 'COLOMBIANO', 'INTERNACIONAL', 'ITALIANO', 'ORIENTAL' ) );

ALTER TABLE rests ADD CONSTRAINT rests_pk PRIMARY KEY ( nombre );

CREATE TABLE salones (
    tiposalon       VARCHAR2(255) NOT NULL,
    horarioapertura DATE,
    horariocierre   DATE,
    nombre          VARCHAR2(255) NOT NULL,
    capacidad       INTEGER
);

ALTER TABLE salones
    ADD CHECK ( tiposalon IN ( 'CONFERENCIA', 'REUNION' ) );

ALTER TABLE salones ADD CONSTRAINT salones_pk PRIMARY KEY ( nombre );

CREATE TABLE smercados (
    nombre          VARCHAR2(255) NOT NULL,
    horarioapertura DATE,
    horariocierre   DATE,
    capacidad       INTEGER NOT NULL
);

ALTER TABLE smercados ADD CONSTRAINT smercados_pk PRIMARY KEY ( nombre );

CREATE TABLE spas (
    nombre          VARCHAR2(255) NOT NULL,
    horarioapertura DATE,
    horariocierre   DATE,
    capacidad       INTEGER NOT NULL
);

ALTER TABLE spas ADD CONSTRAINT spas_pk PRIMARY KEY ( nombre );

CREATE TABLE tiendas (
    nombre          VARCHAR2(255) NOT NULL,
    horarioapertura DATE,
    horariocierre   DATE,
    capacidad       INTEGER NOT NULL
);

ALTER TABLE tiendas ADD CONSTRAINT tiendas_pk PRIMARY KEY ( nombre );

CREATE TABLE tiposhab (
    tipo        VARCHAR2(255) NOT NULL,
    descripcion VARCHAR2(255) NOT NULL,
    id          INTEGER NOT NULL
);

ALTER TABLE tiposhab
    ADD CHECK ( tipo IN ( 'DOBLE', 'FAMILIAR', 'PRESIDENCIAL', 'SENCILLA', 'SUITE' ) );

ALTER TABLE tiposhab ADD CONSTRAINT tiposhab_pk PRIMARY KEY ( id );

CREATE TABLE tiposusuario (
    id          INTEGER NOT NULL,
    tipo        VARCHAR2(255) NOT NULL,
    descripción VARCHAR2(400)
);

ALTER TABLE tiposusuario ADD CONSTRAINT tiposusuario_pk PRIMARY KEY ( id );

CREATE TABLE usuarios (
    id              INTEGER NOT NULL,
    nombre          VARCHAR2(255) NOT NULL,
    cedula          VARCHAR2(255) NOT NULL,
    tiposusuario_id INTEGER NOT NULL
);

ALTER TABLE usuarios ADD CONSTRAINT usuarios_pk PRIMARY KEY ( id );

CREATE TABLE utensilios (
    id     INTEGER NOT NULL,
    nombre VARCHAR2(255 BYTE) NOT NULL
);

ALTER TABLE utensilios ADD CONSTRAINT utensilios_pk PRIMARY KEY ( id );

ALTER TABLE barbebida
    ADD CONSTRAINT barbebida_bares_fk FOREIGN KEY ( bares_nombre )
        REFERENCES bares ( nombre );

ALTER TABLE barbebida
    ADD CONSTRAINT barbebida_bebidas_fk FOREIGN KEY ( bebidas_nombre )
        REFERENCES bebidas ( nombre );

ALTER TABLE bebidas
    ADD CONSTRAINT bebidas_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE consumoder
    ADD CONSTRAINT consumoder_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE consumoder
    ADD CONSTRAINT consumoder_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE gimnasios
    ADD CONSTRAINT gimnasios_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE habitaciones
    ADD CONSTRAINT habitaciones_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE habitaciones
    ADD CONSTRAINT habitaciones_tiposhab_fk FOREIGN KEY ( tiposhab_id )
        REFERENCES tiposhab ( id );

ALTER TABLE internets
    ADD CONSTRAINT internets_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE lavanderias
    ADD CONSTRAINT lavanderias_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE maquinas
    ADD CONSTRAINT maquinas_gimnasios_fk FOREIGN KEY ( gimnasios_nombre )
        REFERENCES gimnasios ( nombre );

ALTER TABLE piscinas
    ADD CONSTRAINT piscinas_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE platos
    ADD CONSTRAINT platos_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE productos
    ADD CONSTRAINT productos_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE productos
    ADD CONSTRAINT productos_smercados_fk FOREIGN KEY ( smercados_nombre )
        REFERENCES smercados ( nombre );

ALTER TABLE productos
    ADD CONSTRAINT productos_tiendas_fk FOREIGN KEY ( tiendas_nombre )
        REFERENCES tiendas ( nombre );

ALTER TABLE reservas
    ADD CONSTRAINT reservas_planesconsumo_fk FOREIGN KEY ( planesconsumo_tipo )
        REFERENCES planesconsumo ( tipo );

ALTER TABLE reservas
    ADD CONSTRAINT reservas_usuarios_fk FOREIGN KEY ( usuarios_id )
        REFERENCES usuarios ( id );

ALTER TABLE reservasservicio
    ADD CONSTRAINT reservasservicio_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE reservasservicio
    ADD CONSTRAINT reservasservicio_salones_fk FOREIGN KEY ( salones_nombre )
        REFERENCES salones ( nombre );

ALTER TABLE reservasservicio
    ADD CONSTRAINT reservasservicio_spas_fk FOREIGN KEY ( spas_nombre )
        REFERENCES spas ( nombre );

ALTER TABLE reservasservicio
    ADD CONSTRAINT reservasservicio_utensilios_fk FOREIGN KEY ( utensilios_id )
        REFERENCES utensilios ( id );

ALTER TABLE restbebida
    ADD CONSTRAINT restbebida_bebidas_fk FOREIGN KEY ( bebidas_nombre )
        REFERENCES bebidas ( nombre );

ALTER TABLE restbebida
    ADD CONSTRAINT restbebida_rests_fk FOREIGN KEY ( rests_nombre )
        REFERENCES rests ( nombre );

ALTER TABLE restplato
    ADD CONSTRAINT restplato_platos_fk FOREIGN KEY ( platos_nombre )
        REFERENCES platos ( nombre );

ALTER TABLE restplato
    ADD CONSTRAINT restplato_rests_fk FOREIGN KEY ( rests_nombre )
        REFERENCES rests ( nombre );

ALTER TABLE usuarios
    ADD CONSTRAINT usuarios_tiposusuario_fk FOREIGN KEY ( tiposusuario_id )
        REFERENCES tiposusuario ( id );



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            27
-- CREATE INDEX                             0
-- ALTER TABLE                             59
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
