package com.evo.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Column(name = "expiry_date")
    private Instant expiryDate;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private RefreshToken() {}

    public static RefreshTokenBuilder builder() {
        return new RefreshTokenBuilder();
    }

        // Builder class
        public static class RefreshTokenBuilder {
            private RefreshToken instance = new RefreshToken();
    
            private RefreshTokenBuilder() {}
    
            public RefreshTokenBuilder token(String token) {
                instance.token = token;
                return this;
            }
    
            public RefreshTokenBuilder expiryDate(Instant expiryDate) {
                instance.expiryDate = expiryDate;
                return this;
            }
    
            public RefreshTokenBuilder user(User user) {
                instance.user = user;
                return this;
            }
    
            public RefreshToken build() {
                return instance;
            }
        }

   
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Instant getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
