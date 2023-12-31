create table groups (
    group_id int8 generated by default as identity,
    group_name varchar(255),
    primary key (group_id)
    )

create table groups_subjects (
    subject_id int8 not null,
    group_id int8 not null
    )

create table ratings (
    rating_id int8 generated by default as identity,
    comment varchar(255),
    mark int4,
    student_id int8,
    subject_id int8,
    task_id int8,
    primary key (rating_id)
    )

create table student (
    student_id int8 generated by default as identity,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    phone_number varchar(255),
    group_id int8,
    primary key (student_id)
    )

create table subject (
    subject_id int8 generated by default as identity,
    name varchar(255),
    primary key (subject_id)
    )

create table task (
    task_id int8 generated by default as identity,
    dead_time timestamp,
    description varchar(255),
    name varchar(255),
    start_time timestamp,
    group_id int8,
    teacher_id int8,
    primary key (task_id)
    )

create table task_answer (
    task_answer_id int8 generated by default as identity,
    comment varchar(255),
    date timestamp,
    student_id int8,
    task_id int8,
    primary key (task_answer_id)
    )

create table tasks_answers_files (
    task_answer_id int8 not null,
    file_uri varchar(255)
    )

create table tasks_files (
    task_id int8 not null,
    file_uri varchar(255)
    )

create table teacher (
    teacher_id int8 generated by default as identity,
    academic_degree varchar(255),
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    phone_number varchar(255),
    primary key (teacher_id)
    )

create table teachers_groups (
    teacher_id int8 not null,
    group_id int8 not null
    )

create table teachers_subjects (
    teacher_id int8 not null,
    subject_id int8 not null
    )