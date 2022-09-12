package com.canaslaner.exchange.validation;

import com.canaslaner.exchange.controller.dto.ExchangeListRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class ValidExchangeListRequestValidator implements
        ConstraintValidator<ValidExchangeListRequest, ExchangeListRequest> {

    @Override
    public void initialize(ValidExchangeListRequest annotation) {
    }

    @Override
    public boolean isValid(ExchangeListRequest exchangeListRequest, ConstraintValidatorContext context) {
        final boolean dateFieldsAreNotEmpty =
                exchangeListRequest.getStartDate() != null || exchangeListRequest.getEndDate() != null;
        final boolean transactionIdIsNotEmpty = StringUtils.isNotEmpty(exchangeListRequest.getTransactionId());
        return transactionIdIsNotEmpty != dateFieldsAreNotEmpty;
    }
}
