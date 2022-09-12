package com.canaslaner.exchange.controller.dto;

import com.canaslaner.exchange.model.dto.AmountInfo;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.canaslaner.exchange.model.Conversion;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ConversionDto {

    private String transactionId;
    private AmountInfo sourceAmount;
    private AmountInfo targetAmount;
    private Instant createdDate;

    public static ConversionDto from(final Conversion conversion) {
        return ConversionDto.builder()
                .transactionId(conversion.getTransactionId())
                .sourceAmount(AmountInfo.of(conversion.getSourceAmount().getAmount(),
                        conversion.getSourceAmount().getCurrency()))
                .targetAmount(AmountInfo.of(conversion.getTargetAmount().getAmount(),
                        conversion.getTargetAmount().getCurrency()))
                .createdDate(conversion.getCreatedDate())
                .build();
    }
}

