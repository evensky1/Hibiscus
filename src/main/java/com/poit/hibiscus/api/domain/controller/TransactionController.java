package com.poit.hibiscus.api.domain.controller;

import com.poit.hibiscus.dto.TransactionsDto;
import com.poit.hibiscus.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transaction/")
public class TransactionController {
    private final TransactionsService.AccountTransactionService transactionService;
    private final TransactionsService.CardTransactionService cardTransactionService;

    @PostMapping("card")
    public ResponseEntity<Void> cardTransaction(@RequestBody TransactionsDto.CardTransactionDto cardTransactionDto) {
        cardTransactionService.insert(
                cardTransactionDto.fromCardId(),
                cardTransactionDto.toCardNumber(),
                cardTransactionDto.amount());
        return ResponseEntity.noContent().build();
    }

     @PostMapping("account")
     public ResponseEntity<Void> accountTransaction(@RequestBody TransactionsDto.AccountTransactionDto accountTransactionDto) throws InterruptedException {
        transactionService.insert(
                accountTransactionDto.fromAccountId(),
                accountTransactionDto.toAccountNumber(),
                accountTransactionDto.amount());

        return ResponseEntity.noContent().build();
     }
}

