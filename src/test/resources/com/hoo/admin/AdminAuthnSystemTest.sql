insert into SNS_ACCOUNT(ID, REAL_NAME, NICKNAME, EMAIL, SNS_ID, SNS_DOMAIN, CREATED_TIME, UPDATED_TIME)
values (1, '남상엽', 'leaf', 'leaf@example.com', 'SNS_ID', 'KAKAO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- password : testPassword(bcrypt encoded)
insert into ADMIN(ID, EMAIL, NICKNAME, PASSWORD, USERNAME, SNS_ACCOUNT_ID, ROLE)
values (1, 'leaf@example.com', 'leaf', '{bcrypt}$2a$12$FmEYMmx/F0phmK.0URWp7.gzntR21qY9KIH0vBtBO27d4AvpwLgZ2',
        'testAdmin', 1, 'SUPER_ADMIN');
