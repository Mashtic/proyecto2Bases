-- CREATE
DELIMITER $$
CREATE PROCEDURE `empleadoTrabajaDiaSemana` (
    IN idEmpleado INT,
    IN fecha DATE,
    OUT debeTrabajar BOOLEAN) -- 1 trabaja, 0 es feriado de semana (error)
BEGIN
    DECLARE jornadaDias VARCHAR(10);
    SET jornadaDias = (SELECT J.letrasJornada FROM listaempleadosplanta AS L 
						INNER JOIN tipocalendarios AS T ON L.calendario = T.id
						INNER JOIN jornadasLaborales AS J ON T.jornada = J.id
                        WHERE L.Id = idEmpleado);
	
    SET debeTrabajar = (SELECT obtenerDiaSemanaTrabajable (jornadaDias, fecha));
    
END$$
DELIMITER ;

-- CALL (prueba)
CALL empleadoTrabajaDiaSemana(1, '2023-11-24', @debeTrabajar);
SELECT @debeTrabajar AS 'Viernes_si';
CALL empleadoTrabajaDiaSemana(1, '2023-11-25', @debeTrabajar);
SELECT @debeTrabajar AS 'Sabado_no';
CALL empleadoTrabajaDiaSemana(1, '2023-11-26', @debeTrabajar);
SELECT @debeTrabajar AS 'Domingo_no';
CALL empleadoTrabajaDiaSemana(1, '2023-11-27', @debeTrabajar);
SELECT @debeTrabajar AS 'Lunes_si';

-- DROP (eliminarla en caso de errores)
DROP PROCEDURE empleadoTrabajaDiaSemana;