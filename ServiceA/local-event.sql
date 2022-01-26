CREATE DATABASE `servera`
CREATE TABLE localEvent (
                            `id` bigint not null auto_increment,
                            eventType VARCHAR(20),
                            payload varchar(2000),
                            status tinyint,
                            createDate datetime,
                            updateDate datetime,
                            primary key(`id`)
) ENGINE=InnoDB CHARSET=utf8 COMMENT='本地事件表';
