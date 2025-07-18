insert into UNIVERSE(UNIVERSE.ID, UNIVERSE.CREATED_TIME, UNIVERSE.UPDATED_TIME, UNIVERSE.CATEGORY, UNIVERSE.DESCRIPTION,
                     UNIVERSE.PUBLIC_STATUS, UNIVERSE.THUMB_MUSIC_FILE_ID, UNIVERSE.THUMBNAIL_FILE_ID,
                     universe.INNER_IMAGE_FILE_ID, UNIVERSE.TITLE, UNIVERSE.VIEW_COUNT)
values (1, CURRENT_TIMESTAMP - INTERVAL 9 HOUR, CURRENT_TIMESTAMP - INTERVAL 9 HOUR,
        'GOVERNMENT_AND_PUBLIC_INSTITUTION', '유니버스는 우주입니다.', 'PUBLIC', 1, 2, 3, '우주', 0);

insert into PIECE(PIECE.ID, PIECE.CREATED_TIME, PIECE.UPDATED_TIME, PIECE.DESCRIPTION, PIECE.INNER_IMAGE_FILE_ID,
                  PIECE.TITLE, PIECE.SX, PIECE.SY, PIECE.EX, PIECE.EY, PIECE.PARENT_SPACE_ID, PIECE.UNIVERSE_ID, HIDDEN)
values (1, '2025.06.09 10:30', '2025.06.09 10:30', '피스는 조각입니다.', null, '조각', 0.3, 0.2, 0.2, 0.3, -1, 1, false);

INSERT INTO SOUND(ID, AUDIO_FILE_ID, TITLE, DESCRIPTION, HIDDEN, PIECE_ID, CREATED_TIME, UPDATED_TIME)
VALUES (1, 1, '물소리', '잔잔한 시냇물 소리를 담았습니다.', true, 1, '2025.06.09 10:30', '2025.06.09 10:30'),
       (2, 2, '숲속 새소리', '숲속에서 들려오는 다양한 새들의 지저귐입니다.', true, 1, '2025.06.09 10:31', '2025.06.09 10:31'),
       (3, 3, '빗소리', '창밖으로 들리는 부드러운 빗소리입니다.', true, 1, '2025.06.09 10:32', '2025.06.09 10:32'),
       (4, 4, '모닥불', '불꽃이 타오르며 나무가 타는 따뜻한 소리입니다.', true, 1, '2025.06.09 10:33', '2025.06.09 10:33'),
       (5, 5, '파도소리', '해변에 밀려오는 파도소리를 담았습니다.', true, 1, '2025.06.09 10:34', '2025.06.09 10:34'),
       (6, 6, '카페 배경음', '조용한 카페에서 사람들의 대화와 잔 소리가 어우러진 환경음입니다.', true, 1, '2025.06.09 10:35', '2025.06.09 10:35'),
       (7, 7, '산책길', '자연 속 산책길에서 들리는 발자국 소리와 새소리입니다.', true, 1, '2025.06.09 10:36', '2025.06.09 10:36'),
       (8, 8, '도시의 아침', '도시에서 아침에 들리는 자동차 소리와 사람들의 움직임입니다.', true, 1, '2025.06.09 10:37', '2025.06.09 10:37'),
       (9, 9, '바람 소리', '들판을 스치는 부드러운 바람 소리입니다.', true, 1, '2025.06.09 10:38', '2025.06.09 10:38'),
       (10, 10, '명상 종소리', '마음을 가라앉히는 명상용 종소리입니다.', true, 1, '2025.06.09 10:39', '2025.06.09 10:39'),
       (11, 11, '기차역', '기차가 도착하고 출발하는 생동감 있는 역 소리입니다.', true, 1, '2025.06.09 10:40', '2025.06.09 10:40');
