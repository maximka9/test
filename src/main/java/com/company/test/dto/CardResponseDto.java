package com.company.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CardResponseDto {

    private Long id;
    private String owner;
    private String expirationDate;
    private BigDecimal balance;
    private String status;
    private String maskedCardNumber; // Маскированный номер карты
}
