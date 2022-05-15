DROP DATABASE IF EXISTS RAManager;
CREATE DATABASE RAManager;
USE RAManager;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Role (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR (50) UNIQUE NOT NULL,

    permissionRead BOOLEAN,
    permissionWrite BOOLEAN,
    permissionRemove BOOLEAN,
    permissionManagement BOOLEAN,

    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Employee (
    id INT NOT NULL AUTO_INCREMENT,
    dni VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR (50) NOT NULL,
    surnames VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    direction VARCHAR(250) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,

    idRole INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idRole) REFERENCES Role (id),
    INDEX (dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Client (
    id INT NOT NULL AUTO_INCREMENT,
    dni VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR (50) NOT NULL,
    surnames VARCHAR(50) NOT NULL,
    direction VARCHAR(250) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,

    PRIMARY KEY (id),
    INDEX (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Vehicle (
    id INT NOT NULL AUTO_INCREMENT,
    registrationNumber VARCHAR(20) UNIQUE NOT NULL,
    model VARCHAR (50) NOT NULL,
    color VARCHAR (50) NOT NULL,
    idClient INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idClient) REFERENCES Client (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX (registrationNumber)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Status (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,

    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE WorkOrder (
    id INT NOT NULL AUTO_INCREMENT,
    inputDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP (),
    description VARCHAR(256) NOT NULL,
    accept BOOLEAN NOT NULL,

    issuedDateTime TIMESTAMP,

    idClient INT NOT NULL,
    idVehicle  INT NOT NULL,
    idEmployeeEvaluator iNT NOT NULL,
    idStatus INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idClient) REFERENCES Client (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idVehicle) REFERENCES Vehicle (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idEmployeeEvaluator) REFERENCES Employee (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idStatus) REFERENCES Status (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Service (
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR (256) NOT NULL,
    hourlyPrice DOUBLE NOT NULL,

    PRIMARY key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Operation (
    id INT NOT NULL AUTO_INCREMENT,

    idOrder INT NOT NULL,
    idMechanic INT NOT NULL,
    idService   INT NOT NULL,

    dedicatedTime INT NOT NULL,
    hourlyPriceApplied DOUBLE NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idOrder) REFERENCES WorkOrder (id) ON DELETE CASCADE,
    FOREIGN KEY (idMechanic) REFERENCES Employee (id) ON UPDATE CASCADE,
    FOREIGN KEY (idService) REFERENCES Service (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Provider (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR (50) NOT NULL,
    direction VARCHAR (256) NOT NULL,
    email VARCHAR (50) NOT NULL,
    phone VARCHAR (50) NOT NULL,

    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Material (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR (50) NOT NULL,
    description VARCHAR (256) NOT NULL,

    idProvider INT NOT NULL,
    unitPrice DOUBLE NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idProvider) REFERENCES Provider (id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------
CREATE TABLE UsedMaterial (
    id INT NOT NULL AUTO_INCREMENT,
    units DOUBLE NOT NULL,
    
    idMaterial INT NOT NULL,
    idOperation INT NOT NULL,
    
    unitPriceApplied DOUBLE NOT NULL,

    PRIMARY key (id),
    FOREIGN KEY (idMaterial) REFERENCES Material (id),
    FOREIGN KEY (idOperation) REFERENCES Operation (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ------------------------------------------------------------------------------------------------

-- INSERT INTO Provider (name, direction, email, phone) VALUES ();
-- INSERT INTO Material (name, description, idProvider, unitPrice) VALUES ("Recambio de freno", "Recambio de freno", 1, 11.1);

