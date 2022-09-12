package com.canaslaner.exchange.service;

import com.canaslaner.exchange.service.client.dto.ExchangeRateLatestResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.canaslaner.exchange.exception.AppException;
import com.canaslaner.exchange.model.Conversion;
import com.canaslaner.exchange.model.dto.AmountInfo;
import com.canaslaner.exchange.service.client.ExchangeRateClient;
import com.canaslaner.exchange.util.Constants;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRateClient exchangeRateClient;
    private final ConversionService conversionService;

    @Override
    public List<AmountInfo> getExchangeRates(final String sourceCurrency, final List<String> targetCurrencies)
            throws AppException {
        return getAmountInfosByLatestExchange(BigDecimal.ONE, sourceCurrency, targetCurrencies);
    }

    @Override
    public Pair<String, List<Conversion>> exchange(final AmountInfo sourceAmountInfo,
            final List<String> targetCurrencies)
            throws AppException {
        final List<AmountInfo> amountInfos = getAmountInfosByLatestExchange(sourceAmountInfo.getAmount(),
                sourceAmountInfo.getCurrency(), targetCurrencies);
        final String transactionId = UUID.randomUUID().toString();

        return Pair.of(transactionId,
                conversionService.save(sourceAmountInfo, amountInfos, transactionId, Instant.now()));
    }

    private List<AmountInfo> getAmountInfosByLatestExchange(final BigDecimal amount, final String currency,
            final List<String> targetCurrencies) throws AppException {
        // remove source currency from desired list
        targetCurrencies.remove(currency);
        final ExchangeRateLatestResponse exchangeRateLatestResponse = callLatestExchangeServiceSafe(amount, currency,
                targetCurrencies);

        return exchangeRateLatestResponse == null || exchangeRateLatestResponse.getRates() == null
                ? Collections.emptyList() : exchangeRateLatestResponse.getRates().entrySet().stream()
                .map(entry -> AmountInfo.of(entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
    }

    private ExchangeRateLatestResponse callLatestExchangeServiceSafe(final BigDecimal amount, final String currency,
            final List<String> targetCurrencies) throws AppException {
        ExchangeRateLatestResponse exchangeRateLatestResponse = null;

        if (!CollectionUtils.isEmpty(targetCurrencies)) {
            final String commaSeperatedCurrencies = String
                    .join(Constants.COMMA_DELIMITER, targetCurrencies).toUpperCase();

            try {
                exchangeRateLatestResponse = exchangeRateClient
                        .latest(currency.toUpperCase(), commaSeperatedCurrencies, amount);
            } catch (final Exception e) {
                log.warn("Got exception while calling provider service: {}", e.getMessage());
                throw new AppException("exchangeService.providerServiceError");
            }

            if (Objects.isNull(exchangeRateLatestResponse)
                    || BooleanUtils.isNotTrue(exchangeRateLatestResponse.getSuccess())) {
                throw new AppException("exchangeService.providerServiceFail");
            }
        }

        return exchangeRateLatestResponse;
    }
}