package com.esprit.cloudcraft.implement.userImplement;

import com.esprit.cloudcraft.entities.userEntities.SecureToken;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.userDao.SecureTokenRepository;
import com.esprit.cloudcraft.services.userServices.SecureTokenService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
@Service
public class SecureTokenImplement implements SecureTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Value("${jdj.secure.token.validity}")
    private int tokenValidityInSeconds;

    @Resource
    SecureTokenRepository secureTokenRepository;
/**************************** create a token for the email ******************/
    @Override
    public SecureToken createSecureToken()
    {
        String tokenValue = new String(Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey())); // this is a sample, you can adapt as per your security need
        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        this.saveSecureToken(secureToken);
        return secureToken;
    }
/****************** save the token created in the database ******************/
    @Override
    public void saveSecureToken(SecureToken token)
    {
        secureTokenRepository.save(token);
    }
/********************* find a token by the token provided **********/
    @Override
    public SecureToken findByToken(String token)
    {
        return secureTokenRepository.findByToken(token);
    }
    /******************* find toke nby user provided ***********/
    @Override
    public Optional<SecureToken> findByUser(User user)
    {
        return secureTokenRepository.findByUser(user);
    }
/******************* remove email token from database *******************/
    @Override
    public void removeToken(SecureToken token)
    {
        secureTokenRepository.delete(token);
    }
    @Override
    public void removeTokenByToken(String token)
    {
        secureTokenRepository.removeByToken(token);
    }
    /************************ return the token validity ***************/
    @Override
    public int getTokenValidityInSeconds()
    {
        return tokenValidityInSeconds;
    }
/************** a crony methode : deleted expired email tokens ************************/
    @Override
    @Transactional
    @Scheduled(cron= " * * * * * ?")
    public List<SecureToken> listofToken()
    {
        List<SecureToken> tokens= secureTokenRepository.findAll();
        for(SecureToken token:tokens )
          if( token.isExpired())
          {
              secureTokenRepository.removeByToken(token.getToken());
          return null;
          }
        return null;
    }


}