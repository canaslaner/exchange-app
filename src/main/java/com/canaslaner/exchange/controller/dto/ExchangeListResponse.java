package com.canaslaner.exchange.controller.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ExchangeListResponse {

    private List<ConversionDto> conversions;
}
