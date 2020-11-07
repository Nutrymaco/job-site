package com.nutrymaco.jobsite.config;

import com.nutrymaco.jobsite.adapter.AutocompleteDBAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThirdPartyServices {

    @Value("${autocomplete.host}")
    private String autocompleteHost;

    @Value("${autocomplete.port}")
    private int autocompletePort;

    @Bean
    AutocompleteDBAdapter autocompleteDBAdapter() {
        return new AutocompleteDBAdapter(autocompleteHost, autocompletePort);
    }

}
