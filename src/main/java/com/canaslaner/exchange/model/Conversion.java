package com.canaslaner.exchange.model;

import com.canaslaner.exchange.model.dto.AmountInfo;
import java.time.Instant;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "conversion", indexes = @Index(columnList = "transaction_id"))
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conversionSequenceGenerator")
    @SequenceGenerator(name = "conversionSequenceGenerator", initialValue = 10, allocationSize = 100)
    private Long id;

    @Column(name = "transaction_id", length = 100, nullable = false)
    private String transactionId;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "source_amount", nullable = false))
    @AttributeOverride(name = "currency", column = @Column(name = "source_currency", length = 5, nullable = false))
    private AmountInfo sourceAmount;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "target_amount", nullable = false))
    @AttributeOverride(name = "currency", column = @Column(name = "target_currency", length = 5, nullable = false))
    private AmountInfo targetAmount;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @CreatedBy
    @Column(nullable = false, length = 300, updatable = false)
    private String createdBy;

}

