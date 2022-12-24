 - liquibase formatted sql

 - changeSet filippova-t:1
CREATE INDEX student_name_index ON student (name);

 - changeSet filippova-t:2
CREATE INDEX faculty_name_and_color_index ON faculty (name, color);
