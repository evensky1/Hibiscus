package com.poit.hibiscus.controller;

import com.poit.hibiscus.dto.AccountTransactionViewDto;
import com.poit.hibiscus.dto.CardTransactionViewDto;
import com.poit.hibiscus.dto.TransactionsDto;
import com.poit.hibiscus.service.AccountTransactionService;
import com.poit.hibiscus.service.CardTransactionService;
import com.poit.hibiscus.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> cardTransaction(
        @RequestBody TransactionsDto.CardTransactionDto cardTransactionDto) {

        cardTransactionService.insert(
            cardTransactionDto.fromCardId(),
            cardTransactionDto.toCardNumber(),
            cardTransactionDto.amount());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("accounts")
    public ResponseEntity<Void> accountTransaction(
        @RequestBody TransactionsDto.AccountTransactionDto accountTransactionDto)
        throws InterruptedException {

        transactionService.insert(
            accountTransactionDto.fromAccountId(),
            accountTransactionDto.toAccountNumber(),
            accountTransactionDto.amount());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("cards")
    public ResponseEntity<List<CardTransactionViewDto>> cardTransactions(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var currentUser = userService.findUserByEmail(userDetails.getUsername());

        var transactionViews =
            cardTransactionService.findUserAttachedTransactions(currentUser.getId());

        var transactionViewDtos =
            transactionViews.stream()
                .map(t -> conversionService.convert(t, CardTransactionViewDto.class))
                .toList();

        return new ResponseEntity<>(transactionViewDtos, HttpStatus.OK);
    }

    @GetMapping("accounts")
    public ResponseEntity<List<AccountTransactionViewDto>> accountTransactions(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var currentUser = userService.findUserByEmail(userDetails.getUsername());

        var transactionViews =
            transactionService.findUserAttachedTransactions(currentUser.getId());

        var transactionViewsDtos = transactionViews.stream()
            .map(t -> conversionService.convert(t, AccountTransactionViewDto.class))
            .toList();

        return new ResponseEntity<>(transactionViewsDtos, HttpStatus.OK);
    }
}
