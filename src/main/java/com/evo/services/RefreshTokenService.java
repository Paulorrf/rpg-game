package com.evo.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evo.models.RefreshToken;
import com.evo.repositories.RefreshTokenRepository;
import com.evo.repositories.UserRepository;

@Service
public class RefreshTokenService {

    RefreshTokenRepository refreshTokenRepository;
    UserRepository userRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }    

    @SuppressWarnings("null")
    public void createOrUpdateRefreshToken(String email, String token){
        //var user = userRepository.findByEmail(email);
        Optional<RefreshToken> refreshExist = refreshTokenRepository.findByUserEmail(email);
        System.out.println(refreshExist);

        if(refreshExist.isPresent()) {
            refreshTokenRepository.updateToken(refreshExist.get().getId(), token);
        }else {
            RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(email))
                .token(token)
                .expiryDate(LocalDateTime.now().plusMinutes(1000).toInstant(ZoneOffset.of("-03:00"))) // set expiry of refresh token to 10 minutes - you can configure it application.properties file 
                .build();
        refreshTokenRepository.save(refreshToken);
        }
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshToken> findByUserEmail(String email){
        return refreshTokenRepository.findByUserEmail(email);
    }

    public Boolean deleteRefreshToken(Long id){
        if(id != null ){
            refreshTokenRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Boolean verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return true;
    }
 /*    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    } */

  
    
}
