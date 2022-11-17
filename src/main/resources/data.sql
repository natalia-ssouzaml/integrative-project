-- CHANGE DATABASE
USE
integrative_project;


-- INSERTS MANAGER
INSERT INTO manager(name)
VALUES ('Mauri');
INSERT INTO manager(name)
VALUES ('Messi');


-- INSERTS WAREHOUSE
INSERT INTO warehouse(volume, manager_code)
VALUES (300, 1);
INSERT INTO warehouse(volume, manager_code)
VALUES (600, 2);


-- SECTIONS
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_code)
VALUES ('REFRIGERADO', 100, 13, 5, 0, 1);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_code)
VALUES ('CONGELADO', 100, 21, -18, -22, 1);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_code)
VALUES ('FRESCO', 100, 46, 10, 5, 1);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_code)
VALUES ('REFRIGERADO', 200, 1, 5, 0, 2);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_code)
VALUES ('CONGELADO', 200, 58, -18, -22, 2);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_code)
VALUES ('FRESCO', 200, 25, 10, 5, 2);

-- BUYER
INSERT INTO buyer(name)
VALUES ('Neymar');
INSERT INTO buyer(name)
VALUES ('Benzema');


-- SELLER
INSERT INTO seller(name)
VALUES ('Lucas');
INSERT INTO seller(name)
VALUES ('Matheus');
INSERT INTO seller(name)
VALUES ('Cristiano Ronaldo');


-- ADVERTISEMENT
-- Congelados
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Hamburguer", 2.00, 1);
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Pizza", 4.00, 1);
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Frango", 15.00, 2);
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Sorvete", 5.50, 3);
-- Refrigerados
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Margarina", 12.00, 1);
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Iorgute", 8.00, 1);
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Queijo de minas", 25.00, 3);
-- Frescos
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Alface", 2.10, 2);
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Tomate", 1.90, 3);
INSERT INTO advertisement(name, price, seller_code)
VALUES ("Mamão", 4.50, 3);

--INBOUND ORDER
-- Congelados
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-11-02", 2);
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-11-02", 2);
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-08-03", 5);
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-03-26", 5);
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-11-02", 2);
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-10-06", 5);
-- Refrigerados
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-11-12", 1);
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-11-17", 4);
-- Frescos
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-11-21", 3);
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-11-17", 6);
INSERT INTO inbound_order (order_date, section_code)
VALUES ("2022-11-17", 3);

--BATCH
-- Congelados
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (-20, "2023-01-25", "2022-11-01T22:50:55", 60, 40, 10, 1, 1);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (-20, "2023-01-23", "2022-11-01T23:40:00", 25, 20, 5, 1, 2);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity,volume, advertisement_code, order_code)
VALUES (-18, "2023-02-20", "2022-08-02T11:34:55", 150, 50, 20, 2, 3);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity,volume, advertisement_code, order_code)
VALUES (-19, "2022-11-15", "2022-03-25T14:38:55", 600, 60, 30, 3, 4);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (-20, "2022-11-25", "2022-11-01T22:50:55", 36, 24, 6, 1, 5);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (-22, "2023-02-25", "2022-10-05T22:50:55", 96, 32, 8, 4, 6);
-- Refrigerados
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (1, "2022-12-10", "2022-11-05T20:00:55", 650, 60, 6, 5, 7);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (1, "2022-12-15", "2022-11-10T08:10:56", 650, 20, 2, 5, 7);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (0, "2023-01-01", "2022-11-11T19:14:45", 380, 50, 5, 6, 7);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (4, "2022-12-01", "2022-11-15T18:41:57", 210, 10, 1, 7, 8);
-- Frescos
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (7, "2022-11-29", "2022-11-19T15:13:38", 150, 100, 20, 8, 9);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (6, "2022-12-04", "2022-11-15T15:31:58", 460, 250, 25, 9, 10);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (6, "2022-12-05", "2022-11-16T12:41:54", 460, 100, 10, 9, 11);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_code, order_code)
VALUES (9, "2023-01-02", "2022-11-17T07:08:41", 135, 32, 16, 10, 11);