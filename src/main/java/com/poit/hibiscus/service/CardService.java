package com.poit.hibiscus.service;

import com.poit.hibiscus.entity.Card;
import java.util.List;

public interface CardService {
    Card createCard(Card card, Long accountId, Long userId);

    List<Card> getUserAttachedCards(Long id);

    Long findCardIdByNumber(String number);
}
