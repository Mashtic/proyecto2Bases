-- CREATE
DELIMITER $$
CREATE FUNCTION obtenerNumDiaSemanaIni (
    inicialD VARCHAR(1)
)
RETURNS INT
BEGIN
    DECLARE diaNum INT;
    
    CASE inicialD
        WHEN 'L' THEN SET diaNum = 1;
        WHEN 'K' THEN SET diaNum = 2;
        WHEN 'M' THEN SET diaNum = 3;
        WHEN 'J' THEN SET diaNum = 4;
        WHEN 'V' THEN SET diaNum = 5;
        WHEN 'S' THEN SET diaNum = 6;
        ELSE SET diaNum = 7;
    END CASE;

    RETURN diaNum;
END $$
DELIMITER ;

-- SELECT (prueba)
SELECT obtenerNumDiaSemanaIni('L') AS L, 
	obtenerNumDiaSemanaIni('K') AS K, 
    obtenerNumDiaSemanaIni('D') AS D; -- Lunes, martes, ..., domingo
    
-- DROP (en caso de necesitar cambiarla)
DROP FUNCTION obtenerNumDiaSemanaIni;