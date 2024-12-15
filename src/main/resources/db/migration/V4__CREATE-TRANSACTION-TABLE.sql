CREATE TABLE "transaction" (
  "id" uuid PRIMARY KEY,
  "payer_wallet_id" uuid,
  "payee_wallet_id" uuid,
  "amount" decimal,
  "processed_at" timestamp
);