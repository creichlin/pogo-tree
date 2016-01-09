
CREATE TABLE object (
  id int(11) PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE list (
  id int(11) PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE objectvalue (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  type int(11) NOT NULL,
  parent int(11) NOT NULL,
  name VARCHAR(256) NOT NULL,
  
  string VARCHAR DEFAULT NULL,
  integer int(11) DEFAULT NULL,
  
  FOREIGN KEY(parent) REFERENCES object(id)
);

CREATE INDEX objectvalue_name ON objectvalue(name);

CREATE TABLE listvalue (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  type int(11) NOT NULL,
  parent int(11) NOT NULL,
  index int(11) NOT NULL,

  string VARCHAR DEFAULT NULL,
  integer int(11) DEFAULT NULL,
  
  FOREIGN KEY(parent) REFERENCES list(id),
);

CREATE INDEX listvalue_index ON listvalue(index);

