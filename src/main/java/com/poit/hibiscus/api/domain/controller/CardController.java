package com.poit.hibiscus.api.domain.controller;

import com.poit.hibiscus.dto.AccountCardWrapper;
import com.poit.hibiscus.dto.CardDto;
import com.poit.hibiscus.entity.Card;
import com.poit.hibiscus.service.AccountService;
import com.poit.hibiscus.service.CardService;
import com.poit.hibiscus.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cards/")
public class CardController {

    private final CardService cardService;
    private final ConversionService conversionService;
    private final UserService userService;
    private final AccountService accountService;

    @PostMapping("new")
    public ResponseEntity<CardDto> createCard(
        @RequestBody AccountCardWrapper accountCardWrapper,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var card =
            conversionService.convert(accountCardWrapper.getCardDto(), Card.class);

        var account =
            accountService.findByIban(accountCardWrapper.getAccountDto().getIban());

        var userId = userService.findUserByEmail(userDetails.getUsername()).getId();

        var newCard = cardService.createCard(card, account.getId(), userId);

        return new ResponseEntity<>(
            conversionService.convert(newCard, CardDto.class),
            HttpStatus.CREATED
        );
    }

    @GetMapping("user-attached")
    public ResponseEntity<List<CardDto>> getUserAttachedCards(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var currentUser = userService.findUserByEmail(userDetails.getUsername());

        var userAttachedCardDtos = cardService
            .getUserAttachedCards(currentUser.getId())
            .stream()
            .map(c -> conversionService.convert(c, CardDto.class))
            .toList();

        return new ResponseEntity<>(userAttachedCardDtos, HttpStatus.OK);
    }
}
