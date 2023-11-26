
--Requerimiento 1: MOSTRAR EL DINERO RECOLECTADO POR SERVICIOS EN CADA HABITACIÓN EN EL ÚLTIMO AÑO CORRIDO

SELECT  /*+ USE_HASH(R, C, S) */
    H.ID AS Habitacion_ID, 
    S.DESCRIPCION, 
    SUM(S.PRECIO) AS Total_Precio  
FROM   
    HABITACION H  
    INNER JOIN RESERVAS R ON R.HABITACIONID = H.ID  
    INNER JOIN CONSUMODER C ON C.RESERVASID = R.ID  
    INNER JOIN SERVICIO S ON S.CONSUMOID = C.CONSUMOID 
WHERE   
    R.FECHAENTRADA >= TRUNC(SYSDATE) - INTERVAL '1' YEAR    
GROUP BY   
    H.ID, S.DESCRIPCION 
ORDER BY H.ID;

-- Req 2
SELECT s.id AS SERVICIOID, s.descripcion AS SERVICIODESCRIPCION, COUNT(c.id) AS CANTIDADCONSUMO 
FROM servicio s 
LEFT JOIN consumos c ON s.consumoid = c.id  
WHERE c.fecha BETWEEN TO_DATE(:fechainicial, 'yyyy-mm-dd') AND TO_DATE(:fechafinal, 'yyyy-mm-dd')  
GROUP BY s.id, s.descripcion 
ORDER BY COUNT(c.id) DESC FETCH FIRST 20 ROWS ONLY 

--Req 3
SELECT habitacion.id AS ID_HABITACION,
    ROUND(COALESCE(
    (SUM(
      CASE
        WHEN reservas.fechaentrada = reservas.fechasalida THEN 1
        WHEN reservas.fechaentrada < (TRUNC(SYSDATE) - 365) THEN (reservas.fechasalida - (TRUNC(SYSDATE) - 365)) + 1
        ELSE (reservas.fechasalida - reservas.fechaentrada) + 1
      END
    ) / 365.0) * 100,
    0
    ), 2) AS OCCUPANCY_PERCENTAGE
    FROM
      habitacion
    LEFT JOIN
      reservas
      ON habitacion.id = reservas.habitacionid
      AND reservas.fechaentrada >= TRUNC(SYSDATE) - 365
    GROUP BY
      habitacion.id;

-- Req4
SELECT id, descripcion, precio FROM SERVICIO 
WHERE precio BETWEEN :precioinicial AND :preciofinal 

-- Req4.1
SELECT s.id AS IDSERVICIO, s.descripcion AS DESCRIPCIONSERVICIO, c.fecha AS FECHA 
FROM SERVICIO s 
INNER JOIN CONSUMOS c ON s.consumoid = c.id 
WHERE c.fecha BETWEEN TO_DATE('2022-02-05', 'yyyy-mm-dd') AND TO_DATE('2022-02-10', 'yyyy-mm-dd') 

-- Req4.2
SELECT s.id AS IDSER, s.descripcion AS NOMBRESER, s.precio AS PRECIOSER FROM SERVICIO s 
WHERE descripcion =: tipo

-- req 5
SELECT 
    R.USUARIOSID AS USUARIO_ID, 
    S.DESCRIPCION AS TIPO_CONSUMO, 
    S.PRECIO AS COSTO  
FROM 
    CONSUMOS C 
    INNER JOIN SERVICIO S ON S.CONSUMOID = C.ID 
    INNER JOIN CONSUMODER CR ON CR.CONSUMOID = C.ID 
    INNER JOIN RESERVAS R ON CR.RESERVASID = R.ID 
WHERE 
    R.USUARIOSID = 12555 
    AND C.FECHA BETWEEN TO_DATE('1990-01-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') 
                    AND TO_DATE('2023-06-30 00:00:00', 'YYYY-MM-DD HH24:MI:SS');

SELECT index_name, table_name
FROM user_indexes;

--9) CONSULTAR CONSUMO EN HOTELANDES
CREATE INDEX nombre_servicio ON SERVICIO(DESCRIPCION);


-- OP1 ORDENAR POR DATOS DEL CLIENTE (NOMBRE): 

SELECT  /*+ USE_HASH(R, C, S) */
DISTINCT U.ID, U.NOMBRE, S.DESCRIPCION AS TIPO
FROM SERVICIO S
JOIN CONSUMOS C ON C.ID= S.CONSUMOID
JOIN CONSUMODER CR ON CR.CONSUMOID=C.ID
JOIN RESERVAS R ON R.ID=CR.RESERVASID
JOIN USUARIOS U ON U.ID= R.USUARIOSID
WHERE S.DESCRIPCION='Internet' AND C.FECHA BETWEEN '1/1/15' AND '31/12/20'
ORDER BY U.NOMBRE;

