drop table IF EXISTS  hit;

create TABLE IF NOT EXISTS hit (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL ,
  app VARCHAR(512) NOT NULL,
  uri VARCHAR(512) NOT NULL,
  ip VARCHAR(512) NOT NULL,
  created TIMESTAMP WITHOUT TIME ZONE,
  CONSTRAINT pk_hit PRIMARY KEY(id)
);

