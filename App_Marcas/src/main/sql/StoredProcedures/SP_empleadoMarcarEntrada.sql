-- CREATE
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
        INSERT INTO marcasplanta (idEmpleado, fecha, horaEntrada) 
        VALUES (idEmpleado, fecha, hora);
    END IF;
END $$
$$
DELIMITER ;

-- CALL (prueba)
CALL empleadoMarcarEntrada(100, '2023-04-11', '09:00:00', @solucion);
SELECT @solucion AS 'Cero';

UPDATE listaempleadosplanta SET activo = 0 WHERE Id = 1;
CALL empleadoMarcarEntrada(1, '2023-04-11', '09:00:00', @solucion);
SELECT @solucion AS 'Uno';
UPDATE listaempleadosplanta SET activo = 1 WHERE Id = 1;

CALL empleadoMarcarEntrada(1, '2023-11-26', '09:00:00', @solucion);
SELECT @solucion AS 'Dos';

UPDATE listaempleadosplanta SET calendario = 1 WHERE Id = 1;
CALL empleadoMarcarEntrada(1, '2022-01-01', '09:00:00', @solucion); -- Es feriado no lab
SELECT @solucion AS 'Tres';
UPDATE listaempleadosplanta SET calendario = 3 WHERE Id = 1;

CALL empleadoMarcarEntrada(1, '2023-11-28', '08:00:00', @solucion);
SELECT @solucion AS 'Cuatro';

CALL empleadoMarcarEntrada(1, '2023-12-04', '09:00:00', @solucion);
SELECT @solucion AS 'Cinco';

-- DROP (eliminarla en caso de errores)
DROP PROCEDURE empleadoMarcarEntrada;

-- Ver cambios
SELECT * from marcasplanta;
DELETE FROM marcasplanta WHERE id > 0;
ALTER TABLE marcasplanta AUTO_INCREMENT = 1;