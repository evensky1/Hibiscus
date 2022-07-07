package com.poit.hibiscus.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "bank")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientProperties {
    public static final String URL = "http://api.currencylayer.com";
    public static final String ENDPOINT = "/live";
    public static final String ACCESS_KEY = "3cb2148e7f1e0c00f2e85dc26d283925";
    public static final String CURRENCIES = "BYN,EUR,RUB";
}
