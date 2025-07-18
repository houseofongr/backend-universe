insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, EMAIL, TERMS_OF_USE_AGREEMENT,
                     PERSONAL_INFORMATION_AGREEMENT, CREATED_TIME, UPDATED_TIME)
values (10, '남상엽', 'NOT_SET', 'leaf', 'test@example.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (11, null, null, 'leaf2', 'test2@example.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into BUSINESS_USER(ID, EMAIL, PASSWORD, NICKNAME, STATUS, TERMS_OF_USE_AGREEMENT, PERSONAL_INFORMATION_AGREEMENT,
                          CREATED_TIME, UPDATED_TIME, USER_ID)
values (1, 'test2@example.com', '{bcrypt}$2a$10$E4pJtJxQtjHHH11j8/tNTOJljXDFJc3NWGlbCt00SZQB8Nn7DiKF.', 'temp_user123',
        'WAITING', true, true, '2025-06-27 20:15', '2025-06-27 20:15', 11);

insert into SNS_ACCOUNT(ID, REAL_NAME, NICKNAME, EMAIL, SNS_ID, SNS_DOMAIN, USER_ID)
values (1, '남상엽', 'leaf', 'test@example.com', 'SNS_ID', 'KAKAO', 10);

insert into HOUSE(ID, TITLE, AUTHOR, DESCRIPTION, BASIC_IMAGE_FILE_ID, BORDER_IMAGE_FILE_ID, WIDTH, HEIGHT,
                  CREATED_TIME, UPDATED_TIME)
values (20, 'cozy house', 'leaf', 'my cozy house', 1, 2, 5000, 5000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into ROOM(ID, NAME, X, Y, Z, WIDTH, HEIGHT, IMAGE_FILE_ID, HOUSE_ID)
values (1, "거실", 0, 0, 0, 5000, 1000, 5, 20),
       (2, "주방", 0, 1000, 0, 5000, 1000, 6, 20);

insert into HOME(ID, USER_ID, HOUSE_ID, NAME, CREATED_TIME, UPDATED_TIME)
values (1, 10, 20, "leaf의 cozy house", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 10, 20, "leaf의 simple house", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


insert into ITEM_SHAPE(ID, X, Y, DTYPE)
values (1, 100, 100, 'RECTANGLE'),
       (2, 200, 200, 'CIRCLE'),
       (3, 500, 500, 'ELLIPSE');

insert into ITEM_SHAPE_RECTANGLE(ID, WIDTH, HEIGHT, ROTATION)
values (1, 10, 10, 5);

insert into ITEM_SHAPE_CIRCLE(ID, RADIUS)
values (2, 10.5);

insert into ITEM_SHAPE_ELLIPSE(ID, RADIUSX, RADIUSY, ROTATION)
values (3, 15, 15, 90);

insert into ITEM(ID, NAME, HOME_ID, ROOM_ID, ITEM_SHAPE_ID)
values (1, '설이', 1, 1, 1),
       (2, '강아지', 1, 1, 2),
       (3, '화분', 1, 1, 3);

insert into SOUND_SOURCE(ID, NAME, DESCRIPTION, AUDIO_FILE_ID, IS_ACTIVE, CREATED_TIME, UPDATED_TIME, ITEM_ID)
values (1, "골골송", "2025년 골골송 V1", 1, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
       (2, "멍멍송", "2025년 멍멍송 V1", 2, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
       (3, "골골송 V2", "2025년 골골송 V2", 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
       (4, "골골골송", "2026년 골골골송", 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
       (5, "멍멍송 V2", "2025년 멍멍송 V2", 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);