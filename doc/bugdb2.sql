CREATE TABLE roles(
  role_id       VARCHAR (3) PRIMARY KEY,
  role_name     VARCHAR (50) NOT NULL
);

CREATE TABLE users(
  user_id       VARCHAR (15) PRIMARY KEY,
  password      VARCHAR (15) NOT NULL,
  full_name     VARCHAR (50) NOT NULL,
  role_id       VARCHAR (3) NOT NULL REFERENCES roles(role_id),
  email         VARCHAR (50)
);

CREATE TABLE bug(
  id            INT PRIMARY KEY,
  assignee      VARCHAR (15) NOT NULL REFERENCES users(user_id),
  summary       VARCHAR (1000) NOT NULL,
  open_date     DATE NOT NULL,
  due_date      DATE NOT NULL DEFAULT '1970-01-01',
  close_date    DATE
);

CREATE TABLE detail(
  bug_id        INT REFERENCES bug(id),
  det_id        INT,
  det_date      DATE NOT NULL,
  user_id       VARCHAR (15) NOT NULL REFERENCES users(user_id),
  comment       VARCHAR (1000) NOT NULL,
  PRIMARY KEY (bug_id, det_id)
);

INSERT INTO roles (role_id, role_name) VALUES ('adm', 'Administrator');
INSERT INTO roles (role_id, role_name) VALUES ('mgr', 'Manager');
INSERT INTO roles (role_id, role_name) VALUES ('dev', 'Developer');
INSERT INTO roles (role_id, role_name) VALUES ('qat', 'Quality Assurance');

INSERT INTO users (user_id, password, full_name, role_id, email)
  VALUES('marge', 'kiss', 'Marge Simpson', 'adm', 'marge@simpsons.com');
INSERT INTO users (user_id, password, full_name, role_id, email)
  VALUES('monty', 'money', 'Monty Burns', 'mgr', 'monty@simpsons.com');
INSERT INTO users (user_id, password, full_name, role_id, email)
  VALUES('bart', 'cowabunga', 'Bart Simpson', 'dev', 'bart@simpsons.com');
INSERT INTO users (user_id, password, full_name, role_id, email)
  VALUES('lisa', 'flute', 'Lisa Simpson', 'dev', 'lisa@simpsons.com');
INSERT INTO users (user_id, password, full_name, role_id, email)
  VALUES('barney', 'burp', 'Barney Grumble', 'qat', 'barney@simpsons.com');
INSERT INTO users (user_id, password, full_name, role_id, email)
  VALUES('homer', 'donuts', 'Homer Simpson', 'qat', 'homer@simpsons.com');

INSERT INTO bug (id, assignee, summary, open_date, due_date)
  VALUES (1, 'bart', $$The mail system throws an exception when you try to send mail to a gmail address. $$, '2012-12-01', '2012-12-31');
INSERT INTO bug (id, assignee, summary, open_date, due_date)
  VALUES (2, 'bart', $$Exceptions don't seem to be showing up in the logs$$, '2012-12-05', '2012-12-31');
INSERT INTO bug (id, assignee, summary, open_date, due_date)
  VALUES (3, 'lisa', $$The new bug screen doesn't allow a manager to assign the bug to a different user.$$, '2012-12-02', '2012-12-31');
INSERT INTO bug (id, assignee, summary, open_date, due_date)
  VALUES (4, 'lisa', $$The login screen doesn't allow for users to reset their password if they forgot it$$, '2012-12-02', '2012-12-31');

INSERT INTO detail (bug_id, det_id, det_date, user_id, comment)
  VALUES (1, 1, '2012-12-01', 'homer', $$User called and was very upset, creating new bug$$);
INSERT INTO detail (bug_id, det_id, det_date, user_id, comment)
  VALUES (2, 1, '2012-12-05', 'homer', $$User called and was very upset, creating new bug$$);
INSERT INTO detail (bug_id, det_id, det_date, user_id, comment)
  VALUES (3, 1, '2012-12-02', 'barney', $$User called and was very upset, creating new bug$$);
INSERT INTO detail (bug_id, det_id, det_date, user_id, comment)
  VALUES (4, 1, '2012-12-02', 'barney', $$User called and was very upset, creating new bug$$);

INSERT INTO detail (bug_id, det_id, det_date, user_id, comment)
  VALUES (1, 2, '2012-12-02', 'monty', $$Assigned to bart$$);
INSERT INTO detail (bug_id, det_id, det_date, user_id, comment)
  VALUES (2, 2, '2012-12-06', 'monty', $$Assigned to bart$$);
INSERT INTO detail (bug_id, det_id, det_date, user_id, comment)
  VALUES (3, 2, '2012-12-03', 'monty', $$Assigned to lisa$$);
INSERT INTO detail (bug_id, det_id, det_date, user_id, comment)
  VALUES (4, 2, '2012-12-03', 'monty', $$Assigned to lisa$$);
