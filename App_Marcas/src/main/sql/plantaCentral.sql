create database plantaCentral;

use plantaCentral;

CREATE TABLE `plantacentral`.`departamento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`));
 
 CREATE TABLE `plantacentral`.`jornadasLaborales` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `horaEntrada` TIME NOT NULL,
  `horaSalida` TIME NOT NULL,
  `letrasJornada` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `plantacentral`.`tipopagos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`));
  
 CREATE TABLE `plantacentral`.`tipoCalendarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipoEmpleado` VARCHAR(50) NOT NULL,
  `pagoHora` DECIMAL(21,6) NOT NULL,
  `pagoHoraE` DECIMAL(21,6) NOT NULL,
  `pagoHoraD` DECIMAL(21,6) NOT NULL,
  `tipoPago` INT NOT NULL,
  `jornada` INT NOT NULL,
  `salarioBruto` DECIMAL(21,6) NOT NULL,
  `horasSemanales` DECIMAL(21,6) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fkTipoPagoTipoCalendario_idx` (`tipoPago` ASC) VISIBLE,
  INDEX `fkJornadaTipoCalendario_idx` (`jornada` ASC) VISIBLE,
  CONSTRAINT `fkTipoPagoTipoCalendario` FOREIGN KEY (`tipoPago`) REFERENCES `plantacentral`.`tipopagos` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fkJornadaTipoCalendario` FOREIGN KEY (`jornada`) REFERENCES `plantacentral`.`jornadasLaborales` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE);
      
CREATE TABLE `plantacentral`.`diasferiados` (
  `calendario` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `esLaborable` BIT(1) NOT NULL,
  `pago` VARCHAR(50) NOT NULL,		-- puede ser N =normal, E =extra, D =doble
  PRIMARY KEY (`calendario`, `fecha`),
  CONSTRAINT `fkCalendarioFeriado` FOREIGN KEY (`calendario`) REFERENCES `plantacentral`.`tipocalendarios` (`id`)
	ON DELETE RESTRICT ON UPDATE CASCADE);

