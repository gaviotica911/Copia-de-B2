-- Generado por Oracle SQL Developer Data Modeler 23.1.0.087.0806
--   en:        2023-10-01 18:20:36 COT
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
    nombre       VARCHAR2(255) NOT NULL,
    precio       NUMBER(13, 2) NOT NULL,
    todoincluido CHAR(1) NOT NULL
);

ALTER TABLE bebidas ADD CONSTRAINT bebidas_pk PRIMARY KEY ( nombre );

CREATE TABLE consumobebida (
    consumos_idconsumo INTEGER NOT NULL,
    bebidas_nombre     VARCHAR2(255) NOT NULL
);

ALTER TABLE consumobebida ADD CONSTRAINT consumobebida_pk PRIMARY KEY ( consumos_idconsumo,
                                                                        bebidas_nombre );

CREATE TABLE consumos (
    preciototal        NUMBER(13, 2) NOT NULL,
    planesconsumo_tipo VARCHAR2(255),
    pazysalvo          CHAR(1) NOT NULL,
    idconsumo          INTEGER NOT NULL
);

CREATE UNIQUE INDEX consumos__idx ON
    consumos (
        planesconsumo_tipo
    ASC );

ALTER TABLE consumos ADD CONSTRAINT consumos_pk PRIMARY KEY ( idconsumo );

CREATE TABLE gimnasios (
    nombre             VARCHAR2(255) NOT NULL,
    horarioapertura    DATE,
    horariocierre      DATE,
    capacidad          INTEGER NOT NULL,
    consumos_idconsumo INTEGER
);

ALTER TABLE gimnasios ADD CONSTRAINT gimnasios_pk PRIMARY KEY ( nombre );

CREATE TABLE habitaciones (
    id_habitacion       INTEGER NOT NULL,
    capacidad           INTEGER NOT NULL,
    reservas_id_reserva INTEGER,
    tiposhab_tipo       VARCHAR2(255) NOT NULL
);

ALTER TABLE habitaciones ADD CONSTRAINT habitaciones_pk PRIMARY KEY ( id_habitacion );

CREATE TABLE internets (
    capacidad          INTEGER NOT NULL,
    precio             NUMBER(13, 2),
    consumos_idconsumo INTEGER NOT NULL
);

ALTER TABLE internets ADD CONSTRAINT internets_pk PRIMARY KEY ( capacidad,
                                                                consumos_idconsumo );

CREATE TABLE lavanderias (
    numprendas         INTEGER NOT NULL,
    numzapatos         INTEGER NOT NULL,
    tipolavado         VARCHAR2(255) NOT NULL,
    costo              NUMBER(13, 2) NOT NULL,
    idlavanderia       INTEGER NOT NULL,
    consumos_idconsumo INTEGER
);

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
    consumos_idconsumo INTEGER NOT NULL
);

ALTER TABLE piscinas ADD CONSTRAINT piscinas_pk PRIMARY KEY ( nombre );

CREATE TABLE planesconsumo (
    tipo                VARCHAR2(255) NOT NULL,
    descuento           INTEGER,
    descripcion         VARCHAR2(255) NOT NULL,
    descuentobar        CHAR(1),
    descuentorest       CHAR(1),
    descuentospa        CHAR(1),
    limitebebidas       INTEGER,
    descuentolavado     CHAR(1),
    reservas_id_reserva INTEGER,
    consumos_idconsumo  INTEGER
);

CREATE UNIQUE INDEX planesconsumo__idx ON
    planesconsumo (
        reservas_id_reserva
    ASC );

CREATE UNIQUE INDEX planesconsumo__idxv1 ON
    planesconsumo (
        consumos_idconsumo
    ASC );

ALTER TABLE planesconsumo ADD CONSTRAINT planesconsumo_pk PRIMARY KEY ( tipo );

CREATE TABLE platos (
    nombre       VARCHAR2(255) NOT NULL,
    precio       NUMBER(13, 2) NOT NULL,
    todoincluido CHAR(1) NOT NULL
);

