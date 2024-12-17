package SimplifyPay.application.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransferMoneyRequest (
    BigDecimal value, 
    @JsonProperty(value="payer") Integer payerId,
    @JsonProperty(value="payee") Integer payeeId 
){}
