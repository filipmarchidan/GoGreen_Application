create table achievements (id integer not null, achievement_name varchar(255) not null, achievement_value integer not null, primary key (id)) engine=InnoDB
create table activities (id integer not null, activity_amount integer not null, activity_type integer not null, date_time varchar(255) not null, user_id integer, primary key (id)) engine=InnoDB
create table activitytypes (id integer not null, activity_name varchar(255) not null, co2_savings integer not null, primary key (id)) engine=InnoDB
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
insert into hibernate_sequence values ( 1 )
create table users (id integer not null, email varchar(255) not null, password varchar(255) not null, solar_panel bit, totalscore integer, primary key (id)) engine=InnoDB
create table users_achievements (users_id integer not null, achievements_id integer not null, primary key (users_id, achievements_id)) engine=InnoDB
create table users_friends (user_id integer not null, friends_id integer not null, primary key (user_id, friends_id)) engine=InnoDB
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table activities add constraint FKq6cjukylkgxdjkm9npk9va2f2 foreign key (user_id) references users (id)
alter table users_achievements add constraint FKg4hfidvrr1tiwevic7kfkpebf foreign key (achievements_id) references achievements (id)
alter table users_achievements add constraint FKcfk941xlfg26nkymvu3pikrox foreign key (users_id) references users (id)
alter table users_friends add constraint FKo51ktjiheso8mkdd5n4pdf9f3 foreign key (friends_id) references users (id)
alter table users_friends add constraint FKry5pun2eg852sbl2l50p236bo foreign key (user_id) references users (id)
CREATE TRIGGER RemoveActivityScore AFTER DELETE ON activities FOR EACH ROW UPDATE users SET totalscore = totalscore - OLD.activity_amount * (SELECT co2_savings from activitytypes WHERE id = OLD.activity_type) WHERE users.id = OLD.user_id
CREATE TRIGGER AddActivityScore AFTER INSERT ON activities FOR EACH ROW UPDATE users SET totalscore = totalscore + NEW.activity_amount * (SELECT co2_savings from activitytypes WHERE id = NEW.activity_type) WHERE users.id = NEW.user_id