ALTER TABLE platos ADD CONSTRAINT platos_pk PRIMARY KEY ( nombre );

CREATE TABLE platosconsumidos (
    consumos_idconsumo INTEGER NOT NULL,
    platos_nombre      VARCHAR2(255) NOT NULL
);

ALTER TABLE platosconsumidos ADD CONSTRAINT platosconsumidos_pk PRIMARY KEY ( consumos_idconsumo,
                                                                              platos_nombre );

CREATE TABLE prestamosut (
    tipo               VARCHAR2(255) NOT NULL,
    cantidad           INTEGER NOT NULL,
    estado             CHAR(1) NOT NULL,
    precio             NUMBER(13, 2),
    idutensilio        INTEGER NOT NULL,
    consumos_idconsumo INTEGER
);

ALTER TABLE prestamosut ADD CONSTRAINT prestamosut_pk PRIMARY KEY ( idutensilio );

CREATE TABLE productoconsumo (
    productos_nombre   VARCHAR2(255) NOT NULL,
    consumos_idconsumo INTEGER NOT NULL
);

ALTER TABLE productoconsumo ADD CONSTRAINT productoconsumo_pk PRIMARY KEY ( productos_nombre,
                                                                            consumos_idconsumo );

CREATE TABLE productos (
    nombre           VARCHAR2(255) NOT NULL,
    precio           NUMBER(13, 2) NOT NULL,
    smercados_nombre VARCHAR2(255),
    tiendas_nombre   VARCHAR2(255)
);

ALTER TABLE productos ADD CONSTRAINT productos_pk PRIMARY KEY ( nombre );

CREATE TABLE reservas (
    id_reserva         INTEGER NOT NULL,
    fechaentrada       DATE NOT NULL,
    fechasalida        DATE NOT NULL,
    numpersonas        INTEGER NOT NULL,
    estado             CHAR(1) NOT NULL,
    planesconsumo_tipo VARCHAR2(255),
    precioreserva      NUMBER(13, 2) NOT NULL,
    usuarios_id        INTEGER NOT NULL
);

CREATE UNIQUE INDEX reservas__idx ON
    reservas (
        planesconsumo_tipo
    ASC );

ALTER TABLE reservas ADD CONSTRAINT reservas_pk PRIMARY KEY ( id_reserva );

CREATE TABLE reservassalon (
    fecha          DATE,
    duracion       INTEGER,
    idreserva      INTEGER NOT NULL,
    salones_nombre VARCHAR2(255) NOT NULL
);

ALTER TABLE reservassalon ADD CONSTRAINT reservassalon_pk PRIMARY KEY ( idreserva );

CREATE TABLE reservasspa (
    fecha       DATE,
    duracion    INTEGER,
    spas_nombre VARCHAR2(255),
    idreserva   INTEGER NOT NULL
);

ALTER TABLE reservasspa ADD CONSTRAINT reservasspa_pk PRIMARY KEY ( idreserva );

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
    tiposalon          VARCHAR2(255) NOT NULL,
    ocupado            CHAR(1) NOT NULL,
    horarioapertura    DATE,
    horariocierre      DATE,
    precio             NUMBER(13, 2) NOT NULL,
    nombre             VARCHAR2(255) NOT NULL,
    capacidad          INTEGER,
    consumos_idconsumo INTEGER
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
    nombre             VARCHAR2(255) NOT NULL,
    horarioapertura    DATE,
    horariocierre      DATE,
    capacidad          INTEGER NOT NULL,
    consumos_idconsumo INTEGER,
    precio             NUMBER NOT NULL
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
    descripcion VARCHAR2(255) NOT NULL
);

ALTER TABLE tiposhab
    ADD CHECK ( tipo IN ( 'DOBLE', 'FAMILIAR', 'PRESIDENCIAL', 'SENCILLA', 'SUITE' ) );

