package com.probity.echawdi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.probity.echawdi.config.WithDatabase;
import com.probity.echawdi.entity.Token;
import com.probity.echawdi.repository.TokenRepsitory;
import com.probity.echawdi.utils.DataSourceType;


@Service
public class TokenService {

    @Autowired
    private TokenRepsitory tokenRepository;

    @WithDatabase(DataSourceType.echmst)
    public Optional<Token> findByAccessToken(String token) {
        return tokenRepository.findByAccessToken(token);
    }
    
    
    
   
}