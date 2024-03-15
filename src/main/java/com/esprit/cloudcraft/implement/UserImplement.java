package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.SecureToken;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.exceptions.UserAlreadyExistException;
import com.esprit.cloudcraft.repository.SecureTokenRepository;
import com.esprit.cloudcraft.repository.UserRepository;
import com.esprit.cloudcraft.services.EmailService;
import com.esprit.cloudcraft.services.SecureTokenService;
import com.esprit.cloudcraft.services.UserService;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Objects;

@Service
public class UserImplement implements UserService,UserDetailsService{
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private EmailService emailService;

    @Resource
    private SecureTokenService secureTokenService;

    @Resource
    SecureTokenRepository secureTokenRepository;
    @Value("${site.base.url.https}")
    private String baseURL;
    @Override
    public User register(User user) throws UserAlreadyExistException {
        if(userRepository.findByEmail(user.getEmail())!= null) {
            throw new UserAlreadyExistException();
        }


             String encryptedPassword = passwordEncoder.encode(user.getPassword());
       user.setPassword(encryptedPassword);
       user.setEnable(false);
        userRepository.save(user);
        sendRegistrationConfirmationEmail(user);
        return userRepository.save(user);
    }
    public void resendToken(User user)  {


        sendRegistrationConfirmationEmail(user);

    }
    @Override
    public void sendRegistrationConfirmationEmail(User user) {
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean verifyUser(String token) throws UserAlreadyExistException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
            throw new UserAlreadyExistException();
        }

        User user = userRepository.getOne(secureToken.getUser().getId());
        if (Objects.isNull(user)) {
            return false;
        }
        user.setEnable(true);
        userRepository.save(user); // let’s same user details

        // we don’t need invalid password now
        secureTokenService.removeToken(secureToken);
        return true;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {
        User user=userRepository.findByEmail(username);
        if(user==null)
             throw new UsernameNotFoundException("user not found");
       return user;
    }


}
