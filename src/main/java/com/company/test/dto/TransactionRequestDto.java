package com.company.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {

    private Long fromCardId;  // ID карты-отправителя
    private Long toCardId;    // ID карты-получателя
    private BigDecimal amount;
}
