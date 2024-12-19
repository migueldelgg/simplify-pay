package SimplifyPay.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record RestErrorMessage (
    @JsonProperty("status_code") String statusCode, // Exibido como "status_code" no JSON
    String message
) {}
