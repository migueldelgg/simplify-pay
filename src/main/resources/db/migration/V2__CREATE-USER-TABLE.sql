CREATE TABLE "user" (
  "id" integer PRIMARY KEY,
  "name" varchar,
  "document" varchar UNIQUE,
  "email" varchar UNIQUE,
  "password" varchar,
  "created_at" timestamp,
  "updated_at" timestamp
);