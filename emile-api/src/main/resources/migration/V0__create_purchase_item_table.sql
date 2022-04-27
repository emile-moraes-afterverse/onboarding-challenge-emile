CREATE TABLE purchase_item(
    itemId  SERIAL NOT NULL,
    item    VARCHAR (100) NOT NULL,
    coins   INTEGER NOT NULL,
    gems    INTEGER NOT NULL,
    profile_id  VARCHAR (100),
    location    VARCHAR NOT NULL,
    created_at  TIMESTAMP ,

    PRIMARY KEY (itemId)
);