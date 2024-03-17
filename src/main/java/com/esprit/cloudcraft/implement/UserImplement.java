package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.authentification.JwtService;
import com.esprit.cloudcraft.dto.AuthenticationResponse;
import com.esprit.cloudcraft.entities.RoleType;
import com.esprit.cloudcraft.entities.SecureToken;
import com.esprit.cloudcraft.entities.User;

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
import java.util.Optional;

@Service
public class UserImplement implements UserService{
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
    @Resource
    private  JwtService jwtService;

    @Value("${site.base.url.https}")
    private String baseURL;
    @Override
    public AuthenticationResponse register(User user) throws UsernameNotFoundException {
        if(userRepository.findByEmail(user.getEmail())!= null) {
            new UsernameNotFoundException("user already exists");

        }


             String encryptedPassword = passwordEncoder.encode(user.getPassword());
       user.setPassword(encryptedPassword);
       user.setEnable(false);
       /* var users = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(RoleType.USER)
                .mfaEnabled(user.isMfaEnabled())
                .birthDate(user.getBirthDate())
                .enable(true)
                .secret("hi")
                .build();*/

        userRepository.save(user);
        sendRegistrationConfirmationEmail(user);

        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
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
    public boolean verifyUser(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {

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



}
