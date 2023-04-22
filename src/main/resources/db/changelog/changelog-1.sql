-- liquibase formatted sql

-- changeset AlekseiLitvin:1682199538-1
ALTER TABLE post
    ADD COLUMN is_edited BOOLEAN