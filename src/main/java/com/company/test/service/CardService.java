package com.company.test.service;

import com.company.test.entity.Card;
import com.company.test.entity.CardStatus;
import com.company.test.entity.User;
import com.company.test.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    public Card findCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Карта не найдена"));
    }

    public Card blockCard(Long cardId) {
        Card card = findCardById(cardId);
        card.setStatus(CardStatus.BLOCKED);
        return cardRepository.save(card);
    }

    public Card activateCard(Long cardId) {
        Card card = findCardById(cardId);
        card.setStatus(CardStatus.ACTIVE);
        return cardRepository.save(card);
    }

    public void deleteCard(Long cardId) {
        Card card = findCardById(cardId);
        cardRepository.delete(card);
    }

    public Card createCard(User user, String ownerName, LocalDate expiryDate) {
        Card card = new Card();
        card.setOwner(user);
        card.setOwnerName(ownerName);
        card.setExpiryDate(expiryDate);
        card.setStatus(CardStatus.ACTIVE);
        return cardRepository.save(card);
    }

    public List<Card> getCardsByUser(User user) {
        return cardRepository.findByOwner(user);
    }

    // ✅ Добавлен метод для тестов
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
}
