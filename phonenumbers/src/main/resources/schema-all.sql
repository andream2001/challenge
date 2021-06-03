DROP TABLE phone_number IF EXISTS;

CREATE TABLE phone_number  (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    sms_phone VARCHAR(50),
    corr_descr VARCHAR(50),
    invalid BOOLEAN DEFAULT false
);