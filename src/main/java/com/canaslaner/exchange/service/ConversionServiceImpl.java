package com.canaslaner.exchange.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.canaslaner.exchange.model.Conversion;
import com.canaslaner.exchange.model.dto.AmountInfo;
import com.canaslaner.exchange.repository.ConversionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversionServiceImpl implements ConversionService {

    private final ConversionRepository conversionRepository;

    @Override
    public List<Conversion> findByTransactionId(final String transactionId, final Pageable pageable) {
        return conversionRepository.findByTransactionId(transactionId, pageable);
    }

    @Override
    public List<Conversion> findByDate(final Instant startDate, final Instant endDate, final Pageable pageable) {
        return conversionRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(startDate, endDate, pageable);
    }

    @Override
    public List<Conversion> save(final AmountInfo sourceAmountInfo, final List<AmountInfo> amountInfos,
            final String transactionId, final Instant createDate) {
        final List<Conversion> conversionsToSave = amountInfos.stream()
                .map(targetInfo -> Conversion.builder()
                        .transactionId(transactionId)
                        .sourceAmount(AmountInfo.of(sourceAmountInfo.getAmount(), sourceAmountInfo.getCurrency()))
                        .targetAmount(AmountInfo.of(targetInfo.getAmount(), targetInfo.getCurrency()))
                        .createdDate(createDate)
                        .build())
                .collect(Collectors.toList());
        return conversionRepository.saveAll(conversionsToSave);
    }
}