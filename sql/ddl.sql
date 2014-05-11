
    drop table if exists partner;

    drop table if exists planner_request;

    drop table if exists setting;

    drop table if exists system_user;

    create table partner (
        id bigint not null auto_increment,
        version bigint not null,
        enabled bit not null,
        `PASSWORD` varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
    );

    create table planner_request (
        id bigint not null auto_increment,
        version bigint not null,
        license_key varchar(255) not null,
        request_duration bigint not null,
        `TIMESTAMP` bigint not null,
        primary key (id)
    );

    create table setting (
        id bigint not null auto_increment,
        version bigint not null,
        `KEY` varchar(255) not null,
        `VALUE` varchar(255),
        primary key (id)
    );

    create table system_user (
        id bigint not null auto_increment,
        version bigint not null,
        `PASSWORD` varchar(255) not null,
        realm_code bigint,
        username varchar(255) not null,
        primary key (id)
    );

    alter table partner 
        add constraint UK_pc69bebkrf0aqiiyjrb2683m2  unique (username);

    alter table setting 
        add constraint UK_jtfd2bh4aetvc6palq0pw2bnx  unique (`KEY`);

    alter table system_user 
        add constraint UK_74y7xiqrvp39wycn0ron4xq4h  unique (username);
