DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id int PRIMARY KEY,
    first_name char(20),
    last_name char(20),
    email varchar(50),
    password char(25),
);

DROP TABLE IF EXISTS rooms;

CREATE TABLE rooms (
    id int PRIMARY KEY,
    room_number int,
    room_type char(50),
    cleaning_status int,
    FOREIGN KEY (cleaning_status) REFERENCES cleaning_records
);

DROP TABLE IF EXISTS cleaning_records;

CREATE TABLE cleaning_records (
    id int PRIMARY KEY,
    status_id int,
    cleaner_id int,
    checker_id int,
    room_id int,
    cleaning_start_time datetime,
    cleaning_end_time datetime,
    check_time datetime,
    remarks text,
)