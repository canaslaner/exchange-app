package com.canaslaner.exchange.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.canaslaner.exchange.controller.dto.ConversionDto;
import com.canaslaner.exchange.controller.dto.ExchangeListRequest;
import com.canaslaner.exchange.controller.dto.ExchangeListResponse;
import com.canaslaner.exchange.controller.dto.ExchangeRateRequest;
import com.canaslaner.exchange.controller.dto.ExchangeRateResponse;
import com.canaslaner.exchange.controller.dto.ExchangeRequest;
import com.canaslaner.exchange.controller.dto.ExchangeResponse;
import com.canaslaner.exchange.exception.AppException;
import com.canaslaner.exchange.model.Conversion;
import com.canaslaner.exchange.model.dto.AmountInfo;
import com.canaslaner.exchange.service.ConversionService;
import com.canaslaner.exchange.service.ExchangeService;

@Slf4j
@RestController
@RequestMapping(value = "/v1/exchange",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Exchange API", description = "Manages exchange processes")
@RequiredArgsConstructor
public class ExchangeController {

    private final ConversionService conversionService;
    private final ExchangeService exchangeService;

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Returns calculated exchange",
            description = "Returns exchange of given amount and currency to desired currencies")
    @ApiResponse(responseCode = "200", description = "Processed successfully.")
    @ApiResponse(responseCode = "400", description = "One or more field is/are invalid.")
    public ExchangeResponse exchange(@Valid @RequestBody final ExchangeRequest request) throws AppException {
        final Pair<String, List<Conversion>> exchangePair = exchangeService.exchange(
                AmountInfo.of(request.getAmount(), request.getCurrency()), request.getTargetCurrencies());

        return ExchangeResponse.builder()
                .transactionId(exchangePair.getFirst())
                .amounts(exchangePair.getSecond()
                        .stream()
                        .map(Conversion::getTargetAmount)
                        .map(targetAmount -> AmountInfo.of(targetAmount.getAmount(), targetAmount.getCurrency()))
                        .collect(Collectors.toList()))
                .build();
    }

    @PostMapping(value = "/rate")
    @Operation(summary = "Returns exchange rates",
            description = "Returns exchange rates of given currency to desired currencies")
    @ApiResponse(responseCode = "200", description = "Processed successfully.")
    @ApiResponse(responseCode = "400", description = "One or more field is/are invalid.")
    public ExchangeRateResponse exchangeRate(@Valid @RequestBody final ExchangeRateRequest request)
            throws AppException {
        return ExchangeRateResponse.builder()
                .rates(exchangeService.getExchangeRates(request.getBase(), request.getTargetCurrencies()))
                .build();
    }

    @PostMapping(value = "/list")
    @Operation(summary = "Returns records that already saved", description = "Returns records that already saved")
    @ApiResponse(responseCode = "200", description = "Processed successfully.")
    @ApiResponse(responseCode = "400", description = "One or more field is/are invalid.")
    public ExchangeListResponse exchangeList(@Valid @RequestBody final ExchangeListRequest request,
            @ParameterObject @PageableDefault(size = 200) final Pageable pageable) {
        final List<Conversion> conversions;
        if (request.getTransactionId() != null) {
            conversions = conversionService.findByTransactionId(request.getTransactionId(), pageable);
        } else {
            conversions = conversionService.findByDate(request.getStartDate(), request.getEndDate(), pageable);
        }

        return ExchangeListResponse.builder()
                .conversions(conversions.stream().map(ConversionDto::from).collect(Collectors.toList()))
                .build();
    }
}
