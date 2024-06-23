create schema if not exists main_schema;

set search_path to main_schema;

create table if not exists users (
    user_id serial primary key,
    username varchar(50) not null unique,
    created_at timestamp not null default current_timestamp
);

create table if not exists authentication (
    auth_id serial primary key,
    user_id int not null,
    password_hash char(60) not null, -- Assuming 60 characters for a bcrypt hash
    foreign key (user_id) references users(user_id) on delete cascade
);

create index idx_authentication_user_id on authentication(user_id);

create table if not exists emails (
    email_id serial primary key,
    user_id int not null,
    email varchar(255) not null,
    foreign key (user_id) references users(user_id) on delete cascade
);

create index idx_emails_user_id on emails(user_id);

create table if not exists forums (
    forum_id serial primary key,
    title varchar(100) not null,
    description text,
    created_at timestamp not null default current_timestamp,
    admin_id int,
    foreign key (admin_id) references users(user_id) on delete set null
);

create index idx_forums_admin_id on forums(admin_id);

create table if not exists posts (
    post_id serial primary key,
    forum_id int not null,
    title varchar(100) not null,
    content text not null,
    created_at timestamp not null default current_timestamp,
    created_by int not null,
    foreign key (forum_id) references forums(forum_id) on delete cascade,
    foreign key (created_by) references users(user_id) on delete set null
);

create index idx_posts_forum_id on posts(forum_id);
create index idx_posts_created_by on posts(created_by);

create table if not exists comments (
    comment_id serial primary key,
    post_id int not null,
    content text not null,
    created_at timestamp not null default current_timestamp,
    created_by int not null,
    foreign key (post_id) references posts(post_id) on delete cascade,
    foreign key (created_by) references users(user_id) on delete set null
);

create index idx_comments_post_id on comments(post_id);
create index idx_comments_created_by on comments(created_by);