package com.canaslaner.exchange.controller.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ExchangeRateRequest {

    @NotNull
    private String base;
    @NotEmpty
    private List<String> targetCurrencies;
}
