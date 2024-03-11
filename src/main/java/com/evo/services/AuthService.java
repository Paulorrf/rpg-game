package com.evo.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.evo.dtos.AuthDto;
import com.evo.models.RefreshToken;
import com.evo.models.User;
import com.evo.repositories.UserRepository;
import com.evo.utils.TokenValidationResult;

@Service
public class AuthService implements UserDetailsService{

    private UserRepository userRepository;

    private RefreshTokenService refreshTokenService;

    
    @Autowired
    public AuthService(UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public String getTokens(AuthDto authDto) {
        var dbUser = userRepository.findByEmail(authDto.email());
        var accessToken = generateTokenJwt(dbUser);

        refreshTokenService.createOrUpdateRefreshToken(dbUser.getEmail(), accessToken);
        
        return accessToken;
    }

    public String getToken(String email) {
        var dbUser = userRepository.findByEmail(email);
        return generateTokenJwt(dbUser);
    }

    public String generateTokenJwt(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("segredooooooo");

            return JWT.create()
                .withIssuer("evolution-api")
                .withSubject(user.getEmail())
                .withExpiresAt(generateExpirationDate(30L))
                .sign(algorithm);

        } catch (JWTCreationException exception){
            throw new RuntimeException("Error on generate token " + exception.getMessage());
        }
    }

    public Instant generateExpirationDate(Long minutes) {
        return LocalDateTime.now().plusSeconds(minutes).toInstant(ZoneOffset.of("-03:00"));
    }

    public TokenValidationResult validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("segredooooooo");

            String email = JWT
                    .require(algorithm)
                    .withIssuer("evolution-api")
                    .build()
                    .verify(token)        
                    .getSubject();

            return new TokenValidationResult(email, true);
        }  catch (TokenExpiredException e) {
            return new TokenValidationResult(emailFromToken(token), false);
        }catch (JWTVerificationException e) {
            return new TokenValidationResult(null, false);
        }
    }

    private String emailFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getSubject();
        } catch (JWTDecodeException e) {
            return null; // Return null if email claim is not found or cannot be decoded
        }
    }

     // Check if the access token is expired
     public Boolean isAccessTokenExpired(String accessToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("segredooooooo");

            JWT.require(algorithm).withIssuer("evolution-api").build().verify(accessToken);

            return true;
            
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    // Extract refresh token from the access token
    public RefreshToken extractRefreshToken(String accessToken) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(accessToken);
    
        if(refreshTokenOptional.isPresent()) {
            return refreshTokenOptional.get(); // Access the value from the Optional and return it
        } else {
            return null;
        }
    }
    

    // Check if the refresh token is valid
    public Boolean isRefreshTokenValid(RefreshToken refreshToken) {
        try {
            refreshTokenService.verifyExpiration(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Generate a new access token using the refresh token
    public String generateNewAccessToken(RefreshToken refreshToken) {
        // Generate a new access token using the refresh token
        // Return the new access token
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }


}
