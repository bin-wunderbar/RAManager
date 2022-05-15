DROP DATABASE IF EXISTS RAManager;
CREATE DATABASE RAManager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE RAManager;

-- DROP USER IF EXISTS 'mysqladmin'@'%';
-- CREATE USER IF NOT EXISTS 'mysqladmin'@'%' IDENTIFIED BY 'Password1';
-- GRANT ALL PRIVILEGES ON *.* TO 'mysqladmin'@'%' WITH GRANT OPTION;

-- DROP USER IF EXISTS 'raadmin'@'%';
-- CREATE USER IF NOT EXISTS 'raadmin'@'%' IDENTIFIED BY 'Password1';
-- GRANT ALL PRIVILEGES ON RAManager.* TO 'raadmin'@'%';


-- ------------------------------------------------------------------------------------------------
CREATE TABLE Role (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',
    name VARCHAR (50) UNIQUE NOT NULL COMMENT 'Nombre del rol',

    permissionRead BOOLEAN COMMENT 'Permiso de lectura',
    permissionWrite BOOLEAN COMMENT 'Permiso de escritura',
    permissionRemove BOOLEAN COMMENT 'Permiso de eliminacion',
    permissionManagement BOOLEAN COMMENT 'Permiso de administracion',

    CONSTRAINT PK_ROLE PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Rol';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Employee (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',
    dni VARCHAR(10) UNIQUE NOT NULL COMMENT 'Numero de identificacion fiscal',
    name VARCHAR (50) NOT NULL COMMENT 'Nombre',
    surnames VARCHAR(50) NOT NULL COMMENT 'Apellidos',
    password VARCHAR(50) NOT NULL COMMENT 'Contrasenna',
    province VARCHAR(50) NOT NULL COMMENT 'Province',
    direction VARCHAR(250) NOT NULL COMMENT 'Direccion',
    email VARCHAR(50) UNIQUE NOT NULL COMMENT 'Correo electronico',
    phone VARCHAR(20) UNIQUE NOT NULL COMMENT 'Telefono',

    idRole INT NOT NULL,

    CONSTRAINT PK_ROLE_EMPLOYEE PRIMARY KEY (id),
    CONSTRAINT FK_ROLE FOREIGN KEY (idRole) REFERENCES Role (id),
    INDEX (dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Empleado';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Client (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',
    dni VARCHAR(10) UNIQUE NOT NULL COMMENT 'Numero de identificacion fiscal',
    name VARCHAR (50) NOT NULL COMMENT 'Nombre',
    surnames VARCHAR(50) NOT NULL COMMENT 'Apellidos',
    province VARCHAR(50) NOT NULL COMMENT 'Province',
    direction VARCHAR(250) NOT NULL COMMENT 'Contrasenna',
    email VARCHAR(50) UNIQUE NOT NULL COMMENT 'Direccion',
    phone VARCHAR(20) UNIQUE NOT NULL COMMENT 'Telefono',

    CONSTRAINT PK_CLIENT PRIMARY KEY (id),
    INDEX (dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Cliente';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Vehicle (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',
    registrationNumber VARCHAR(20) UNIQUE NOT NULL COMMENT 'Matricula',
    model VARCHAR (50) NOT NULL COMMENT 'Modelo',
    color VARCHAR (50) NOT NULL COMMENT 'Color',
    idClient INT NOT NULL COMMENT 'Identificador del cliente propietario',

    CONSTRAINT PK_VEHICLE PRIMARY KEY (id),
    CONSTRAINT FK_VEHICLE_CLIENT FOREIGN KEY (idClient) REFERENCES Client (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX (registrationNumber)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Vehículo del cliente';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Status (
    id INT NOT NULL COMMENT 'Identificador de la entidad',
    name VARCHAR(50) NOT NULL COMMENT 'Nombre',

    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Estado de la orden de trabajo';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE WorkOrder (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',
    inputDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP () COMMENT 'Fecha de entrada',
    description VARCHAR(256) NOT NULL COMMENT 'Descripcion',
    accept BOOLEAN NOT NULL COMMENT 'Orden aceptada por el cliente',

    issuedDateTime TIMESTAMP NULL COMMENT 'Fecha de facturacion',

    idClient INT NOT NULL COMMENT 'Cliente asociado a la orden',
    idVehicle  INT NOT NULL COMMENT 'Vehículo asociado a la orden',
    idEmployeeEvaluator iNT NOT NULL COMMENT 'Empleado responsable de la orden',
    idStatus INT NOT NULL COMMENT 'Estado',

    CONSTRAINT PK_ORDER PRIMARY KEY (id),
    CONSTRAINT FK_ORDER_CLIENT FOREIGN KEY (idClient) REFERENCES Client (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_ORDER_VEHICLE FOREIGN KEY (idVehicle) REFERENCES Vehicle (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_ORDER_EMPLOYEE FOREIGN KEY (idEmployeeEvaluator) REFERENCES Employee (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_ORDER_STATUS FOREIGN KEY (idStatus) REFERENCES Status (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Orden de trabajo';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Service (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',
    description VARCHAR (256) NOT NULL COMMENT 'Descripcion',
    hourlyPrice DOUBLE NOT NULL COMMENT 'Precio de la mano de obra por hora',

    CONSTRAINT PK_SERVICE PRIMARY key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Servicio disponible';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Operation (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',

    idOrder INT NOT NULL COMMENT 'Orden a la que pertenece esta operacion',
    idMechanic INT NOT NULL COMMENT 'Mecanico que resuelve la operacion',
    idService   INT NOT NULL COMMENT 'Servicio asignado',

    dedicatedTime INT NOT NULL COMMENT 'Tiempo dedicado',
    hourlyPriceApplied DOUBLE NOT NULL COMMENT 'Precio de hora aplicado',

    CONSTRAINT PK_OPERATION PRIMARY KEY (id),
    CONSTRAINT FK_OPERATION_ORDER FOREIGN KEY (idOrder) REFERENCES WorkOrder (id) ON DELETE CASCADE,
    CONSTRAINT FK_OPERATION_EMPLOYEE FOREIGN KEY (idMechanic) REFERENCES Employee (id) ON UPDATE CASCADE,
    CONSTRAINT FK_OPERATION_SERVICE FOREIGN KEY (idService) REFERENCES Service (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Operación de la orden de trabajo';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Provider (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',
    name VARCHAR (50) NOT NULL COMMENT 'Nombre',
    direction VARCHAR (256) NOT NULL COMMENT 'Direccion',
    email VARCHAR (50) NOT NULL COMMENT 'Correo electronico',
    phone VARCHAR (50) NOT NULL COMMENT 'Telefono',

    CONSTRAINT PK_PROVIDER PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Proveedor de materiales';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Material (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',
    name VARCHAR (50) NOT NULL COMMENT 'Nombre',
    description VARCHAR (256) NOT NULL COMMENT 'Descripcion',

    idProvider INT NOT NULL COMMENT 'Proveedor del material',
    unitPrice DOUBLE NOT NULL COMMENT 'Precio por unidad',

    CONSTRAINT PK_MATERIAL PRIMARY KEY (id),
    CONSTRAINT FK_MATERIAL_PROVIDER FOREIGN KEY (idProvider) REFERENCES Provider (id) ON DELETE RESTRICT,

    CONSTRAINT MATERIAL_UNIT_PRICE_ZERO CHECK (unitPrice > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Material disponible';

-- ------------------------------------------------------------------------------------------------
CREATE TABLE UsedMaterial (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la entidad',
    units DOUBLE NOT NULL COMMENT 'Cantidad de materiales utilizados',
    
    idMaterial INT NOT NULL COMMENT 'Material utilizado',
    idOperation INT NOT NULL COMMENT 'Operacion que utiliza este material',
    
    unitPriceApplied DOUBLE NOT NULL COMMENT 'Precio aplicado por unidad',

    CONSTRAINT PK_USED_MATERIAL PRIMARY key (id),
    CONSTRAINT FK_USED_MATERIAL_MATERIAL FOREIGN KEY (idMaterial) REFERENCES Material (id),
    CONSTRAINT FK_USED_MATERIAL_OPERATION FOREIGN KEY (idOperation) REFERENCES Operation (id) ON DELETE CASCADE,

    CONSTRAINT USED_MATERIAL_UNITS_ZERO CHECK (units > 0),
    CONSTRAINT USED_MATERIAL_PRICE_APPLIED_ZERO CHECK (unitPriceApplied > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'Material utilizado por la operación';

-- ------------------------------------------------------------------------------------------------
CREATE FUNCTION getLocalDateTimeFormat (_timeStamp TIMESTAMP) RETURNS VARCHAR(30)
    RETURN date_format(_timeStamp, "%Y-%m-%dT%H:%m:%s")
