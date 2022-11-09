-- CHANGE DATABASE
USE integrative_project;

-- INSERTS TABLES
INSERT INTO manager(name) VALUES('Mauri');
INSERT INTO manager(name) VALUES('Messi');
SELECT * FROM manager;
INSERT INTO warehouse(volume, manager_code) VALUES(300, 1);
INSERT INTO warehouse(volume, manager_code) VALUES(600, 2);
SELECT * FROM warehouse;

--SECTIONS
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id) VALUES('REFRIGERADO', 100, 0, 5, 0, 1);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id) VALUES('CONGELADO', 100, 0, -18, -22, 1);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id) VALUES('FRESCO', 100, 0, 10, 5, 1);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id) VALUES('REFRIGERADO', 0, 200, 5, 0, 2);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id) VALUES('CONGELADO', 200, 0, -18, -22, 2);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id) VALUES('FRESCO', 200, 0, 10, 5, 2);