-- CREATE
DELIMITER $$
CREATE PROCEDURE `empleadoNoTieneSalida` (
    IN idEmpleado INT,
    IN fecha DATE,
    IN diaConMarca INT, -- Si tiene (1) está bien
    OUT noTieneSalida boolean) -- 1 no tiene, 0 sí tiene
BEGIN
	IF (SELECT M.horaSalida FROM marcasplanta AS M
		WHERE M.idEmpleado = idEmpleado AND M.fecha = fecha) IS NULL 
        AND diaConMarca = 1 THEN
		SET noTieneSalida = true;
	ELSE
		SET noTieneSalida = false;
	END IF;
END$$
DELIMITER ;

-- CALL (prueba)
CALL empleadoNoTieneSalida(1, '2023-12-01', 0, @solucion);
SELECT @solucion AS 'Mal';
CALL empleadoNoTieneSalida(1, '2023-12-01', 1, @solucion);
SELECT @solucion AS 'Bien';

-- DROP (eliminarla en caso de errores)
DROP PROCEDURE empleadoNoTieneSalida;