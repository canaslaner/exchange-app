package com.canaslaner.exchange.service.client.dto;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ExchangeRateLatestResponse {

    private Boolean success;
    private String base;
    private Map<String, BigDecimal> rates;
}
