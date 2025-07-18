insert into FILE(ID, ABSOLUTE_PATH, REAL_FILE_NAME, FILE_SYSTEM_NAME, IS_DELETED, FILE_SIZE, CREATED_TIME, UPDATED_TIME)
values
    ('1', '/tmp/public/images', 'test.png', 'test-1234.png', 0, 1234, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('2', '/tmp/public/images', 'test2.png', 'test-1235.png', 0, 1234, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('3', '/tmp/public/images', 'test3.png', 'test-1236.png', 0, 1234, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('4', '/tmp/public/images', 'test4.png', 'test-1237.png', 0, 1234, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('5', '/tmp/public/images', 'test5.png', 'test-1238.png', 0, 1234, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);