-- OP2 ORDENAR POR DATOS DEL CLIENTE (ID): 
SELECT /*+ USE_HASH(R, C, S) */
DISTINCT U.ID, U.NOMBRE, S.DESCRIPCION
FROM SERVICIO S
JOIN CONSUMOS C ON C.ID= S.CONSUMOID
JOIN CONSUMODER CR ON CR.CONSUMOID=C.ID
JOIN RESERVAS R ON R.ID=CR.RESERVASID
JOIN USUARIOS U ON U.ID= R.USUARIOSID
WHERE S.DESCRIPCION='SPA' AND C.FECHA BETWEEN '1/1/80' AND '31/12/10'
ORDER BY U.ID;

-- OP3 AGRUPAR POR FECHA Y NUMERO DE VECES UTILIZADO : 
SELECT /*+ USE_HASH(R, C, S) */
C.FECHA, S.DESCRIPCION AS SERVICIO, COUNT(*) AS NUMERO_VECES_UTILIZADO
FROM SERVICIO S
JOIN CONSUMOS C ON C.ID = S.CONSUMOID
JOIN CONSUMODER CR ON CR.CONSUMOID = C.ID
JOIN RESERVAS R ON R.ID = CR.RESERVASID
JOIN USUARIOS U ON U.ID = R.USUARIOSID
WHERE S.DESCRIPCION = 'SPA' AND C.FECHA BETWEEN '1/1/80' AND '31/12/10'
GROUP BY C.FECHA, S.DESCRIPCION;

--Req6
SELECT TRUNC(CONSUMOS.FECHA), SUM(S.PRECIO) AS Total_del_dia  , COUNT(DISTINCT R.ID ) AS OCUPACION
FROM RESERVAS R 
INNER JOIN CONSUMODER C ON R.ID = C.RESERVASID
INNER JOIN CONSUMOS ON CONSUMOS.ID= C.CONSUMOID
INNER JOIN SERVICIO S ON S.CONSUMOID = CONSUMOS.ID 
GROUP BY TRUNC(CONSUMOS.FECHA)
ORDER BY OCUPACION desc;

--Req7
SELECT U.id, U.nombre, SUM(S.PRECIO) AS Justificacion
FROM USUARIOS U
INNER JOIN Reservas R ON U.id = R.usuariosid
INNER JOIN consumoder CR ON R.id = CR.reservasid
INNER JOIN consumos C ON C.id = CR.consumoid
INNER JOIN servicio S ON S.consumoid = C.id
WHERE U.TIPOID = 2 AND R.fechaentrada >= trunc(sysdate) - 365
GROUP BY U.id, U.nombre
HAVING Sum(s.precio) > 1000000
UNION
SELECT id, nombre, duracion_total_estadia
FROM (
    SELECT usuarios.id, usuarios.nombre, SUM(reservas.fechasalida - reservas.fechaentrada) AS duracion_total_estadia
    FROM usuarios JOIN reservas ON usuarios.id = reservas.usuariosid WHERE usuarios.tipoid= 2 AND reservas.fechaentrada >= trunc(sysdate) - 365
    GROUP BY usuarios.id, usuarios.nombre
    )
    WHERE duracion_total_estadia >= 14
;


--RFC10 - 
--/*+ USE_HASH(R, C, S) */

-- OP1 ORDENAR POR DATOS DEL CLIENTE (NOMBRE): 
SELECT /*+ USE_HASH(R, C, S) */
DISTINCT U.ID, U.NOMBRE, S.DESCRIPCION
FROM SERVICIO S
JOIN CONSUMOS C ON C.ID= S.CONSUMOID
JOIN CONSUMODER CR ON CR.CONSUMOID=C.ID
JOIN RESERVAS R ON R.ID=CR.RESERVASID
JOIN USUARIOS U ON U.ID= R.USUARIOSID
WHERE S.DESCRIPCION !='SPA' AND C.FECHA BETWEEN '1/1/80' AND '31/12/10'
ORDER BY U.NOMBRE;

-- OP2 ORDENAR POR DATOS DEL CLIENTE (ID): 
SELECT /*+ USE_HASH(R, C, S) */
DISTINCT U.ID, U.NOMBRE, S.DESCRIPCION
FROM SERVICIO S
JOIN CONSUMOS C ON C.ID= S.CONSUMOID
JOIN CONSUMODER CR ON CR.CONSUMOID=C.ID
JOIN RESERVAS R ON R.ID=CR.RESERVASID
JOIN USUARIOS U ON U.ID= R.USUARIOSID
WHERE S.DESCRIPCION!='SPA' AND C.FECHA BETWEEN '1/1/80' AND '31/12/10'
ORDER BY U.ID;

