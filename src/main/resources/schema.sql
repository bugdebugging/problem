drop table if exists test_cases;
drop table if exists problems;

create table problems
(
    id                 bigint auto_increment,
    description        varchar(2048),
    input_description  varchar(1024),
    output_description varchar(1024),
    memory             integer,
    time               integer,
    name               varchar(255),
    score              integer,
    author             varchar(64),
    created_at         timestamp not null,
    updated_at         timestamp not null,
    primary key (id)
);
create table test_cases
(
    problem_id       bigint not null,
    name             varchar(255),
    input_file_path  varchar(255),
    output_file_path varchar(255),
    output_hash      varchar(255),
    foreign key (problem_id) references problems (id)
);