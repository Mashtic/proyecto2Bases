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

-- Prueba
DELETE FROM marcasplanta WHERE idEmpleado = 1;
ALTER TABLE marcasplanta AUTO_INCREMENT = 1;
CALL empleadoMarcarEntrada (1, '2023-11-01', '06:00:00', @e);
CALL empleadoMarcarEntrada (1, '2023-11-30', '06:00:00', @r);
SELECT @e, @r;
SELECT * FROM marcasplanta;

-- En caso de error
DROP PROCEDURE empleadoMarcarEntrada;