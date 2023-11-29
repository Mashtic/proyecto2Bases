-- CREATE
DELIMITER $$
CREATE FUNCTION obtenerNumDiaSemana (
    fecha DATE
)
RETURNS INT -- 1: lunes, 2: martes, ..., 7: domingo
BEGIN
    DECLARE diaNum INT;
    
    SET diaNum = (SELECT DAYOFWEEK(fecha)-1);
    
    IF diaNum = 0 THEN 
		SET diaNum = 7;
	END IF;

    RETURN diaNum;
END $$
DELIMITER ;

-- SELECT (prueba)
SELECT obtenerNumDiaSemana('2023-11-20') AS L, 
	obtenerNumDiaSemana('2023-11-21') AS K, 
    obtenerNumDiaSemana('2023-11-22') AS M,
    obtenerNumDiaSemana('2023-11-23') AS J, 
    obtenerNumDiaSemana('2023-11-24') AS V,
    obtenerNumDiaSemana('2023-11-25') AS S, 
    obtenerNumDiaSemana('2023-11-26') AS D; -- Lunes, martes, ..., domingo

-- DROP (en caso de necesitar cambiarla)
DROP FUNCTION obtenerNumDiaSemana;