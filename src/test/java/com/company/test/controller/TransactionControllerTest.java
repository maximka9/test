package com.company.test.controller;

import com.company.test.entity.Transaction;
import com.company.test.entity.TransactionType;
import com.company.test.security.JwtAuthFilter;
import com.company.test.service.TransactionService;
import com.company.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc(addFilters = false) // Отключаем безопасность для тестов
@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;
    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;
    @MockitoBean
    private UserService userService;
    @Test
    void testTransferFunds() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(200.0));
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(TransactionType.TRANSFER);

        when(transactionService.transfer(1L, 2L, BigDecimal.valueOf(200.0)))
                .thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/transfer")
                .param("fromCardId", "1")
                .param("toCardId", "2")
                .param("amount", "200.0"));
    }

    @Test
    void testDepositFunds() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(500.0));
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(TransactionType.DEPOSIT);

        when(transactionService.deposit(1L, BigDecimal.valueOf(500.0)))
                .thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/deposit")
                .param("cardId", "1")
                .param("amount", "500.0"));
    }

    @Test
    void testWithdrawFunds() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(300.0));
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(TransactionType.WITHDRAWAL);

        when(transactionService.withdraw(1L, BigDecimal.valueOf(300.0)))
                .thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/withdraw")
                        .param("cardId", "1")
                        .param("amount", "300.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(300.0))
                .andExpect(jsonPath("$.type").value("WITHDRAWAL"));

    }
}
