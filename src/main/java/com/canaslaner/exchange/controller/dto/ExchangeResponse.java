package com.canaslaner.exchange.controller.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.canaslaner.exchange.model.dto.AmountInfo;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ExchangeResponse {

    private String transactionId;
    private List<AmountInfo> amounts;
}