CREATE TABLE `plantacentral`.`listaempleadosplanta` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `nombreEmpleado` VARCHAR(50) NOT NULL,
  `apellidosEmpleado` VARCHAR(100) NOT NULL,
  `fechaEntrada` DATE NOT NULL,
  `fechaSalida` DATE,
  `activo` BIT(1) NOT NULL,
  `departamento` INT NOT NULL,
  `supervisor` INT,
  `calendario` INT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fkDepartamentoEmpleado_idx` (`departamento` ASC) VISIBLE,
  INDEX `fkCalendarioEmpleado_idx` (`calendario` ASC) VISIBLE,
  INDEX `fkSupervisorEmpleado_idx` (`supervisor` ASC) VISIBLE,
  CONSTRAINT `fkDepartamentoEmpleado` FOREIGN KEY (`departamento`) REFERENCES `plantacentral`.`departamento` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fkCalendarioEmpleado` FOREIGN KEY (`calendario`) REFERENCES `plantacentral`.`tipocalendarios` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fkSupervisorEmpleado` FOREIGN KEY (`supervisor`) REFERENCES `plantacentral`.`listaempleadosplanta` (`Id`)
    ON DELETE RESTRICT ON UPDATE CASCADE);    
    
CREATE TABLE `plantacentral`.`marcasplanta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idEmpleado` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `horaEntrada` TIME,
  `horaSalida` TIME,
  PRIMARY KEY (`id`),
  INDEX `fkEmpleadoMarca_idx` (`idEmpleado` ASC) VISIBLE,
  CONSTRAINT `fkEmpleadoMarca` FOREIGN KEY (`idEmpleado`) REFERENCES `plantacentral`.`listaempleadosplanta` (`Id`)
    ON DELETE RESTRICT ON UPDATE CASCADE);
  
CREATE TABLE `plantacentral`.`planilla` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idEmpleado` INT NOT NULL,
  `fechaInicio` DATE NOT NULL,
  `fechaFinal` DATE NOT NULL,
  `salarioBruto` DECIMAL(21,6) NOT NULL,
  `obligaciones` DECIMAL(21,6) NOT NULL,
  `salarioNeto` DECIMAL(21,6) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fkEmpleadoPlanilla_idx` (`idEmpleado` ASC) VISIBLE,
  CONSTRAINT `fkEmpleadoPlanilla` FOREIGN KEY (`idEmpleado`) REFERENCES `plantacentral`.`listaempleadosplanta` (`Id`)
    ON DELETE RESTRICT ON UPDATE CASCADE);

-- inserts
INSERT INTO departamento (nombre)
	VALUES ('Compras'), ('Finanzas'), ('Gerencia'), ('Logística'), ('Producción'), ('Ventas')

INSERT INTO tipopagos (nombre)
	VALUES ('Semanal'), ('Quincenal'), ('Mensual')

INSERT INTO jornadasLaborales (horaEntrada, horaSalida, letrasJornada)
	VALUES ('6:00', '14:00', 'L-S'), ('6:00', '15:00', 'L-V'), ('8:00', '16:00', 'L-V')

INSERT INTO tipocalendarios (tipoEmpleado, pagoHora, pagoHoraE, pagoHoraD, tipoPago, jornada, salarioBruto, horasSemanales)
	VALUES 	('Operativo1', 3, 4.5, 6, 1, 1, 144, 48), 
			('Operativo2', 3, 4.5, 6, 1, 2, 135, 45), 
			('Administrativo', 10, 15, 20, 2, 3, 800, 40), 
            ('Gerente nivel 1', 20, 30, 40, 2, 3, 1600, 40), 
            ('Gerente', 50, 75, 100, 3, 3, 4000, 40)

INSERT INTO diasferiados (calendario, fecha, esLaborable, pago)
	VALUE 	(1, '2023-04-11', 1, 'D'), (1, '2023-05-01', 1, 'D'), (1, '2023-07-25', 1, 'D'), (1, '2023-08-15', 1, 'D'), (1, '2023-09-15', 1, 'D'), (1, '2023-01-01', 0, 'N'),
			(2, '2023-04-11', 1, 'D'), (2, '2023-05-01', 1, 'D'), (2, '2023-07-25', 1, 'D'), (2, '2023-08-15', 1, 'D'), (2, '2023-09-15', 1, 'D'), (2, '2023-01-01', 0, 'N'),
            (3, '2023-04-11', 1, 'N'), (3, '2023-05-01', 1, 'N'), (3, '2023-07-25', 1, 'N'), (3, '2023-08-15', 1, 'N'), (3, '2023-09-15', 1, 'N'), (3, '2023-01-01', 1, 'N'),	-- no especifica si se trabaja o no, se va a suponer que se trabaja
            (4, '2023-04-11', 1, 'N'), (4, '2023-05-01', 1, 'N'), (4, '2023-07-25', 1, 'N'), (4, '2023-08-15', 1, 'N'), (4, '2023-09-15', 1, 'N'), (4, '2023-01-01', 1, 'N'),
            (5, '2023-04-11', 1, 'N'), (5, '2023-05-01', 1, 'N'), (5, '2023-07-25', 1, 'N'), (5, '2023-08-15', 1, 'N'), (5, '2023-09-15', 1, 'N'), (5, '2023-01-01', 1, 'N')
            
            
-- stores procedures 
DELIMITER $$
CREATE PROCEDURE `insertEmpleado` (
	IN nombre nvarchar(50), 
    IN apellidos nvarchar(100), 
    IN fechaEntrada date, IN fechaSalida date, 
    IN departamento int, IN supervisor int, IN calendario int)
BEGIN
	DECLARE activo BIT(1);
    IF fechaSalida IS NULL THEN
        SET activo = 1; -- Si fechaSalida es NULL, activo = 1
    ELSE
        SET activo = 0; -- Si fechaSalida no es NULL, activo = 0
    END IF;
    INSERT INTO listaempleadosplanta(nombreEmpleado, apellidosEmpleado, fechaEntrada, fechaSalida, activo, departamento, supervisor, calendario)
    VALUES (nombre, apellidos, fechaEntrada, fechaSalida, activo, departamento, supervisor, calendario);
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `setEmpleadoPorID` (
	IN idEmpleado int,
	IN nombre nvarchar(50), 
    IN apellidos nvarchar(100), 
    IN fechaE date, IN fechaS date, 
    IN idDepartamento int, IN idSupervisor int, IN idCalendario int)
BEGIN
	DECLARE activoE BIT(1);
    IF fechaS IS NULL THEN
        SET activoE = 1; -- Si fechaSalida es NULL, activo = 1
    ELSE
        SET activoE = 0; -- Si fechaSalida no es NULL, activo = 0
    END IF;
	UPDATE listaempleadosplanta
    SET nombreEmpleado = nombre, apellidosEmpleado = apellidos, fechaEntrada = fechaE, 
    fechaSalida = fechaS, activo = activoE, 
    departamento = idDepartamento, supervisor = idSupervisor, calendario = idCalendario
	WHERE idEmpleado = Id;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `eliminarEmpleado` (
	IN idEmpleado int,
    IN fechaS date)
BEGIN
	DECLARE activoE BIT(1);
	SET activoE = 0;
    
	UPDATE listaempleadosplanta
	set fechaSalida = fechaS, activo = activoE
	where Id = idEmpleado;
END$$
DELIMITER $$;

DELIMITER $$
CREATE PROCEDURE `getEmpleados` ()
BEGIN
	SELECT * FROM listaempleadosplanta;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `getEmpleadoPorID` (
	IN idEmpleado int)
BEGIN
	SELECT * 
    FROM listaempleadosplanta
    WHERE Id = idEmpleado;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `getEmpleadosDepartamento` (IN departamento int)
BEGIN
	SELECT *
    FROM listaempleadosplanta p
    WHERE p.departamento = departamento;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `getEmpleadosDadosDeBaja` (
	IN desde date,
    IN hasta date)
BEGIN
	SELECT *
    FROM listaempleadosplanta p
    WHERE p.fechaSalida IS NOT NULL and p.fechaSalida BETWEEN desde AND hasta;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `getDepartamentos` ()
BEGIN
	SELECT * FROM departamento;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `getSupervisores` ()
BEGIN
	SELECT distinct(p.supervisor)
    FROM listaempleadosplanta p;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `getEmpleadosSupervisor` (IN supervisor int)
BEGIN
	SELECT *
    FROM listaempleadosplanta p
    WHERE p.supervisor = supervisor;
END$$
DELIMITER ;


-- EMPLEADOS ************** planta Central
-- sin supervisor
CALL insertEmpleado ('Carmen', 'Jacquelin Lugo', '2020-08-08', null, 3, null, 5);
-- supervisor 42
CALL insertEmpleado ('José', 'Danny', '2021-12-01', null, 3, 1, 3);
CALL insertEmpleado ('Kenny', 'Jiménez Jiménez', '2020-06-02', null, 2, 1, 4);
CALL insertEmpleado ('Ramon Jacinto', 'Santiago Ovalles', '2021-08-16', null, 4, 1, 4);
CALL insertEmpleado ('Evangelina', 'Mota Serrano', '2021-07-14', null, 6, 1, 4);
-- supervisor 40
CALL insertEmpleado ('Digna Asuncion', 'Camacho López', '2021-05-06', null, 2, 3, 3);
CALL insertEmpleado ('Rosse Mary', 'Yocelyn Corniel', '2021-03-10', null, 2, 3, 3);
-- supervisor 37
CALL insertEmpleado ('Hugo José', 'Santos Lantigua', '2020-11-25', null, 4, 4, 3);
CALL insertEmpleado ('Adelinda', 'Pérez Díaz', '2020-11-18', null, 4, 4, 3);
-- supervisor 34
CALL insertEmpleado ('Rosa M.', 'Reyes Rodríguez', '2020-11-11', null, 6, 5, 3);
CALL insertEmpleado ('Yanet Mercedes', 'Ramos Gonzalez', '2020-08-10', null, 6, 5, 3);
CALL insertEmpleado ('Marilyn Altagracia', 'Mercedes R.', '2020-06-02', null, 6, 5, 3);

