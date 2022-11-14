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
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id)
VALUES ('REFRIGERADO', 100, 0, 5, 0, 1);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id)
VALUES ('CONGELADO', 100, 21, -18, -22, 1);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id)
VALUES ('FRESCO', 100, 0, 10, 5, 1);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id)
VALUES ('REFRIGERADO', 200, 0, 5, 0, 2);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id)
VALUES ('CONGELADO', 200, 50, -18, -22, 2);
INSERT INTO section(category, volume, accumulated_volume, max_temperature, min_temperature, warehouse_id)
VALUES ('FRESCO', 200, 0, 10, 5, 2);

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
INSERT INTO advertisement(name, price, seller_id)
VALUES ("Hamburguer", 2.00, 1);
INSERT INTO advertisement(name, price, seller_id)
VALUES ("Pizza", 4.00, 1);
INSERT INTO advertisement(name, price, seller_id)
VALUES ("Frango", 15.00, 2);
INSERT INTO advertisement(name, price, seller_id)
VALUES ("Sorvete", 5.50, 3);

--INBOUND ORDER
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

--BATCH
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_id, order_number)
VALUES (-20, "2023-01-25", "2022-11-01T22:50:55", 60, 40, 10, 1, 1);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_id, order_number)
VALUES (-20, "2023-01-23", "2022-11-01T23:40:00", 25, 20, 5, 1, 2);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity,volume, advertisement_id, order_number)
VALUES (-18, "2023-02-20", "2022-08-02T11:34:55", 150, 50, 20, 2, 3);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity,volume, advertisement_id, order_number)
VALUES (-19, "2022-11-15", "2022-03-25T14:38:55", 600, 60, 30, 3, 4);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_id, order_number)
VALUES (-20, "2022-11-25", "2022-11-01T22:50:55", 36, 24, 6, 1, 5);
INSERT INTO batch (current_temperature, due_date, manufacturing_date_time, price, product_quantity, volume, advertisement_id, order_number)
VALUES (-22, "2023-02-25", "2022-10-05T22:50:55", 96, 32, 8, 4, 6);