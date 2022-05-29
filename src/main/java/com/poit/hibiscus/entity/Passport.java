package com.poit.hibiscus.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "passports")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Passport {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sns_id")
    private SNS sns;

    @Column(name = "dob")
    private LocalDateTime dob;

    @Column(name = "identity_code")
    private String identityCode;
}
