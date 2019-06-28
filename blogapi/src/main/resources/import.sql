--this script initiates db for h2 db (used in test profile)
insert into user (account_status, email, first_name, last_name) values ('CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (account_status, email, first_name) values ('NEW', 'brian@domain.com', 'Brian')
insert into user (account_status, email) values ('REMOVED', 'test@domain.com')
insert into user (account_status, email) values ('CONFIRMED', 'john123@domain.com')

insert into blog_post (id, entry, user_id) values (1, 'entry', 2)
insert into blog_post (id, entry, user_id) values (2, 'entry', 4)
