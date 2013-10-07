
    drop table if exists partner;

    drop table if exists planner_request;

    drop table if exists system_user;

    create table partner (
        id bigint not null auto_increment,
        version bigint not null,
        enabled boolean not null,
        `password` varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
    );

    create table planner_request (
        id bigint not null auto_increment,
        version bigint not null,
        license_key varchar(255) not null,
        request_duration bigint not null,
        `timestamp` datetime not null,
        primary key (id)
    );

    create table system_user (
        id bigint not null auto_increment,
        version bigint not null,
        `password` varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
    );

    alter table partner 
        add constraint uc_partner_1 unique (username);

    alter table system_user 
        add constraint uc_system_user_1 unique (username);
