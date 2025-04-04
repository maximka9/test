package com.company.test.service;

import com.company.test.entity.Card;
import com.company.test.entity.CardStatus;
import com.company.test.entity.User;
import com.company.test.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    private Card testCard;
    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);

        testCard = new Card();
        testCard.setId(1L);
        testCard.setEncryptedNumber("encrypted_data");
        testCard.setOwnerName("John Doe");
        testCard.setExpiryDate(LocalDate.now().plusYears(3));
        testCard.setOwner(testUser);
        testCard.setStatus(CardStatus.ACTIVE);
        testCard.setBalance(new BigDecimal("1000.00"));
    }

    @Test
    void testCreateCard() {
        when(cardRepository.save(any(Card.class))).thenReturn(testCard);

        Card createdCard = cardService.createCard(testUser, "John Doe", LocalDate.now().plusYears(3));

        assertNotNull(createdCard);
        assertEquals(CardStatus.ACTIVE, createdCard.getStatus());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void testBlockCard() {
        when(cardRepository.findById(any(Long.class))).thenReturn(Optional.of(testCard));
        when(cardRepository.save(any(Card.class))).thenReturn(testCard);

        Card blockedCard = cardService.blockCard(testCard.getId());

        assertNotNull(blockedCard);
        assertEquals(CardStatus.BLOCKED, blockedCard.getStatus());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void testDeleteCard() {
        when(cardRepository.findById(any(Long.class))).thenReturn(Optional.of(testCard));

        cardService.deleteCard(testCard.getId());

        verify(cardRepository, times(1)).delete(testCard);
    }
}
