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

DROP PROCEDURE empleadoMarcarEntrada;

DELIMITER $$
CREATE PROCEDURE `empleadoMarcarEntrada` (
    IN idEmpleado INT,
    IN fecha DATE,
    IN hora TIME,
    OUT solucion TINYINT)	-- 5: no tiene errores, puede marcar
							-- 0: no existe empleado con ese id
							-- 1: empleado no está activo
                            -- 2: no debe trabajar ese día de semana
							-- 3: no debe trabajar por feriado no laborable
							-- 4: tiene otra marca ese día
BEGIN
    DECLARE existente INT;
    DECLARE estaActivo INT;
    DECLARE debeTrabajar INT;
    DECLARE esFeriadoLab INT;
    DECLARE puedeMarcar INT;
    
    CALL empleadoExiste(idEmpleado, existente);
    CALL empleadoActivo(idEmpleado, estaActivo);
    CALL empleadoTrabajaDiaSemana(idEmpleado, fecha, debeTrabajar);
    CALL empleadoEnFeriadoLaborable(idEmpleado, fecha, esFeriadoLab);
    CALL empleadoComprobarMarcaEnDia(idEmpleado, fecha, puedeMarcar);

    IF existente = 0 THEN
        SET solucion = 0;
    ELSEIF estaActivo = 0 THEN
        SET solucion = 1;
    ELSEIF debeTrabajar = 0 THEN
        SET solucion = 2;
    ELSEIF esFeriadoLab = 0 THEN
        SET solucion = 3;
    ELSEIF puedeMarcar = 1 THEN
        SET solucion = 4;
    ELSE
        SET solucion = 5;
        CALL empleadoAgregarAusencias (idEmpleado, fecha);
        INSERT INTO marcasplanta (idEmpleado, fecha, horaEntrada) 
        VALUES (idEmpleado, fecha, hora);
    END IF;
END $$
$$
DELIMITER ;