# DB 생성
DROP database if exists text_board;
create database text_board;

# DB 선택
use text_board;

# 게시물 테이블 생성
CREATE table article(
id int unsigned not null primary key auto_increment,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
title CHAR(100) NOT NULL,
`body` TEXT NOT NULL
);

# 게시물 테이블 테스트 데이터
INSERT into article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT("제목", RAND()),
`body` = CONCAT("내용", RAND());

# 멤버 테이블 생성
CREATE table `member`(
id int(10) unsigned not null primary key auto_increment,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
loginId CHAR(20) NOT NULL,
loginPw CHAR(100) NOT NULL,
`name` CHAR(200) NOT NULL
);

