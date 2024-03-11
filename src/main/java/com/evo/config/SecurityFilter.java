package com.evo.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.evo.models.RefreshToken;
import com.evo.models.User;
import com.evo.repositories.UserRepository;
import com.evo.services.AuthService;
import com.evo.services.RefreshTokenService;
import com.evo.utils.TokenValidationResult;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractHeaderToken(request);

        if(token != null) {
            TokenValidationResult validationResult = authService.validateToken(token);
            User user = userRepository.findByEmail(validationResult.getEmail());

            if(validationResult.isValid()) {
                setNewContext(user);
                System.out.println("eaeaw");
            //try to create a new access token
            }else if(!validationResult.getEmail().isEmpty()) {
                Optional<RefreshToken> refreshTokenOp = refreshTokenService.findByUserEmail(validationResult.getEmail());
                if(refreshTokenOp.isPresent()) {
                    RefreshToken refreshToken = refreshTokenOp.get();
                    if(refreshTokenService.verifyExpiration(refreshToken)){
                        refreshTokenService.createOrUpdateRefreshToken(validationResult.getEmail(), token);
                        var newToken = authService.getToken(validationResult.getEmail());
                        //set the new access token in the response header
                        response.setHeader("Authorization", "Bearer " + newToken);
                    }else {
                        //invalidate context
                        setNewContext(null);
                        //delete refresh token
                        refreshTokenService.deleteRefreshToken(refreshToken.getId());
                    }
                }
            }
            
            // check if no token but has valid refresh token
            // delete refresh from database
            //setNewContext(null);
        }
        
        filterChain.doFilter(request, response);
    } 

    void setNewContext(User user) {
        if (user != null) {
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // If user is null, clear the authentication context
            SecurityContextHolder.clearContext();
        }
    }

    public String extractHeaderToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if(authHeader == null) return null;

        if(!authHeader.split(" ")[0].equals("Bearer")) return null;

        return authHeader.split(" ")[1];
    }

}
