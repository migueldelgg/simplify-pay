CREATE TABLE "wallet" (
  "id" UUID PRIMARY KEY,
  "user_id" UUID UNIQUE,
  "type" "WALLET_TYPE",
  "created_at" timestamp,
  "updated_at" timestamp
);