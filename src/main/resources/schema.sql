-- Create Tables
DROP TABLE if EXISTS item CASCADE;
DROP TABLE if EXISTS to_do CASCADE;
DROP TABLE if EXISTS todo_user CASCADE;

CREATE TABLE item (item_id bigint not null, activity varchar(255), status integer, to_do_id bigint, primary key (item_id));
CREATE TABLE to_do (to_do_id bigint not null, type varchar(255), user_id bigint, primary key (to_do_id));
CREATE TABLE todo_user (user_id bigint not null, user_name varchar(255), primary key (user_id));

-- Alter table for required constraints
ALTER TABLE item add constraint FKi2ijcv3yxu2ljw5d2tjwge8qw foreign key (to_do_id) references to_do;
ALTER TABLE to_do add constraint FKsjso2phfn2gtjor3lbqxhn5nq foreign key (user_id) references todo_user;

-- Sequence
DROP SEQUENCE if exists hibernate_sequence;
CREATE SEQUENCE hibernate_sequence start with 5 increment by 1;