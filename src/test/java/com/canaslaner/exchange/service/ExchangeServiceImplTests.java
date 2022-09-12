package com.canaslaner.exchange.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.canaslaner.exchange.exception.AppException;
import com.canaslaner.exchange.service.client.ExchangeRateClient;
import com.canaslaner.exchange.service.client.dto.ExchangeRateLatestResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceImplTests {

    @InjectMocks
    private ExchangeServiceImpl exchangeService;

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @BeforeEach
    public void setup() throws AppException {
        Mockito.clearAllCaches();
    }

    @Test
    void testGetExchangeRates_whenBaseInTheSingleTargetCurrencies_thenShouldNotCallService() throws AppException {
        // given
        final String base = "TRY";
        final List<String> targetCurrencies = new ArrayList<>();
        targetCurrencies.add(base);

        // when
        final var result = exchangeService.getExchangeRates(base, targetCurrencies);

        // then
        Mockito.verify(exchangeRateClient, Mockito.never())
                .latest(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetExchangeRates_whenServiceGotException_thenShouldThrowAppException() {
        // given
        final BigDecimal amount = BigDecimal.ONE;
        final String base = "TRY";
        final List<String> targetCurrencies = new ArrayList<>();
        targetCurrencies.add("USD");

        Mockito.doThrow(new RuntimeException()).when(exchangeRateClient).latest(base, "USD", amount);

        // when
        AppException exception = assertThrows(AppException.class,
                () -> exchangeService.getExchangeRates(base, targetCurrencies));

        // then
        Mockito.verify(exchangeRateClient)
                .latest(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any());
        Assertions.assertEquals("exchangeService.providerServiceError", exception.getMessage());
    }

    @Test
    void testGetExchangeRates_whenServiceReturnsNull_thenShouldThrowAppException() {
        // given
        final BigDecimal amount = BigDecimal.ONE;
        final String base = "CAD";
        final List<String> targetCurrencies = new ArrayList<>();
        targetCurrencies.add("USD");

        Mockito.doReturn(null).when(exchangeRateClient).latest(base, "USD", amount);

        // when
        AppException exception = assertThrows(AppException.class,
                () -> exchangeService.getExchangeRates(base, targetCurrencies));

        // then
        Mockito.verify(exchangeRateClient)
                .latest(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any());
        Assertions.assertEquals("exchangeService.providerServiceFail", exception.getMessage());
    }

    @Test
    void testGetExchangeRates_whenServiceResponseSuccessIsFalse_thenShouldThrowAppException() {
        // given
        final BigDecimal amount = BigDecimal.ONE;
        final String base = "EUR";
        final List<String> targetCurrencies = new ArrayList<>();
        targetCurrencies.add("USD");

        Mockito.doReturn(ExchangeRateLatestResponse.builder().build()).when(exchangeRateClient)
                .latest(base, "USD", amount);

        // when
        AppException exception = assertThrows(AppException.class,
                () -> exchangeService.getExchangeRates(base, targetCurrencies));

        // then
        Mockito.verify(exchangeRateClient)
                .latest(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any());
        Assertions.assertEquals("exchangeService.providerServiceFail", exception.getMessage());
    }
}
