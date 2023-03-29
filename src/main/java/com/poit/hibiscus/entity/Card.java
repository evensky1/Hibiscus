package com.poit.hibiscus.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Card {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pin")
    private String pin;

    @Column(name = "number")
    private String number;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    @Column(name = "person_id")
    private Long userId;

    @Column(name = "account_id")
    private Long accountId;
}
