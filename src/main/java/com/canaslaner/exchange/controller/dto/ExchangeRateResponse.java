package com.canaslaner.exchange.controller.dto;

import com.canaslaner.exchange.model.dto.AmountInfo;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ExchangeRateResponse {

    private List<AmountInfo> rates;
}
