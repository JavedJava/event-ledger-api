package com.example.event_ledger_api.dto;

import com.example.event_ledger_api.model.EventType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public record EventRequest(

        @NotBlank
        String eventId,

        @NotBlank
        String accountId,

        @NotNull
        EventType type,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        @NotBlank
        String currency,

        @NotNull
        Instant eventTimestamp,

        Map<String, Object> metadata
) {
}