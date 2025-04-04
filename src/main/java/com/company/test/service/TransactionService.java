package com.company.test.service;

import com.company.test.entity.Card;
import com.company.test.entity.Transaction;
import com.company.test.entity.TransactionType;
import com.company.test.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardService cardService;

    // Операция перевода между картами
    public Transaction transfer(Long fromCardId, Long toCardId, BigDecimal amount) {
        Card fromCard = cardService.findCardById(fromCardId);
        Card toCard = cardService.findCardById(toCardId);

        if (fromCard.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на карте");
        }

        // Обновляем баланс карт
        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));

        // Сохраняем транзакции
        Transaction transaction = Transaction.builder()
                .card(fromCard)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .type(TransactionType.TRANSFER)
                .build();
        transactionRepository.save(transaction);

        // Сохраняем карты с обновленным балансом
        cardService.saveCard(fromCard);
        cardService.saveCard(toCard);

        return transaction;
    }

    // Операция списания средств с карты (с учетом лимитов)
    public Transaction withdraw(Long cardId, BigDecimal amount) {
        Card card = cardService.findCardById(cardId);
        BigDecimal dailyLimit = card.getDailyLimit();

        // Получаем начало и конец дня
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        // Подсчитываем сумму снятых средств за сегодня
        BigDecimal totalWithdrawnToday = transactionRepository.sumAmountByCardAndDate(cardId, startOfDay.toLocalDate(), endOfDay.toLocalDate());

        if (totalWithdrawnToday.add(amount).compareTo(dailyLimit) > 0) {
            throw new IllegalArgumentException("Превышен дневной лимит");
        }

        if (card.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на карте");
        }

        // Обновляем баланс карты
        card.setBalance(card.getBalance().subtract(amount));

        // Сохраняем транзакцию
        Transaction transaction = Transaction.builder()
                .card(card)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .type(TransactionType.WITHDRAWAL)
                .build();
        transactionRepository.save(transaction);

        // Сохраняем карту
        cardService.saveCard(card);

        return transaction;
    }


    // Операция пополнения баланса карты
    public Transaction deposit(Long cardId, BigDecimal amount) {
        Card card = cardService.findCardById(cardId);

        // Обновляем баланс карты
        card.setBalance(card.getBalance().add(amount));

        // Сохраняем транзакцию
        Transaction transaction = Transaction.builder()
                .card(card)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .type(TransactionType.DEPOSIT)
                .build();
        transactionRepository.save(transaction);

        // Сохраняем карту
        cardService.saveCard(card);

        return transaction;
    }
}
