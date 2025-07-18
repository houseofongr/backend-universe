set foreign_key_checks = 0;
TRUNCATE TABLE CATEGORY;
set foreign_key_checks = 1;

INSERT INTO CATEGORY(ID, TITLE_KOR, TITLE_ENG)
values (1, '생활','LIFE'),
       (2, '공공','PUBLIC'),
       (3, '정부','GOVERNMENT');