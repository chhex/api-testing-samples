CREATE TABLE IF NOT EXISTS GENDER
(
    id  int auto_increment PRIMARY KEY,
    designation VARCHAR NOT NULL,
    description VARCHAR NOT NULL
);
CREATE TABLE IF NOT EXISTS ADDRESS
(
    id int auto_increment PRIMARY KEY,
    street_name VARCHAR NOT NULL,
    street_number VARCHAR NOT NULL,
    postal_code VARCHAR NOT NULL

);
CREATE TABLE IF NOT EXISTS PERSON
(
    id  int auto_increment PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    second_name VARCHAR NOT NULL,
    birth_day DATE NOT NULL,
    gender_id int NOT NULL,
    address_id int,
    foreign key (gender_id) references GENDER(ID),
    foreign key (address_id) references ADDRESS(ID)
);
CREATE TABLE IF NOT EXISTS HOBBY
(
    id  int auto_increment PRIMARY KEY,
    short_name VARCHAR NOT NULL
);
CREATE TABLE IF NOT EXISTS PERSON_HOBBY
(
    id  int auto_increment PRIMARY KEY,
    person_id  int  NOT NULL,
    hobby_id int NOT NULL,
    foreign key (person_id) references PERSON(ID),
    foreign key (hobby_id) references HOBBY(ID)
);
