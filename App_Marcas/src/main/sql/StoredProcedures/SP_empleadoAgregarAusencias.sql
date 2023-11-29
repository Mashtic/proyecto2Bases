DELIMITER $$
CREATE PROCEDURE `empleadoAgregarAusencias` (
    IN idEmpleado INT,
    IN fechaEntrada DATE)
BEGIN
	DECLARE fechaUltima DATE;
    DECLARE debeTrabajar INT;
    DECLARE esFeriadoLab INT;
    DECLARE puedeMarcar INT;
    SET fechaUltima = (SELECT MAX(M.fecha) FROM marcasplanta AS M
						GROUP BY M.idEmpleado
						HAVING M.idEmpleado = idEmpleado);
	SET fechaUltima = (SELECT date_add(fechaUltima, interval 1 day));
	
    
    loopAgregarAusencias: WHILE fechaUltima < fechaEntrada DO
		CALL empleadoTrabajaDiaSemana(idEmpleado, fechaUltima, debeTrabajar);
		CALL empleadoEnFeriadoLaborable(idEmpleado, fechaUltima, esFeriadoLab);
		CALL empleadoComprobarMarcaEnDia(idEmpleado, fechaUltima, puedeMarcar);
		IF (debeTrabajar = 1 AND esFeriadoLab = 1 AND puedeMarcar = 0) THEN
			INSERT INTO marcasplanta (idEmpleado, fecha) 
			VALUES (idEmpleado, fechaUltima);
            ITERATE loopAgregarAusencias;
		END IF;
		SET fechaUltima = date_add(fechaUltima, interval 1 day);
	END WHILE loopAgregarAusencias;
    
END$$
DELIMITER ;

-- Prueba
DELETE FROM marcasplanta WHERE idEmpleado = 1;
ALTER TABLE marcasplanta AUTO_INCREMENT = 1;
INSERT INTO marcasplanta (idEmpleado, fecha) 
VALUES (1, '2023-11-01');
CALL empleadoAgregarAusencias (1, '2023-11-30');
SELECT * FROM marcasplanta;

-- En caso de error
DROP PROCEDURE empleadoAgregarAusencias;