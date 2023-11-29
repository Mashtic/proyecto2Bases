-- CREATE
DELIMITER $$
CREATE PROCEDURE `empleadoActivo` (
    IN idEmpleado INT,
    OUT estaActivo BOOLEAN) -- 0 innactivo (error), 1 activo
BEGIN
    SET estaActivo = (
        SELECT l.activo
        FROM listaempleadosplanta AS l
        WHERE l.Id = idEmpleado);
        
	 IF estaActivo IS NULL THEN
        SET estaActivo = 0;
    END IF;
END$$
DELIMITER ;

-- CALL (prueba)
CALL empleadoActivo (1, @estaActivo);
SELECT @estaActivo AS 'Activo';
CALL empleadoActivo (100, @estaActivo);
SELECT @estaActivo AS 'No activo';

-- DROP (eliminarla en caso de errores)
DROP PROCEDURE empleadoActivo;