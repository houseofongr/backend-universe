insert into HOUSE(ID, TITLE, AUTHOR, DESCRIPTION, BASIC_IMAGE_FILE_ID, BORDER_IMAGE_FILE_ID, WIDTH, HEIGHT,
                  CREATED_TIME, UPDATED_TIME)
values (2, 'not cozy house', 'arang', 'this is not cozy house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 'temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, 'temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 'temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, 'temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (7, 'temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (8, 'temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (9, 'temp house', 'tester', 'temp house', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10, 'temp houseInfo', 'tester', 'temp houseInfo', 3, 4, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (1, 'cozy house', 'leaf', 'this is cozy house', 1, 2, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into ROOM(ID, NAME, X, Y, Z, WIDTH, HEIGHT, IMAGE_FILE_ID, HOUSE_ID)
values (1, "거실", 0, 0, 0, 5000, 1000, 5, 1),
       (2, "주방", 0, 1000, 0, 5000, 1000, 6, 1);