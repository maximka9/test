package com.company.test.service;

import com.company.test.entity.Card;
import com.company.test.entity.CardStatus;
import com.company.test.entity.Transaction;
import com.company.test.repository.CardRepository;
import com.company.test.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardService cardService;

    @InjectMocks
    private TransactionService transactionService;

    private Card senderCard;
    private Card receiverCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        senderCard = new Card();
        senderCard.setId(1L); // Long вместо UUID
        senderCard.setBalance(BigDecimal.valueOf(1000.0)); // BigDecimal вместо double
        senderCard.setStatus(CardStatus.ACTIVE);

        receiverCard = new Card();
        receiverCard.setId(2L); // Long вместо UUID
        receiverCard.setBalance(BigDecimal.valueOf(500.0));
        receiverCard.setStatus(CardStatus.ACTIVE);
    }

    @Test
    void testTransferFunds() {
        BigDecimal transferAmount = BigDecimal.valueOf(200.0);

        when(cardService.findCardById(senderCard.getId())).thenReturn(senderCard);
        when(cardService.findCardById(receiverCard.getId())).thenReturn(receiverCard);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.transfer(senderCard.getId(), receiverCard.getId(), transferAmount);

        assertNotNull(transaction);
        assertEquals(BigDecimal.valueOf(800.0), senderCard.getBalance());
        assertEquals(BigDecimal.valueOf(700.0), receiverCard.getBalance());

        verify(cardService, times(2)).saveCard(any(Card.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testInsufficientFunds() {
        BigDecimal transferAmount = BigDecimal.valueOf(2000.0);

        when(cardService.findCardById(senderCard.getId())).thenReturn(senderCard);
        when(cardService.findCardById(receiverCard.getId())).thenReturn(receiverCard);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transfer(senderCard.getId(), receiverCard.getId(), transferAmount);
        });

        assertEquals("Недостаточно средств на карте", exception.getMessage());

        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
