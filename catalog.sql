create table category
(
    id   serial8,
    name varchar not null,
    primary key (id)
);

insert into category (name)
values ('Процессоры'),
       ('Мониторы');

create table products
(
    id          serial8,
    category_id int8    not null,
    name        varchar not null,
    price       int8    not null,
    primary key (id),
    foreign key (category_id) references category (id)
);

insert into products (category_id, name, price)
values (1, 'Intel core i7-11700', 129990),
       (1, 'AMD Ryzen 5600', 99900),
       (2, 'HP Monitor', 67900),
       (2, 'Asus Monitor', 89900);

create table characteristics
(
    id          serial8,
    category_id int8    not null,
    name        varchar not null,
    primary key (id),
    foreign key (category_id) references category (id)
);

insert into characteristics (category_id, name)
values (1, 'Производитель'),
       (1, 'Количество ядер'),
       (1, 'Сокет'),
       (2, 'Производитель'),
       (2, 'Диагональ'),
       (2, 'Матрица'),
       (2, 'Разрешение');

create table char_values
(
    id         serial8,
    char_id    int8    not null,
    product_id int8    not null,
    value      varchar not null,
    primary key (id),
    foreign key (char_id) references characteristics (id),
    foreign key (product_id) references products (id)
);

insert into char_values (char_id, product_id, value)
values (1, 1, 'Intel'),
       (1, 2, 'AMD'),
       (2, 1, '8'),
       (2, 2, '6'),
       (3, 1, '1151'),
       (3, 2, '1155'),
       (4, 3, 'HP'),
       (4, 4, 'Asus'),
       (5, 3, '27'),
       (5, 4, '27.5'),
       (6, 3, 'TN'),
       (6, 4, 'IPS'),
       (7, 3, '1920*1080'),
       (7, 4, '1920*1080');
