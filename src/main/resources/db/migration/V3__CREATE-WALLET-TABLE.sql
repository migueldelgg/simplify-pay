CREATE TABLE "wallet" (
  "id" uuid PRIMARY KEY,
  "user_id" integer UNIQUE,
  "type" "WALLET_TYPE",
  "created_at" timestamp,
  "updated_at" timestamp
);