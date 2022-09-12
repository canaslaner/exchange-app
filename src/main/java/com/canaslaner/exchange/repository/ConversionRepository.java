package com.canaslaner.exchange.repository;

import com.canaslaner.exchange.model.Conversion;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {

    List<Conversion> findByTransactionId(String transactionId, Pageable pageable);

    List<Conversion> findByCreatedDateBetweenOrderByCreatedDateDesc(Instant startDate, Instant endDate,
            Pageable pageable);
}
