package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.entity.Card;
import com.poit.hibiscus.error.factory.configuration.HandleError;
import com.poit.hibiscus.error.factory.model.CardException;
import com.poit.hibiscus.repository.CardRepository;
import com.poit.hibiscus.service.CardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public Card createCard(Card card, Long accountId, Long userId) {
        int cvv = (int) (Math.random() * 899 + 100);
        long num = (long) (1000_0000_0000_0000L + Math.random() * (Long.MAX_VALUE / 1000L));
        int pin = (int) (Math.random() * 1000);
        var newCard = Card.builder()
                .accountId(accountId)
                .userId(userId)
                .cvv(String.valueOf(cvv))
                .pin(String.valueOf(pin))
                .number(String.valueOf(num))
                .expirationTime(card.getExpirationTime())
                .build();

        return cardRepository.save(newCard);
    }

    @Override
    @HandleError
    public List<Card> getUserAttachedCards(Long id) {
        var cards = cardRepository.getAllByUserId(id);

        if(cards == null) {
            throw new CardException("No card attached to card");
        }

        return cards;
    }

    @Override
    public Long findCardIdByNumber(String number) {
        return cardRepository.findCardIdByCardNumber(number);
    }
}
