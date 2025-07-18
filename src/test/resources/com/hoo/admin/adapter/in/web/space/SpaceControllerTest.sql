insert into AAR_USER(ID, REAL_NAME, PHONE_NUMBER, NICKNAME, EMAIL, TERMS_OF_USE_AGREEMENT,
                     PERSONAL_INFORMATION_AGREEMENT, CREATED_TIME, UPDATED_TIME)
values (1, '남상엽', 'NOT_SET', 'leaf', 'test@example.com', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


insert into UNIVERSE(UNIVERSE.ID, UNIVERSE.CREATED_TIME, UNIVERSE.UPDATED_TIME, UNIVERSE.CATEGORY, UNIVERSE.DESCRIPTION,
                     UNIVERSE.PUBLIC_STATUS, UNIVERSE.THUMB_MUSIC_FILE_ID, UNIVERSE.THUMBNAIL_FILE_ID,
                     universe.INNER_IMAGE_FILE_ID, UNIVERSE.TITLE, UNIVERSE.VIEW_COUNT, UNIVERSE.USER_ID)
values (1, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR,
        'GOVERNMENT_AND_PUBLIC_INSTITUTION', '공공기관 관련 유니버스입니다.', 'PUBLIC', 1, 2, 3, '정책 유니버스', 1, 1);

insert into SPACE(SPACE.ID, SPACE.INNER_IMAGE_FILE_ID, SPACE.TITLE, SPACE.DESCRIPTION, SPACE.PARENT_SPACE_ID,
                  SPACE.UNIVERSE_ID, SPACE.SX, SPACE.SY, SPACE.EX, SPACE.EY, SPACE.CREATED_TIME,
                  SPACE.UPDATED_TIME, HIDDEN)
values (1, 4, 'space1', '유니버스의 스페이스-1', null, 1, 0.5, 0.5, 0.7, 0.6, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (2, 5, 'space2', '유니버스의 스페이스-2', null, 1, 0.4, 0.2, 0.5, 0.1, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (3, 6, 'space3', '스페이스1의 스페이스-1', 1, 1, 0.2, 0.3, 0.4, 0.2, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (4, 7, 'space4', '스페이스2의 스페이스-1', 2, 1, 0.7, 0.4, 0.3, 0.3, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (5, 8, 'space5', '스페이스2의 스페이스-2', 2, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00', false);

insert into PIECE(PIECE.ID, PIECE.INNER_IMAGE_FILE_ID, PIECE.TITLE, PIECE.DESCRIPTION, PIECE.PARENT_SPACE_ID,
                  PIECE.UNIVERSE_ID, PIECE.SX, PIECE.SY, PIECE.EX, PIECE.EY,
                  PIECE.CREATED_TIME, PIECE.UPDATED_TIME, HIDDEN)
values (1, null, 'PIECE1', '유니버스의 엘리먼트-1', null, 1, 0.5, 0.5, 0.7, 0.6, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (2, null, 'PIECE2', '스페이스1의 엘리먼트-1', 1, 1, 0.4, 0.2, 0.5, 0.1, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (3, null, 'PIECE3', '스페이스3의 엘리먼트-1', 3, 1, 0.2, 0.3, 0.4, 0.2, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (4, 9, 'PIECE4', '스페이스4의 엘리먼트-1', 4, 1, 0.7, 0.4, 0.3, 0.3, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (5, null, 'PIECE5', '스페이스4의 엘리먼트-2', 4, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (6, null, 'PIECE6', '스페이스5의 엘리먼트-1', 5, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00', false),
       (7, null, 'PIECE7', '스페이스5의 엘리먼트-2', 5, 1, 0.8, 0.1, 0.2, 0.4, '2025-06-06 17:00', '2025-06-06 17:00', false);

INSERT INTO SOUND(ID, AUDIO_FILE_ID, TITLE, DESCRIPTION, HIDDEN, PIECE_ID, CREATED_TIME, UPDATED_TIME)
VALUES (1, 10, '물소리', '잔잔한 시냇물 소리를 담았습니다.', true, 4, '2025.06.09 10:30', '2025.06.09 10:30'),
       (2, 11, '숲속 새소리', '숲속에서 들려오는 다양한 새들의 지저귐입니다.', true, 4, '2025.06.09 10:31', '2025.06.09 10:31'),
       (3, 12, '빗소리', '창밖으로 들리는 부드러운 빗소리입니다.', true, 4, '2025.06.09 10:32', '2025.06.09 10:32'),
       (4, 13, '모닥불', '불꽃이 타오르며 나무가 타는 따뜻한 소리입니다.', true, 4, '2025.06.09 10:33', '2025.06.09 10:33'),
       (5, 14, '파도소리', '해변에 밀려오는 파도소리를 담았습니다.', true, 5, '2025.06.09 10:34', '2025.06.09 10:34'),
       (6, 15, '카페 배경음', '조용한 카페에서 사람들의 대화와 잔 소리가 어우러진 환경음입니다.', true, 5, '2025.06.09 10:35', '2025.06.09 10:35'),
       (7, 16, '산책길', '자연 속 산책길에서 들리는 발자국 소리와 새소리입니다.', true, 5, '2025.06.09 10:36', '2025.06.09 10:36'),
       (8, 17, '도시의 아침', '도시에서 아침에 들리는 자동차 소리와 사람들의 움직임입니다.', true, 5, '2025.06.09 10:37', '2025.06.09 10:37'),
       (9, 18, '바람 소리', '들판을 스치는 부드러운 바람 소리입니다.', true, 6, '2025.06.09 10:38', '2025.06.09 10:38'),
       (10, 19, '명상 종소리', '마음을 가라앉히는 명상용 종소리입니다.', true, 6, '2025.06.09 10:39', '2025.06.09 10:39'),
       (11, 20, '기차역', '기차가 도착하고 출발하는 생동감 있는 역 소리입니다.', true, 7, '2025.06.09 10:40', '2025.06.09 10:40');
