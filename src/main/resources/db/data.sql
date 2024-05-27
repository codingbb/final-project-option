-- user 정보
insert into user_tb(role, username, password, person_name, phone, birth, email, created_at) values(1, 'admin', '1234', '최주호', '010-1234-5678', '1970-12-03','admin@nate.com', now());
insert into user_tb(role, username, password, person_name, phone, birth, email, created_at) values(2, 'ssar', '1234', '이서현', '010-2222-2222', '1980-03-21','ssar@nate.com', now());
insert into user_tb(role, username, password, person_name, phone, birth, email, created_at) values(2, 'cos', '1234', '심유주', '010-3333-3333', '1988-09-22','cos@nate.com', now());
insert into user_tb(role, username, password, person_name, phone, birth, email, created_at) values(2, 'love', '1234', '이소연', '010-4444-4444', '1999-01-26','love@nate.com', now());

-- 카테고리
INSERT INTO category_tb(category_code, category_name, created_at) VALUES ('A01', '과일', NOW());
INSERT INTO category_tb(category_code, category_name, created_at) VALUES ('A02', '채소', NOW());
INSERT INTO category_tb(category_code, category_name, created_at) VALUES ('A03', '유제품', NOW());

-- product 정보
INSERT INTO product_tb(name, price, qty, img, created_at) VALUES ('성주 참외 1.5kg(4~7입)', 17900, 100, 'prod_3.jpg' ,NOW());
INSERT INTO product_tb(name, price, qty, img, created_at) VALUES ('바나나 실속 2종', 4800, 100, 'prod_4.jpg', NOW());
INSERT INTO product_tb(name, price, qty, img, created_at) VALUES ('당도선별 천혜향 1kg(4~6입)', 15000, 100, 'prod_1.jpg',NOW());
INSERT INTO product_tb(name, price, qty, img, created_at) VALUES ('대추 방울 토마토 750g', 9900, 100,'prod_2.jpg', NOW());
INSERT INTO product_tb(name, price, qty, img, created_at) VALUES ('애플청포도 500g', 11900, 100, 'prod_7.jpg',NOW());
INSERT INTO product_tb(name, price, qty, img, created_at) VALUES ('수박 4kg 이상', 28000, 100, 'prod_8.jpg',NOW());

-- cart 정보
INSERT INTO cart_tb(user_id, product_id, order_qty, is_checked, created_at) VALUES (2, 1, 20, false, NOW());
INSERT INTO cart_tb(user_id, product_id, order_qty, is_checked, created_at) VALUES (2, 3, 10, false, NOW());
INSERT INTO cart_tb(user_id, product_id, order_qty, is_checked, created_at) VALUES (2, 4,  5, false, NOW());
INSERT INTO cart_tb(user_id, product_id, order_qty, is_checked, created_at) VALUES (3, 2, 15, false, NOW());
INSERT INTO cart_tb(user_id, product_id, order_qty, is_checked, created_at) VALUES (3, 5, 10, false, NOW());
INSERT INTO cart_tb(user_id, product_id, order_qty, is_checked, created_at) VALUES (4, 2,  3, false, NOW());
INSERT INTO cart_tb(user_id, product_id, order_qty, is_checked, created_at) VALUES (4, 3,  20, false, NOW());

-- order 정보
-- INSERT INTO order_tb(user_id, address, sum, status, orderNumb, created_at) VALUES (2, '부산광역시 진구 신암로', 119500, 'ORDER_COMPLETE', '240422RVLR4', '2024-04-22 14:00:00');
-- INSERT INTO order_tb(user_id, address, sum, status, orderNumb, created_at) VALUES (2, '부산광역시 진구 신암로', 129000, 'ORDER_COMPLETE', '240428ESC5R','2024-04-28 15:30:00');
-- INSERT INTO order_tb(user_id, address, sum, status, orderNumb, created_at) VALUES (3, '서울 강남구 가로수길 10', 99000, 'ORDER_COMPLETE', '240501TKDSD','2024-05-01 09:45:00');
-- INSERT INTO order_tb(user_id, address, sum, status, orderNumb, created_at) VALUES (4, '서울 신사동 541', 90000, 'ORDER_CANCEL', '240428TKD52','2024-04-28 18:00:00');
--
--
-- -- -- order-item 정보
-- INSERT INTO order_item_tb(product_id, order_qty, order_id, created_at) VALUES (1, 5, 1, NOW());
-- INSERT INTO order_item_tb(product_id, order_qty, order_id, created_at) VALUES (3, 2, 1, NOW());
--
-- INSERT INTO order_item_tb(product_id, order_qty, order_id, created_at) VALUES (4, 7, 2, NOW());
-- INSERT INTO order_item_tb(product_id, order_qty, order_id, created_at) VALUES (2, 5, 2, NOW());
-- INSERT INTO order_item_tb(product_id, order_qty, order_id, created_at) VALUES (5, 3, 2, NOW());
--
-- INSERT INTO order_item_tb(product_id, order_qty, order_id, created_at) VALUES (4, 10, 3, NOW());
-- INSERT INTO order_item_tb(product_id, order_qty, order_id, created_at) VALUES (3, 6, 4, NOW());

