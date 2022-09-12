package com.canaslaner.exchange.controller;

import com.canaslaner.exchange.controller.dto.ExchangeRateRequest;
import com.canaslaner.exchange.exception.AppException;
import com.canaslaner.exchange.model.dto.AmountInfo;
import com.canaslaner.exchange.service.ExchangeService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.canaslaner.testutil.MvcUtils;

@SpringBootTest
class ExchangeControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ExchangeService exchangeService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws AppException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Mockito.doReturn(List.of(AmountInfo.of(BigDecimal.TEN, "TRY"))).when(exchangeService)
                .getExchangeRates(ArgumentMatchers.eq("EUR"), ArgumentMatchers.anyList());
    }

    @Test
    void testExchangeRate_whenRequestIsValid_thenReturnRates() throws Exception {
        // given
        final var request = ExchangeRateRequest.builder()
                .base("EUR").targetCurrencies(List.of("TRY"))
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/exchange/rate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(MvcUtils.createJsonContent(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @ParameterizedTest
    @CsvSource({
            ", EUR, base, must not be null",
            "TRY, , targetCurrencies, must not be empty"
    })
    void testExchangeRate_whenFieldIsInvalid_thenReturnBadRequest(final String base, final String targetCurrency,
            final String expectedFieldValue, final String expectedMessageValue) throws Exception {
        // given
        final var request = ExchangeRateRequest.builder()
                .base(base).targetCurrencies(targetCurrency != null ? List.of(targetCurrency) : null)
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/exchange/rate")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(MvcUtils.createJsonContent(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.violations[0].field").value(expectedFieldValue))
                .andExpect(MockMvcResultMatchers.jsonPath("$.violations[0].message").value(expectedMessageValue));
    }
}
