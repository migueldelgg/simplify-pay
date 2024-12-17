CREATE TABLE "transaction" (
  "id" UUID PRIMARY KEY,
  "payer_wallet_id" UUID UNIQUE,
  "payee_wallet_id" UUID UNIQUE,
  "amount" decimal,
  "processed_at" timestamp
);