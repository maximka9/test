package com.company.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponseDto {

    private Long transactionId;
    private Long fromCardId;
    private Long toCardId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
