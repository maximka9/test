package com.company.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestDto {

    @NotNull(message = "Card owner cannot be null")
    private String owner;

    @NotNull(message = "Expiration date cannot be null")
    private String expirationDate; // в формате "MM/YY"

    @NotNull(message = "Initial balance cannot be null")
    private BigDecimal balance;

    private String status; // Активна, Заблокирована, Истек срок действия
}
