-- CREATE
DELIMITER $$
CREATE PROCEDURE `empleadoComprobarMarcaEnDia` (
    IN idEmpleado INT,
    IN fecha DATE,
    OUT tieneMarca BOOLEAN) -- 1 sí, 0 no
							 -- (si tiene en entrada da error)
                             -- (si no tiene en salida da error)
BEGIN
    IF fecha IN (SELECT M.fecha FROM marcasplanta AS M
				  WHERE M.idEmpleado = idEmpleado AND M.fecha = fecha) THEN -- Da la marca del día y empleado
		SET tieneMarca = true;
	ELSE
		SET tieneMarca = false;
	END IF;
END$$
DELIMITER ;

-- CALL (prueba)
CALL empleadoComprobarMarcaEnDia(1, '2023-11-28', @tieneMarca);
SELECT @tieneMarca AS 'Tiene_marca';
CALL empleadoComprobarMarcaEnDia(100, '2023-11-28', @tieneMarca);
SELECT @tieneMarca AS 'No_existe_id';
CALL empleadoComprobarMarcaEnDia(1, '2023-11-30', @tieneMarca);
SELECT @tieneMarca AS 'No_tiene_marca';

-- DROP (eliminarla en caso de errores)
DROP PROCEDURE empleadoComprobarMarcaEnDia;