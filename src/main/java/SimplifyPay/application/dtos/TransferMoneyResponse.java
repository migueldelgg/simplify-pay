package SimplifyPay.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TransferMoneyResponse (
        @JsonProperty(value = "payer_wallet_id") UUID payer,
        @JsonProperty(value = "payee_wallet_id") UUID payee,
        BigDecimal value){
}
