-- CREATE
DELIMITER $$
CREATE FUNCTION obtenerDiaSemanaTrabajable (
	diasJornada VARCHAR(10),
    fechaEvaluar DATE)
RETURNS BOOLEAN
BEGIN
	DECLARE diaIni INT;
    DECLARE diaFin INT;
    DECLARE diaEvaluar INT;
    
    SET diaIni = obtenerNumDiaSemanaIni(LEFT(diasJornada, 1));
    SET diaFin = obtenerNumDiaSemanaIni(RIGHT(diasJornada, 1));
	SET diaEvaluar = obtenerNumDiaSemana(fechaEvaluar);
    
    IF diaIni <= diaEvaluar AND diaEvaluar <= diaFin THEN
		RETURN true;
	ELSE
		RETURN false;
	END IF;
    
END$$
DELIMITER ;

-- SELECT (prueba)
SELECT obtenerDiaSemanaTrabajable('L-V', '2023-11-20') AS L_si, 
	obtenerDiaSemanaTrabajable('K-S', '2023-11-20') AS L_no, 
    obtenerDiaSemanaTrabajable('L-D', '2023-11-26') AS D_si;

-- DROP (en caso de necesitar cambiarla)
DROP FUNCTION obtenerDiaSemanaTrabajable;