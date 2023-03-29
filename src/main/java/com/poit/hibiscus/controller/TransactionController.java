package com.poit.hibiscus.controller;

import com.poit.hibiscus.dto.AccountTransactionDto;
import com.poit.hibiscus.dto.AccountTransactionViewDto;
import com.poit.hibiscus.dto.CardTransactionDto;
import com.poit.hibiscus.dto.CardTransactionViewDto;
import com.poit.hibiscus.service.AccountTransactionService;
import com.poit.hibiscus.service.CardTransactionService;
import com.poit.hibiscus.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transaction/")
public class TransactionController {

    private final AccountTransactionService transactionService;
    private final CardTransactionService cardTransactionService;
    private final UserService userService;
    private final ConversionService conversionService;

    @PostMapping("cards")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cardTransaction(
        @RequestBody CardTransactionDto cardTransactionDto) {

        cardTransactionService.insert(
            cardTransactionDto.fromCardId(),
            cardTransactionDto.toCardNumber(),
            cardTransactionDto.amount());
    }

    @PostMapping("accounts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void accountTransaction(
        @RequestBody AccountTransactionDto accountTransactionDto)
        throws InterruptedException {

        transactionService.insert(
            accountTransactionDto.fromAccountId(),
            accountTransactionDto.toAccountNumber(),
            accountTransactionDto.amount());
    }

    @GetMapping("cards")
    @ResponseStatus(HttpStatus.OK)
    public List<CardTransactionViewDto> cardTransactions(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var currentUser = userService.findUserByEmail(userDetails.getUsername());

        var transactionViews =
            cardTransactionService.findUserAttachedTransactions(currentUser.getId());

        return transactionViews.stream()
            .map(t -> conversionService.convert(t, CardTransactionViewDto.class))
            .toList();
    }

    @GetMapping("accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountTransactionViewDto> accountTransactions(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var currentUser = userService.findUserByEmail(userDetails.getUsername());

        var transactionViews =
            transactionService.findUserAttachedTransactions(currentUser.getId());

        return transactionViews.stream()
            .map(t -> conversionService.convert(t, AccountTransactionViewDto.class))
            .toList();
    }
}
