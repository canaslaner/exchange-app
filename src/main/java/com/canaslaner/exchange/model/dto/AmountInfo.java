package com.canaslaner.exchange.model.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AmountInfo {

    private BigDecimal amount;
    private String currency;

    public static AmountInfo of(final BigDecimal amount, final String currency) {
        return AmountInfo.builder().amount(amount).currency(currency).build();
    }
}
