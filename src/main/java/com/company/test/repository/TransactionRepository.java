package com.company.test.repository;

import com.company.test.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Метод для подсчета суммы транзакций по карте и дате
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.card.id = :cardId AND t.timestamp >= :startDate AND t.timestamp < :endDate")
    BigDecimal sumAmountByCardAndDate(Long cardId, LocalDate startDate, LocalDate endDate);
}