ALTER TABLE tiposhab ADD CONSTRAINT tiposhab_pk PRIMARY KEY ( tipo );

CREATE TABLE usuarios (
    id     INTEGER NOT NULL,
    nombre VARCHAR2(255) NOT NULL,
    cedula VARCHAR2(255) NOT NULL,
    tipo   VARCHAR2(255) NOT NULL
);

ALTER TABLE usuarios
    ADD CHECK ( tipo IN ( 'ADMINISTRADOR', 'CLIENTE', 'EMPLEADO', 'GERENTE', 'RECEPCIONISTA' ) );

ALTER TABLE usuarios ADD CONSTRAINT usuarios_pk PRIMARY KEY ( id );

CREATE TABLE utensilios (
    id                      INTEGER NOT NULL,
    nombre                  VARCHAR2(255 BYTE) NOT NULL,
    prestamosut_idutensilio INTEGER NOT NULL
);

ALTER TABLE utensilios ADD CONSTRAINT utensilios_pk PRIMARY KEY ( id );

ALTER TABLE barbebida
    ADD CONSTRAINT barbebida_bares_fk FOREIGN KEY ( bares_nombre )
        REFERENCES bares ( nombre );

ALTER TABLE barbebida
    ADD CONSTRAINT barbebida_bebidas_fk FOREIGN KEY ( bebidas_nombre )
        REFERENCES bebidas ( nombre );

ALTER TABLE consumobebida
    ADD CONSTRAINT consumobebida_bebidas_fk FOREIGN KEY ( bebidas_nombre )
        REFERENCES bebidas ( nombre );

ALTER TABLE consumobebida
    ADD CONSTRAINT consumobebida_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE consumos
    ADD CONSTRAINT consumos_planesconsumo_fk FOREIGN KEY ( planesconsumo_tipo )
        REFERENCES planesconsumo ( tipo );

ALTER TABLE gimnasios
    ADD CONSTRAINT gimnasios_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE habitaciones
    ADD CONSTRAINT habitaciones_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE habitaciones
    ADD CONSTRAINT habitaciones_tiposhab_fk FOREIGN KEY ( tiposhab_tipo )
        REFERENCES tiposhab ( tipo );

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

ALTER TABLE planesconsumo
    ADD CONSTRAINT planesconsumo_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE planesconsumo
    ADD CONSTRAINT planesconsumo_reservas_fk FOREIGN KEY ( reservas_id_reserva )
        REFERENCES reservas ( id_reserva );

ALTER TABLE platosconsumidos
    ADD CONSTRAINT platosconsumidos_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE platosconsumidos
    ADD CONSTRAINT platosconsumidos_platos_fk FOREIGN KEY ( platos_nombre )
        REFERENCES platos ( nombre );

ALTER TABLE prestamosut
    ADD CONSTRAINT prestamosut_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE productoconsumo
    ADD CONSTRAINT productoconsumo_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE productoconsumo
    ADD CONSTRAINT productoconsumo_productos_fk FOREIGN KEY ( productos_nombre )
        REFERENCES productos ( nombre );

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

ALTER TABLE reservassalon
    ADD CONSTRAINT reservassalon_salones_fk FOREIGN KEY ( salones_nombre )
        REFERENCES salones ( nombre );

ALTER TABLE reservasspa
    ADD CONSTRAINT reservasspa_spas_fk FOREIGN KEY ( spas_nombre )
        REFERENCES spas ( nombre );

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

ALTER TABLE salones
    ADD CONSTRAINT salones_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE spas
    ADD CONSTRAINT spas_consumos_fk FOREIGN KEY ( consumos_idconsumo )
        REFERENCES consumos ( idconsumo );

ALTER TABLE utensilios
    ADD CONSTRAINT utensilios_prestamosut_fk FOREIGN KEY ( prestamosut_idutensilio )
        REFERENCES prestamosut ( idutensilio );



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            30
-- CREATE INDEX                             4
-- ALTER TABLE                             66
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
