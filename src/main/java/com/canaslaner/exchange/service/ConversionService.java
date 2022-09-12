package com.canaslaner.exchange.service;

import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Pageable;
import com.canaslaner.exchange.model.Conversion;
import com.canaslaner.exchange.model.dto.AmountInfo;

public interface ConversionService {

    List<Conversion> findByTransactionId(String transactionId, Pageable pageable);

    List<Conversion> findByDate(Instant startDate, Instant endDate, Pageable pageable);

    List<Conversion> save(final AmountInfo sourceAmountInfo, final List<AmountInfo> amountInfos,
            final String transactionId, final Instant createDate);
}
