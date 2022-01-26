create table test_pay(
    `id` bigint auto_increment not null,
    `status` tinyint not null,
    primary key(`id`)
)Engine = InnoDB CHARACTER SET = utf8mb4;

INSERT test_pay VALUE(1,1);