CREATE TABLE purchase_package(
    itemId SERIAL NOT NULL,
    quantityCoins INTEGER NOT NULL,
    quantityGems INTEGER NOT NULL,
    price   DECIMAL NOT NULL,
    profile_id VARCHAR (100) NOT NULL,
    location VARCHAR NOT NULL,
    createdAt TIMESTAMP,

    PRIMARY KEY (itemId)
);