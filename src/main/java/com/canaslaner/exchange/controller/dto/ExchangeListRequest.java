package com.canaslaner.exchange.controller.dto;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.canaslaner.exchange.validation.ValidExchangeListRequest;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ValidExchangeListRequest
public class ExchangeListRequest {

    private String transactionId;

    private Instant startDate;
    private Instant endDate;
}
