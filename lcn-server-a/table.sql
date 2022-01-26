create table test_order(
    `id` bigint auto_increment not null,
    `status` tinyint not null,
    primary key(`id`)
)Engine = InnoDB CHARACTER SET = utf8mb4;

INSERT test_order VALUE(1,1);