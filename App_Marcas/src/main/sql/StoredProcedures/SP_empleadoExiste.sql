-- CREATE
DELIMITER $$
CREATE PROCEDURE `empleadoExiste` (
    IN idEmpleado INT,
    OUT existente BOOLEAN) -- 0 no existe (error), 1 existe
BEGIN
    IF idEmpleado IN (SELECT l.Id FROM listaempleadosplanta AS l
					  WHERE l.Id = idEmpleado) THEN 
        SET existente = true;
	ELSE
		SET existente = false;
    END IF;
END$$
DELIMITER ;

-- CALL (prueba)
CALL empleadoExiste (1, @existente);
SELECT @existente AS 'Existe';
CALL empleadoExiste (100, @existente);
SELECT @existente AS 'No existe';

-- DROP (eliminarla en caso de errores)
DROP PROCEDURE empleadoExiste;