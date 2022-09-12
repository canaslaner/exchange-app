package com.canaslaner.exchange.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.Min;
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
public class ExchangeRequest {

    @Min(0)
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String currency;
    @NotEmpty
    private List<String> targetCurrencies;
}
