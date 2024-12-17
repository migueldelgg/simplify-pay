CREATE SEQUENCE user_id_seq;

CREATE TABLE "user" (
  "id" INTEGER NOT NULL DEFAULT nextval('user_id_seq'),
  "name" varchar,
  "document" varchar UNIQUE,
  "email" varchar UNIQUE,
  "password" varchar,
  "created_at" timestamp,
  "updated_at" timestamp,
  PRIMARY KEY (id)
);