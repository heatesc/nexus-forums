create schema if not exists main_schema;

set search_path to forum_schema;

create table if not exists Users (
    userId serial primary key,
    username varchar(50) not null unique,
    createdAt timestamp not null default current_timestamp
);

create table if not exists Authentication (
    userId int primary key,
    passwordHash char(60) not null, -- Assuming 60 characters for a bcrypt hash
    foreign key (userId) references users(userId) on delete cascade
);

create table if not exists Forums (
    forumId serial primary key,
    title varchar(100) not null,
    description text,
    createdAt timestamp not null default current_timestamp,
    adminId int,
    foreign key (adminId) references users(userId) on delete set null
);

create table if not exists Posts (
    postId serial primary key,
    forumId int not null,
    title varchar(100) not null,
    content text not null,
    createdAt timestamp not null default current_timestamp,
    createdBy int not null,
    foreign key (forumId) references forums(forumId) on delete cascade,
    foreign key (createdBy) references users(userId) on delete set null
);

create table if not exists Comments (
    commentId serial primary key,
    postId int not null,
    content text not null,
    createdAt timestamp not null default current_timestamp,
    createdBy int not null,
    foreign key (postId) references posts(postId) on delete cascade,
    foreign key (createdBy) references users(userId) on delete set null
);
