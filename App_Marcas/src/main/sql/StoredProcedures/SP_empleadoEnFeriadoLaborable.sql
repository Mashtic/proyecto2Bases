-- CREATE
DELIMITER $$
CREATE PROCEDURE `empleadoEnFeriadoLaborable` (
    IN idEmpleado INT,
    IN fecha DATE,
    OUT esFeriadoLab BOOLEAN) -- 1 es feriado laborable o no es feriado, 
							  -- 0 es feriado no laborable (error)
BEGIN
    IF DATE_FORMAT(fecha, '%m-%d') IN (SELECT DATE_FORMAT(D.fecha, '%m-%d') FROM listaempleadosplanta AS L 
				 INNER JOIN tipocalendarios AS T ON L.calendario = T.id
				 INNER JOIN diasFeriados AS D ON T.id = D.calendario
				  WHERE L.Id = idEmpleado AND D.esLaborable = 0) THEN -- Solo no laborables
		SET esFeriadoLab = false;
	ELSE
		SET esFeriadoLab = true;
	END IF;
    
END$$
DELIMITER ;

-- CALL (prueba)
CALL empleadoEnFeriadoLaborable(1, '2023-04-11', @esFeriadoLab);
SELECT @esFeriadoLab AS 'Feriado_laborable_si';
CALL empleadoEnFeriadoLaborable(1, '2023-11-27', @esFeriadoLab);
SELECT @esFeriadoLab AS 'No_feriado_lunes_si';

-- DROP (eliminarla en caso de errores)
DROP PROCEDURE empleadoEnFeriadoLaborable;