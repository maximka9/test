package com.company.test.controller;

import com.company.test.entity.Card;
import com.company.test.entity.User;
import com.company.test.service.CardService;
import com.company.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    // Создание карты для пользователя
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Card createCard(@PathVariable Long userId, @RequestParam String ownerName, @RequestParam String expiryDate) {
        User user = userService.getUserById(userId);
        return cardService.createCard(user, ownerName, LocalDate.parse(expiryDate));
    }
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    // Получение всех карт пользователя
    @GetMapping("/user/{userId}")
    public List<Card> getUserCards(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return cardService.getCardsByUser(user);
    }

    // Блокировка карты
    @PutMapping("/{cardId}/block")
    public Card blockCard(@PathVariable Long cardId) {
        return cardService.blockCard(cardId);
    }

    // Активация карты
    @PutMapping("/{cardId}/activate")
    public Card activateCard(@PathVariable Long cardId) {
        return cardService.activateCard(cardId);
    }

    // Удаление карты
    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
    }
}
