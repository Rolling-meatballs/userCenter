-- auto-generated definition
create table "user"
(
    id            bigint generated always as identity
        constraint id
            primary key,
    username      varchar(256)             default ''::character varying not null,
    user_account  varchar(256)                                           not null,
    avatar_url    varchar(1024),
    gender        smallint                 default 1                     not null,
    user_password varchar(512)                                           not null,
    phone         varchar(128),
    email         varchar(512),
    user_status   smallint                 default 0                     not null,
    create_time   timestamp with time zone default date(now())           not null,
    update_time   timestamp with time zone default date(now())           not null,
    is_delete     smallint                 default 0                     not null,
    user_role     integer                  default 0                     not null
);

comment on column "user".id is 'id';

comment on column "user".username is 'nikename';

comment on column "user".user_account is '用户名';

comment on column "user".avatar_url is '头像';

comment on column "user".gender is '性别';

comment on column "user".user_password is '密码';

comment on column "user".phone is 'phone';

comment on column "user".user_status is '是否删除';

comment on column "user".user_role is '用户角色；0 - 普通用户  0 - 管理员 2 - 超级管理员';

alter table "user"
    owner to postgres;
