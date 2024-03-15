package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.SecureToken;

import java.util.List;

public interface secureTokenService {
    SecureToken createSecureToken();
    void saveSecureToken(SecureToken token);
    SecureToken findByToken(String token);
    void removeToken(SecureToken token);
    void removeTokenByToken(String token);
    int getTokenValidityInSeconds();
    List<SecureToken> listofToken();

}
