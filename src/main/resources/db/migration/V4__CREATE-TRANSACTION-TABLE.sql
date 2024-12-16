CREATE TABLE "transaction" (
  "id" UUID PRIMARY KEY,
  "payer_wallet_id" UUID,
  "payee_wallet_id" UUID,
  "amount" decimal,
  "processed_at" timestamp
);