-- CREATE
DELIMITER $$
CREATE PROCEDURE `empleadoEntradaDespuesSalida` (
    IN idEmpleado INT,
    IN fecha DATE,
    IN horaSalida TIME,
    OUT despuesDeEntrada boolean) -- 1 Marca salida despúes de entrar, 
								  -- 0 marca antes de entrar
BEGIN
	DECLARE horaEntrada TIME;
    SET horaEntrada = (SELECT M.horaEntrada FROM marcasplanta AS M
					  WHERE M.idEmpleado = idEmpleado AND M.fecha = fecha);
                      
	IF horaEntrada IS NULL THEN
		SET despuesDeEntrada = false;
	ELSEIF horaEntrada < horaSalida THEN
		SET despuesDeEntrada = true;
	ELSE
		SET despuesDeEntrada = false;
	END IF;
END$$
DELIMITER ;

-- CALL (prueba)
CALL empleadoEntradaDespuesSalida(1, '2023-12-01', '03:00:00', @solucion);
SELECT @solucion AS 'Mal';
CALL empleadoEntradaDespuesSalida(1, '2023-12-01', '12:00:00', @solucion);
SELECT @solucion AS 'Bien';

-- DROP (eliminarla en caso de errores)
DROP PROCEDURE empleadoEntradaDespuesSalida;