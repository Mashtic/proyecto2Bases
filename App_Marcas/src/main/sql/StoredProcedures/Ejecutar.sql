DELIMITER $$
CREATE FUNCTION obtenerNumDiaSemana (
    fecha DATE
)
RETURNS INT -- 1: lunes, 2: martes, ..., 7: domingo
DETERMINISTIC
BEGIN
    DECLARE diaNum INT;
    
    SET diaNum = (SELECT DAYOFWEEK(fecha)-1);
    
    IF diaNum = 0 THEN 
		SET diaNum = 7;
	END IF;

    RETURN diaNum;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION obtenerNumDiaSemanaIni (
    inicialD VARCHAR(1)
)
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE diaNum INT;
    
    CASE inicialD
        WHEN 'L' THEN SET diaNum = 1;
        WHEN 'K' THEN SET diaNum = 2;
        WHEN 'M' THEN SET diaNum = 3;
        WHEN 'J' THEN SET diaNum = 4;
        WHEN 'V' THEN SET diaNum = 5;
        WHEN 'S' THEN SET diaNum = 6;
        ELSE SET diaNum = 7;
    END CASE;

    RETURN diaNum;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION obtenerDiaSemanaTrabajable (
	diasJornada VARCHAR(10),
    fechaEvaluar DATE)
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	DECLARE diaIni INT;
    DECLARE diaFin INT;
    DECLARE diaEvaluar INT;
    
    SET diaIni = obtenerNumDiaSemanaIni(LEFT(diasJornada, 1));
    SET diaFin = obtenerNumDiaSemanaIni(RIGHT(diasJornada, 1));
	SET diaEvaluar = obtenerNumDiaSemana(fechaEvaluar);
    
    IF diaIni <= diaEvaluar AND diaEvaluar <= diaFin THEN
		RETURN true;
	ELSE
		RETURN false;
	END IF;
    
END$$
DELIMITER ;

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

DELIMITER $$
CREATE PROCEDURE `empleadoEnFeriadoLaborable` (
    IN idEmpleado INT,
    IN fecha DATE,
    OUT esFeriadoLab BOOLEAN) -- 1 es feriado laborable o no es feriado, 
							  -- 0 es feriado no laborable (error)
BEGIN
    IF DATE_FORMAT(fecha, '%m-%d') IN (SELECT DATE_FORMAT(D.fecha, '%m-%d') FROM listaempleadosplanta AS L 
				 INNER JOIN tipocalendarios AS T ON L.calendario = T.id
				 INNER JOIN diasFeriados AS D ON T.id = D.calendario
				  WHERE L.Id = idEmpleado AND D.esLaborable = 0) THEN -- Solo no laborables
		SET esFeriadoLab = false;
	ELSE
		SET esFeriadoLab = true;
	END IF;
    
END$$
DELIMITER ;

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