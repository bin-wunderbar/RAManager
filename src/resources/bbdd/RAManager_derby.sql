-- ------------------------------------------------------------------------------------------------
CREATE TABLE Role (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR (50) UNIQUE NOT NULL,
    permissionRead BOOLEAN,
    permissionWrite BOOLEAN,
    permissionRemove BOOLEAN,
    permissionManagement BOOLEAN,
    
    primary key (id)
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Employee (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    dni VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR (50) NOT NULL,
    surnames VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    province VARCHAR(50) NOT NULL,
    direction VARCHAR(250) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,

    idRole INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idRole) REFERENCES Role (id)
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Client (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    dni VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR (50) NOT NULL,
    surnames VARCHAR(50) NOT NULL,
    province VARCHAR(50) NOT NULL,
    direction VARCHAR(250) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,

    PRIMARY KEY (id)
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Vehicle (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    registrationNumber VARCHAR(20) UNIQUE NOT NULL,
    model VARCHAR (50) NOT NULL,
    color VARCHAR (50) NOT NULL,
    idClient INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idClient) REFERENCES Client (id) ON DELETE RESTRICT
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Status (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,

    PRIMARY KEY (id)
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE WorkOrder (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    inputDateTime TIMESTAMP NOT NULL,
    description VARCHAR(256) NOT NULL,
    accept BOOLEAN NOT NULL,

    issuedDateTime TIMESTAMP,

    idClient INT NOT NULL,
    idVehicle  INT NOT NULL,
    idEmployeeEvaluator iNT NOT NULL,
    idStatus INT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idClient) REFERENCES Client (id) ON DELETE CASCADE,
    FOREIGN KEY (idVehicle) REFERENCES Vehicle (id) ON DELETE CASCADE,
    FOREIGN KEY (idEmployeeEvaluator) REFERENCES Employee (id) ON DELETE CASCADE,
    FOREIGN KEY (idStatus) REFERENCES Status (id)
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Service (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    description VARCHAR (256) NOT NULL,
    hourlyPrice DOUBLE NOT NULL,

    PRIMARY key (id)
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Operation (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,

    idOrder INT NOT NULL,
    idMechanic INT NOT NULL,
    idService   INT NOT NULL,

    dedicatedTime INT NOT NULL,
    hourlyPriceApplied DOUBLE NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idOrder) REFERENCES WorkOrder (id) ON DELETE CASCADE,
    FOREIGN KEY (idMechanic) REFERENCES Employee (id) ON DELETE CASCADE,
    FOREIGN KEY (idService) REFERENCES Service (id)
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Provider (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,

    name VARCHAR (50) NOT NULL,
    direction VARCHAR (256) NOT NULL,
    email VARCHAR (50) NOT NULL,
    phone VARCHAR (50) NOT NULL,

    PRIMARY KEY (id)
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE Material (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,

    name VARCHAR (50) NOT NULL,
    description VARCHAR (256) NOT NULL,

    idProvider INT NOT NULL,
    unitPrice DOUBLE NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (idProvider) REFERENCES Provider (id) ON DELETE RESTRICT,

    CHECK (unitPrice > 0)
);

-- ------------------------------------------------------------------------------------------------
CREATE TABLE UsedMaterial (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,

    units DOUBLE NOT NULL,
    
    idMaterial INT NOT NULL,
    idOperation INT NOT NULL,
    
    unitPriceApplied DOUBLE NOT NULL,

    PRIMARY key (id),
    FOREIGN KEY (idMaterial) REFERENCES Material (id),
    FOREIGN KEY (idOperation) REFERENCES Operation (id) ON DELETE CASCADE,

    CHECK (units > 0),
    CHECK (unitPriceApplied > 0)
);

-- ------------------------------------------------------------------------------------------------
CREATE FUNCTION getLocalDateTimeFormat (timeStampValue TIMESTAMP) RETURNS VARCHAR(30)
    PARAMETER STYLE JAVA NO SQL LANGUAGE JAVA
    EXTERNAL NAME 'dal.DBManager.getLocalDateTimeFormat'
