package com.company.test.controller;

import com.company.test.entity.Card;
import com.company.test.entity.CardStatus;
import com.company.test.service.CardService;
import com.company.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CardController.class)
@AutoConfigureMockMvc(addFilters = false) // Отключаем безопасность для тестов
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CardService cardService;
    @MockitoBean
    private UserService userService;

    @Test
    void testGetAllCards() throws Exception {
        Card card = new Card();
        card.setId(1L);
        card.setBalance(BigDecimal.valueOf(1000.0));
        card.setStatus(CardStatus.ACTIVE);

        List<Card> cards = Collections.singletonList(card);
        when(cardService.getAllCards()).thenReturn(cards);

        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].balance").value(1000.0))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }
}
