ALTER TABLE "wallet" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "transaction" ADD FOREIGN KEY ("payer_wallet_id") REFERENCES "wallet" ("id");

ALTER TABLE "transaction" ADD FOREIGN KEY ("payee_wallet_id") REFERENCES "wallet" ("id");