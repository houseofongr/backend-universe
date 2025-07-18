set foreign_key_checks = 0;
truncate table FILE;
truncate table AAR_USER;
truncate table UNIVERSE;
truncate table UNIVERSE_HASHTAG;
truncate table UNIVERSE_LIKE;
truncate table HASHTAG;
truncate table CATEGORY;
truncate table SPACE;
truncate table PIECE;
truncate table SOUND;
set foreign_key_checks = 1;

insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, EMAIL, TERMS_OF_USE_AGREEMENT,
                     PERSONAL_INFORMATION_AGREEMENT, CREATED_TIME, UPDATED_TIME)
values (1, '남상엽', 'NOT_SET', 'leaf', 'test@example.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, '남수정', 'NOT_SET', 'yeop', 'test2@example.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO CATEGORY(ID, TITLE_KOR, TITLE_ENG)
values (1, '생활', 'LIFE'),
       (2, '공공', 'PUBLIC'),
       (3, '정부', 'GOVERNMENT');

INSERT INTO UNIVERSE (ID, TITLE, DESCRIPTION, VIEW_COUNT, PUBLIC_STATUS,
                      THUMB_MUSIC_FILE_ID, THUMBNAIL_FILE_ID, INNER_IMAGE_FILE_ID,
                      USER_ID, CATEGORY_ID, CREATED_TIME, UPDATED_TIME)
VALUES (1, '정책 유니버스', '공공기관 관련 유니버스입니다.', 1, 'PUBLIC', 1, 2, 3, 1, 2, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (2, '비공개 정책', '비공개 공공기관 유니버스입니다.', 2, 'PRIVATE', 4, 5, 6, 1, 2, '2025-07-09 15:01:00', '2025-07-09 15:01:00'),
       (3, '건강 유니버스', '건강 기관과 관련된 콘텐츠입니다.', 3, 'PUBLIC', 7, 8, 9, 1, 1, '2025-07-09 15:02:00', '2025-07-09 15:02:00'),
       (4, '비공개 건강 정보', '비공개 건강 관련 정보입니다.', 5, 'PRIVATE', 10, 11, 12, 1, 1, '2025-07-09 15:03:00',
        '2025-07-09 15:03:00'),
       (5, '라이프스타일 유니버스', '생활과 일상에 대한 이야기입니다.', 4, 'PUBLIC', 13, 14, 15, 1, 1, '2025-07-09 15:04:00',
        '2025-07-09 15:04:00'),
       (6, '비공개 라이프', '비공개 일상 콘텐츠입니다.', 7, 'PRIVATE', 16, 17, 18, 1, 1, '2025-07-09 15:05:00', '2025-07-09 15:05:00'),
       (7, '패션 유니버스', '패션과 뷰티 관련 유니버스입니다.', 10, 'PUBLIC', 19, 20, 21, 1, 1, '2025-07-09 15:06:00',
        '2025-07-09 15:06:00'),
       (8, '비공개 뷰티', '비공개 패션 콘텐츠입니다.', 7, 'PRIVATE', 22, 23, 24, 1, 1, '2025-07-09 15:07:00', '2025-07-09 15:07:00'),
       (9, '웰니스 유니버스', '웰빙과 건강을 위한 콘텐츠입니다.', 4, 'PUBLIC', 25, 26, 27, 1, 3, '2025-07-09 15:08:00',
        '2025-07-09 15:08:00'),
       (10, '소소한 유니버스', '삶의 소소한 이야기를 담았습니다.', 6, 'PUBLIC', 28, 29, 30, 2, 1, '2025-07-09 15:09:00',
        '2025-07-09 15:09:00'),
       (11, '럭셔리 유니버스', '럭셔리와 아름다움의 세계입니다.', 1, 'PUBLIC', 31, 32, 33, 2, 1, '2025-07-09 15:10:00',
        '2025-07-09 15:10:00'),
       (12, '우주', '유니버스는 우주입니다.', 0, 'PUBLIC', 34, 35, 36, 2, 2, '2025-07-09 15:11:00', '2025-07-09 15:11:00');

insert into HASHTAG(ID, TAG, CREATED_TIME, UPDATED_TIME)
values (1, 'exist', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, '우주', current_timestamp, current_timestamp),
       (3, '행성', current_timestamp, current_timestamp),
       (4, '지구', current_timestamp, current_timestamp),
       (5, '별', current_timestamp, current_timestamp);

insert into UNIVERSE_HASHTAG(ID, HASHTAG_ID, UNIVERSE_ID)
values (1, 2, 1),
       (2, 3, 1),
       (3, 4, 1),
       (4, 5, 1);

insert into UNIVERSE_LIKE(ID, CREATED_TIME, UPDATED_TIME, UNIVERSE_ID, USER_ID)
values (1, current_timestamp, current_timestamp, 1, 1),
       (2, current_timestamp, current_timestamp, 1, 2),
       (3, current_timestamp, current_timestamp, 10, 1),
       (4, current_timestamp, current_timestamp, 10, 2),
       (5, current_timestamp, current_timestamp, 11, 1),
       (6, current_timestamp, current_timestamp, 11, 2);

INSERT INTO SPACE (ID, TITLE, DESCRIPTION, SX, SY, EX, EY,
                   INNER_IMAGE_FILE_ID, UNIVERSE_ID, PARENT_SPACE_ID,
                   HIDDEN, CREATED_TIME, UPDATED_TIME)
VALUES (1, 'SPACE1', '유니버스의 스페이스-1', 0.5, 0.5, 0.7, 0.6, 37, 1, -1, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (2, 'SPACE2', '유니버스의 스페이스-2', 0.4, 0.2, 0.5, 0.1, 38, 1, -1, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (3, 'SPACE3', '스페이스1의 스페이스-1', 0.2, 0.3, 0.4, 0.2, 39, 1, 1, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (4, 'SPACE4', '스페이스2의 스페이스-1', 0.7, 0.4, 0.3, 0.3, 40, 1, 2, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (5, 'SPACE5', '스페이스2의 스페이스-2', 0.8, 0.1, 0.2, 0.4, 41, 1, 2, true, '2025-07-09 15:00:00', '2025-07-09 15:00:00');

INSERT INTO PIECE (ID, TITLE, DESCRIPTION, SX, SY, EX, EY,
                   INNER_IMAGE_FILE_ID, UNIVERSE_ID, PARENT_SPACE_ID,
                   HIDDEN, CREATED_TIME, UPDATED_TIME)
VALUES (1, 'PIECE1', '유니버스의 피스-1', 0.5, 0.5, 0.7, 0.6, -1, 1, -1, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (2, 'PIECE2', '스페이스1의 피스-1', 0.4, 0.2, 0.5, 0.1, -1, 1, 1, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (3, 'PIECE3', '스페이스3의 피스-1', 0.2, 0.3, 0.4, 0.2, -1, 1, 3, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (4, 'PIECE4', '스페이스4의 피스-1', 0.7, 0.4, 0.3, 0.3, -1, 1, 4, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (5, 'PIECE5', '스페이스4의 피스-2', 0.8, 0.1, 0.2, 0.4, -1, 1, 4, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (6, 'PIECE6', '스페이스5의 피스-1', 0.8, 0.1, 0.2, 0.4, -1, 1, 5, false, '2025-07-09 15:00:00',
        '2025-07-09 15:00:00'),
       (7, 'PIECE7', '스페이스5의 피스-2', 0.8, 0.1, 0.2, 0.4, -1, 1, 5, true, '2025-07-09 15:00:00', '2025-07-09 15:00:00');

INSERT INTO SOUND (ID, TITLE, DESCRIPTION, AUDIO_FILE_ID, HIDDEN, PIECE_ID, CREATED_TIME, UPDATED_TIME)
VALUES (1, '물소리', '잔잔한 시냇물 소리를 담았습니다.', 42, true, 4, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (2, '숲속 새소리', '숲속에서 들려오는 다양한 새들의 지저귐입니다.', 43, true, 4, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (3, '빗소리', '창밖으로 들리는 부드러운 빗소리입니다.', 44, true, 4, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (4, '모닥불', '불꽃이 타오르며 나무가 타는 따뜻한 소리입니다.', 45, true, 4, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (5, '파도소리', '해변에 밀려오는 파도소리를 담았습니다.', 46, true, 5, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (6, '카페 배경음', '조용한 카페에서 사람들의 대화와 잔 소리가 어우러진 환경음입니다.', 47, true, 5, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (7, '산책길', '자연 속 산책길에서 들리는 발자국 소리와 새소리입니다.', 48, true, 5, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (8, '도시의 아침', '도시에서 아침에 들리는 자동차 소리와 사람들의 움직임입니다.', 49, true, 5, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (9, '바람 소리', '들판을 스치는 부드러운 바람 소리입니다.', 50, true, 6, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (10, '명상 종소리', '마음을 가라앉히는 명상용 종소리입니다.', 51, true, 6, '2025-07-09 15:00:00', '2025-07-09 15:00:00'),
       (11, '기차역', '기차가 도착하고 출발하는 생동감 있는 역 소리입니다.', 52, true, 7, '2025-07-09 15:00:00', '2025-07-09 15:00:00');
