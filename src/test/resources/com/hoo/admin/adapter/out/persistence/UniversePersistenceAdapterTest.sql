insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, TERMS_OF_USE_AGREEMENT, PERSONAL_INFORMATION_AGREEMENT)
values (1, '남상엽', 'NOT_SET', 'leaf', false, false),
       (2, '남엽돌', 'NOT_SET', '건', false, false);

insert into UNIVERSE(UNIVERSE.ID, UNIVERSE.CREATED_TIME, UNIVERSE.UPDATED_TIME, UNIVERSE.CATEGORY, UNIVERSE.DESCRIPTION,
                     UNIVERSE.PUBLIC_STATUS, UNIVERSE.THUMB_MUSIC_FILE_ID, UNIVERSE.THUMBNAIL_FILE_ID,
                     universe.INNER_IMAGE_FILE_ID, UNIVERSE.TITLE, UNIVERSE.VIEW_COUNT, UNIVERSE.USER_ID)
values (1, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR,
        'GOVERNMENT_AND_PUBLIC_INSTITUTION', '공공기관 관련 유니버스입니다.', 'PUBLIC', '1', '1', 1, '정책 유니버스', 1, 1),
       (2, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR,
        'GOVERNMENT_AND_PUBLIC_INSTITUTION', '비공개 공공기관 유니버스입니다.', 'PRIVATE', '2', '2', 2, '비공개 정책', 2, 1),
       (3, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'HEALTH_INSTITUTION',
        '건강 기관과 관련된 콘텐츠입니다.', 'PUBLIC', '3', '3', 3, '건강 유니버스', 3, 1),
       (4, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'HEALTH_INSTITUTION',
        '비공개 건강 관련 정보입니다.', 'PRIVATE', '4', '4', 4, '비공개 건강 정보', 5, 1),
       (5, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'LIFE', '생활과 일상에 대한 이야기입니다.',
        'PUBLIC', '5', '5', 5, '라이프스타일 유니버스', 4, 1),
       (6, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'LIFE', '비공개 일상 콘텐츠입니다.',
        'PRIVATE', '6', '6', 6, '비공개 라이프', 7, 1),
       (7, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'FASHION_AND_BEAUTY',
        '패션과 뷰티 관련 유니버스입니다.', 'PUBLIC', '7', '7', 7, '패션 유니버스', 10, 1),
       (8, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'FASHION_AND_BEAUTY',
        '비공개 패션 콘텐츠입니다.', 'PRIVATE', '8', '8', 8, '비공개 뷰티', 7, 2),
       (9, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'HEALTH_INSTITUTION',
        '웰빙과 건강을 위한 콘텐츠입니다.', 'PUBLIC', '9', '9', 9, '웰니스 유니버스', 4, 2),
       (10, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'LIFE', '삶의 소소한 이야기를 담았습니다.',
        'PUBLIC', '10', '10', 10, '소소한 유니버스', 6, 2),
       (11, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, 'FASHION_AND_BEAUTY',
        '럭셔리와 아름다움의 세계입니다.', 'PUBLIC', '11', '11', 11, '럭셔리 유니버스', 1, 2),
       (12, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR,
        'GOVERNMENT_AND_PUBLIC_INSTITUTION', '유니버스는 우주입니다.', 'PUBLIC', '12', '12', 12, '우주', 0, 2);


insert into HASHTAG(ID, TAG, CREATED_TIME, UPDATED_TIME)
values ('1', 'exist', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('2', '우주', current_timestamp, current_timestamp),
       ('3', '행성', current_timestamp, current_timestamp),
       ('4', '지구', current_timestamp, current_timestamp),
       ('5', '별', current_timestamp, current_timestamp);

insert into UNIVERSE_HASHTAG(ID, HASHTAG_ID, UNIVERSE_ID)
values ('1', '2', '12'),
       ('2', '3', '12'),
       ('3', '4', '12'),
       ('4', '5', '12');

insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, TERMS_OF_USE_AGREEMENT, PERSONAL_INFORMATION_AGREEMENT)
values (10, '남상엽', 'NOT_SET', 'leaf10', false, false),
       (11, '남상엽', 'NOT_SET', 'leaf11', false, false),
       (12, '남상엽', 'NOT_SET', 'leaf12', false, false),
       (13, '남상엽', 'NOT_SET', 'leaf13', false, false);

insert into UNIVERSE_LIKE(UNIVERSE_LIKE.ID, UNIVERSE_LIKE.CREATED_TIME, UNIVERSE_LIKE.UPDATED_TIME,
                          UNIVERSE_LIKE.UNIVERSE_ID, UNIVERSE_LIKE.USER_ID)
values (1, current_timestamp, current_timestamp, 12, 10),
       (2, current_timestamp, current_timestamp, 12, 11),
       (3, current_timestamp, current_timestamp, 12, 12),
       (4, current_timestamp, current_timestamp, 12, 13);

insert into SPACE(SPACE.ID, SPACE.INNER_IMAGE_FILE_ID, SPACE.TITLE, SPACE.DESCRIPTION, SPACE.PARENT_SPACE_ID,
                  SPACE.UNIVERSE_ID, SPACE.SX, SPACE.SY, SPACE.EX, SPACE.EY, SPACE.CREATED_TIME,
                  SPACE.UPDATED_TIME, HIDDEN)
values (1, 4, 'space1', '유니버스의 스페이스-1', -1, 1, 0.5, 0.5, 0.7, 0.6, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (2, 5, 'space2', '유니버스의 스페이스-2', -1, 1, 0.4, 0.2, 0.5, 0.1, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (3, 6, 'space3', '스페이스1의 스페이스-1', 1, 1, 0.2, 0.3, 0.4, 0.2, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (4, 7, 'space4', '스페이스2의 스페이스-1', 2, 1, 0.7, 0.4, 0.3, 0.3, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (5, 8, 'space5', '스페이스2의 스페이스-2', 2, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00', false);

insert into PIECE(PIECE.ID, PIECE.INNER_IMAGE_FILE_ID, PIECE.TITLE, PIECE.DESCRIPTION, PIECE.PARENT_SPACE_ID,
                  PIECE.UNIVERSE_ID, PIECE.SX, PIECE.SY, PIECE.EX, PIECE.EY,
                  PIECE.CREATED_TIME, PIECE.UPDATED_TIME, HIDDEN)
values (1, 9, 'PIECE1', '유니버스의 엘리먼트-1', -1, 1, 0.5, 0.5, 0.7, 0.6, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (2, 10, 'PIECE2', '스페이스1의 엘리먼트-1', 1, 1, 0.4, 0.2, 0.5, 0.1, '2025-06-06 17:00', '2025-06-06 17:00',false),
       (3, 11, 'PIECE3', '스페이스3의 엘리먼트-1', 3, 1, 0.2, 0.3, 0.4, 0.2, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (4, 12, 'PIECE4', '스페이스4의 엘리먼트-1', 4, 1, 0.7, 0.4, 0.3, 0.3, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (5, 13, 'PIECE5', '스페이스4의 엘리먼트-2', 4, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (6, 14, 'PIECE6', '스페이스5의 엘리먼트-1', 5, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (7, 15, 'PIECE7', '스페이스5의 엘리먼트-2', 5, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00', false);
