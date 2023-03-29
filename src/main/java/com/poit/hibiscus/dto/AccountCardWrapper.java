package com.poit.hibiscus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCardWrapper {
    private AccountDto accountDto;

    private CardDto cardDto;
}
