package com.canaslaner.exchange.service;

import java.util.List;
import org.springframework.data.util.Pair;
import com.canaslaner.exchange.exception.AppException;
import com.canaslaner.exchange.model.Conversion;
import com.canaslaner.exchange.model.dto.AmountInfo;

public interface ExchangeService {

    List<AmountInfo> getExchangeRates(final String base, final List<String> targetCurrencies) throws AppException;

    Pair<String, List<Conversion>> exchange(final AmountInfo amountInfo, final List<String> targetCurrencies)
            throws AppException;
}
