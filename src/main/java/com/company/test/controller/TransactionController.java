package com.company.test.controller;

import com.company.test.entity.Transaction;
import com.company.test.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // Перевод средств между картами
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction transfer(@RequestParam Long fromCardId, @RequestParam Long toCardId, @RequestParam BigDecimal amount) {
        return transactionService.transfer(fromCardId, toCardId, amount);
    }

    // Списание средств с карты
    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestParam Long cardId, @RequestParam BigDecimal amount) {
        return transactionService.withdraw(cardId, amount);
    }

    // Пополнение баланса карты
    @PostMapping("/deposit")
    public Transaction deposit(@RequestParam Long cardId, @RequestParam BigDecimal amount) {
        return transactionService.deposit(cardId, amount);
    }
}
