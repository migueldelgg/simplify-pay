package SimplifyPay.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record RestErrorMessage (
        @JsonProperty("status_phrase") String statusPhrase,
        String message
) {}
