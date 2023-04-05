DROP database if exists text_board;
create database text_board;
use text_board;

CREATE table article(
id int unsigned not null primary key auto_increment,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
title CHAR(100) NOT NULL,
`body` TEXT NOT NULL
);

INSERT into article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT("제목", RAND()),
`body` = CONCAT("내용", RAND());