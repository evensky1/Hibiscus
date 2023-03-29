package com.poit.hibiscus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime dob;

    private String identityCode;

    private String name;

    private String surname;

    private String succession;
}
