-- 유저
insert into admin_account (user_id, user_password, role_types, nickname, email, memo, create_at, create_by, modified_at, modified_by) values
('jinwoo', '{noop}asdf1234', 'ADMIN', 'Jinwoo', 'jinwoo@mail.com', 'I am Jinwoo.', now(), 'jinwoo', now(), 'jinwoo'),
('mark', '{noop}asdf1234', 'MANAGER', 'Mark', 'mark@mail.com', 'I am Mark.', now(), 'jinwoo', now(), 'jinwoo'),
('susan', '{noop}asdf1234', 'MANAGER,DEVELOPER', 'Susan', 'Susan@mail.com', 'I am Susan.', now(), 'jinwoo', now(), 'jinwoo'),
('jim', '{noop}asdf1234', 'USER', 'Jim', 'jim@mail.com', 'I am Jim.', now(), 'jinwoo', now(), 'jinwoo')
;