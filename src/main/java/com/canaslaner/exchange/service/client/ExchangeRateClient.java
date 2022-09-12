package com.canaslaner.exchange.service.client;

import com.canaslaner.exchange.service.client.dto.ExchangeRateLatestResponse;
import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exchangeRateClient", url = "${exchangeRateClient.feign.url}")
public interface ExchangeRateClient {

    // example service url : https://api.exchangerate.host/latest?base=USD&symbols=USD,EUR,CZK,TRY&amount=1000
    @GetMapping(value = "/latest")
    ExchangeRateLatestResponse latest(@RequestParam(value = "base") String base,
            @RequestParam(value = "symbols") String currencies,
            @RequestParam(value = "amount") BigDecimal amount);
}