-- OP3 AGRUPAR POR FECHA Y NUMERO DE VECES UTILIZADO : 
SELECT C.FECHA, S.DESCRIPCION AS SERVICIO, COUNT(*) AS NUMERO_VECES_UTILIZADO
FROM SERVICIO S
JOIN CONSUMOS C ON C.ID = S.CONSUMOID
JOIN CONSUMODER CR ON CR.CONSUMOID = C.ID
JOIN RESERVAS R ON R.ID = CR.RESERVASID
JOIN USUARIOS U ON U.ID = R.USUARIOSID
WHERE S.DESCRIPCION! = 'SPA' AND C.FECHA BETWEEN '1/1/80' AND '31/12/10'
GROUP BY C.FECHA, S.DESCRIPCION;

-- Req 8
SELECT s.id AS SERVICIOID, s.descripcion AS SERVICIODESCRIPCION, COUNT(c.id) AS CANTIDADCONSUMO  
FROM servicio s 
LEFT JOIN consumos c ON s.consumoid = c.id  
WHERE c.fecha BETWEEN SYSDATE - 365 AND SYSDATE  
GROUP BY s.id, s.descripcion  HAVING COUNT(c.id) < 3   
ORDER BY s.id, s.descripcion 

--Req 11a
SELECT 
    YearWeek,
    habitacionid,
    RoomBookings
FROM (
    SELECT 
        TO_CHAR(TRUNC(reservas.fechaentrada, 'IW'), 'IYYY-IW') AS YearWeek, 
        reservas.habitacionid,
        COUNT(*) AS RoomBookings,
        RANK() OVER (PARTITION BY TO_CHAR(TRUNC(reservas.fechaentrada, 'IW'), 'IYYY-IW') ORDER BY COUNT(*) DESC) as rnk
    FROM 
        reservas 
    WHERE 
        reservas.fechaentrada >= ADD_MONTHS(TRUNC(SYSDATE, 'YY'), -12)
        AND reservas.fechaentrada < TRUNC(SYSDATE, 'YY')
    GROUP BY 
        TO_CHAR(TRUNC(reservas.fechaentrada, 'IW'), 'IYYY-IW'), 
        reservas.habitacionid
) 
WHERE 
    rnk = 1
ORDER BY 
    YearWeek;
--Req 11b
SELECT 
    YearWeek,
    id AS ID_CONSUMO,
    ServiceConsumptions
FROM (
    SELECT 
        TO_CHAR(TRUNC(reservas.fechaentrada, 'IW'), 'IYYY-IW') AS YearWeek, 
        servicio.id,
        COUNT(*) AS ServiceConsumptions,
        RANK() OVER (PARTITION BY TO_CHAR(TRUNC(reservas.fechaentrada, 'IW'), 'IYYY-IW') ORDER BY COUNT(*) DESC) as rnk
    FROM 
        reservas
        JOIN consumoder ON reservas.id = consumoder.reservasid
        JOIN consumos ON consumoder.consumoid = consumos.id
        JOIN servicio ON consumos.id = servicio.consumoid
    WHERE 
        reservas.fechaentrada >= ADD_MONTHS(TRUNC(SYSDATE, 'YY'), -12)
        AND reservas.fechaentrada < TRUNC(SYSDATE, 'YY')
    GROUP BY 
        TO_CHAR(TRUNC(reservas.fechaentrada, 'IW'), 'IYYY-IW'), 
        servicio.id
) 
WHERE 
    rnk = 1
ORDER BY 
    YearWeek, SERVICECONSUMPTIONS;

-- Req 12
SELECT DISTINCT u.id AS IDUSUARIO, u.nombre AS NOMBREUSUARIO, u.cedula AS CEDULAUSUARIO 
FROM usuarios u  
JOIN reservas r ON u.id = r.usuariosid 
JOIN consumoder cd ON r.id = cd.reservasid  
JOIN consumos c ON cd.reservasid = c.id 
JOIN reservasservicio rs ON c.id = rs.consumoid 
JOIN tiposusuario tu ON u.tipoid = tu.id 
WHERE tu.tipo='cliente' AND ( 
    (r.fechaentrada >= TRUNC(SYSDATE, 'Q') AND r.fechaentrada <= SYSDATE) 
    OR EXISTS ( 
        SELECT 1 
        FROM reservasservicio rss 
        WHERE c.id = rss.consumoid AND rss.precio > 300000 
    ) 
    OR ( 
        rs.descripcion IN ('SPA', 'Salones de reuniones') 
        AND (rs.fechayhoraf - rs.fechayhorai) > 4/24 
    ) 
) 
OR EXISTS ( 
        SELECT 1 
        FROM reservas r2 
        WHERE u.id = r2.usuariosid 
        AND MONTHS_BETWEEN(TRUNC(SYSDATE), TRUNC(r2.fechaentrada)) <= 12 
        AND (r2.fechasalida - r2.fechaentrada) <= 90 
    ) 
GROUP BY u.nombre, u.id, u.cedula; 