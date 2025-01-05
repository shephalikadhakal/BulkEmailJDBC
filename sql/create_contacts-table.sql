CREATE DATABASE contactinfo;
USE contactinfo;
CREATE TABLE contacts (
    cid INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(40) NOT NULL,
    PRIMARY KEY (cid)
);
