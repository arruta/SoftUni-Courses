INSERT INTO users (id, is_active, email, first_name, last_name, password)
VALUES (1, 1, 'admin@example.com', 'Admin', 'Adminov',
        '612c6a1a3a56d3ae988021ba29fa6de9ada73901c45438208f5b38734ccd2bb19f0230e651f1fd6b7ec06c582c0a7336'),
       (2, 1, 'user@example.com', 'User', 'Userov',
        '612c6a1a3a56d3ae988021ba29fa6de9ada73901c45438208f5b38734ccd2bb19f0230e651f1fd6b7ec06c582c0a7336');

INSERT INTO roles (id, role)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);

INSERT INTO brands (name)
VALUES ('Toyota'),
       ('Ford'),
       ('Trabant');

INSERT INTO models (category, brand_id, name)
VALUES ('CAR', 1, 'Camry'),
       ('CAR', 1, 'Corolla'),
       ('CAR', 2, 'Focus'),
       ('CAR', 2, 'Fiesta'),
       ('CAR', 3, '601');

INSERT INTO offers (id, description, engine, image_url, mileage, price, transmission, uuid, year, model_id)
VALUES ('1', 'Top Trabi!', 'GASOLINE',
        'https://www.autoscout24.bg/cms-content-assets/ofx9265tkP0tokDWbcvyT-5974b71ed84f16b4932762a0807e8780-trabant-l-1100.jpg',
        '25000', '2000', 'MANUAL', '36e0f14a-8195-11ee-b962-0242ac120002', '1987', '5'),
       ('2', 'Top Trabi!', 'GASOLINE',
        'https://www.autoscout24.bg/cms-content-assets/ofx9265tkP0tokDWbcvyT-5974b71ed84f16b4932762a0807e8780-trabant-l-1100.jpg',
        '25000', '2500', 'MANUAL', '36e0f14a-8195-11ee-b962-0242ac120003', '1986', '5'),
       ('3', 'Top Trabi!', 'GASOLINE',
        'https://www.autoscout24.bg/cms-content-assets/ofx9265tkP0tokDWbcvyT-5974b71ed84f16b4932762a0807e8780-trabant-l-1100.jpg',
        '25000', '2700', 'MANUAL', '36e0f14a-8195-11ee-b962-0242ac120004', '1988', '5'),
       ('4', 'Top Trabi!', 'GASOLINE',
        'https://www.autoscout24.bg/cms-content-assets/ofx9265tkP0tokDWbcvyT-5974b71ed84f16b4932762a0807e8780-trabant-l-1100.jpg',
        '25000', '2600', 'MANUAL', '36e0f14a-8195-11ee-b962-0242ac120005', '1989', '5'),
       ('5', 'Top Trabi!', 'GASOLINE',
        'https://www.autoscout24.bg/cms-content-assets/ofx9265tkP0tokDWbcvyT-5974b71ed84f16b4932762a0807e8780-trabant-l-1100.jpg',
        '25000', '2350', 'MANUAL', '36e0f14a-8195-11ee-b962-0242ac120006', '1984', '5');