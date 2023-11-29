-- CREATE
DELIMITER $$
CREATE PROCEDURE `empleadoMarcarSalida` (
    IN idEmpleado INT,
    IN fecha DATE,
    IN hora TIME,
    OUT solucion TINYINT) -- 5: no tiene errores, puede marcar la salida
							-- 0: no existe empleado con ese id
							-- 1: empleado no está activo
							-- 2: no tiene marca ese día
                            -- 3: tiene salida ese día
                            -- 4: marca antes salida que entrada
BEGIN
	DECLARE existente INT;
    DECLARE estaActivo INT;
    DECLARE tieneMarca INT;
    DECLARE noTieneSalida INT;
    DECLARE entradaDespuesSalida INT;

    CALL empleadoExiste(idEmpleado, existente);
    CALL empleadoActivo(idEmpleado, estaActivo);
    CALL empleadoComprobarMarcaEnDia(idEmpleado, fecha, tieneMarca);
    CALL empleadoNoTieneSalida(idEmpleado, fecha, tieneMarca, noTieneSalida);
    CALL empleadoEntradaDespuesSalida(idEmpleado, fecha, hora, entradaDespuesSalida);

    IF existente = 0 THEN
        SET solucion = 0;
    ELSEIF estaActivo = 0 THEN
        SET solucion = 1;
    ELSEIF tieneMarca = 0 THEN
        SET solucion = 2;
	ELSEIF noTieneSalida = 0 THEN
        SET solucion = 3;
	ELSEIF entradaDespuesSalida = 0 THEN
        SET solucion = 4;
    ELSE
        SET solucion = 5;
        UPDATE marcasplanta AS M
        SET M.horaSalida = hora
		WHERE M.idEmpleado = idEmpleado AND M.fecha = fecha;
    END IF;
END$$
DELIMITER ;

-- CALL (prueba)
CALL empleadoMarcarSalida(100, '2023-04-11', '09:00:00', @solucion);
SELECT @solucion AS 'Cero';

UPDATE listaempleadosplanta SET activo = 0 WHERE Id = 1;
CALL empleadoMarcarSalida(1, '2023-04-11', '09:00:00', @solucion);
SELECT @solucion AS 'Uno';
UPDATE listaempleadosplanta SET activo = 1 WHERE Id = 1;

CALL empleadoMarcarSalida(1, '2023-11-20', '09:00:00', @solucion);
SELECT @solucion AS 'Dos';

CALL empleadoMarcarSalida(1, '2023-12-01', '10:00:00', @solucion);
SELECT @solucion AS 'Tres';

CALL empleadoMarcarSalida(1, '2023-11-28', '08:00:00', @solucion);
SELECT @solucion AS 'Cuatro';

CALL empleadoMarcarSalida(1, '2023-11-28', '12:00:00', @solucion);
SELECT @solucion AS 'Cinco';

-- DROP (eliminarla en caso de errores)
DROP PROCEDURE empleadoMarcarSalida;

-- Ver cambios
SELECT * from marcasplanta;
DELETE FROM marcasplanta WHERE id > 0;
ALTER TABLE marcasplanta AUTO_INCREMENT = 1;