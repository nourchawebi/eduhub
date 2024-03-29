package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.authentification.JwtService;
import com.esprit.cloudcraft.dto.*;
import com.esprit.cloudcraft.entities.SecureToken;
import com.esprit.cloudcraft.entities.User;

import com.esprit.cloudcraft.repository.SecureTokenRepository;
import com.esprit.cloudcraft.repository.UserRepository;
import com.esprit.cloudcraft.services.AuthenticationService;
import com.esprit.cloudcraft.services.EmailService;
import com.esprit.cloudcraft.services.SecureTokenService;
import com.esprit.cloudcraft.services.UserService;

import com.esprit.cloudcraft.tfa.TwoFactorAuthenticationService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserImplement implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private EmailService emailService;

    @Resource
    private SecureTokenService secureTokenService;
    @Resource
    private AuthenticationService authService;
    @Resource
    SecureTokenRepository secureTokenRepository;
    @Resource
    private JwtService jwtService;
    @Resource
    private TwoFactorAuthenticationService tfaService;


    @Value("${site.base.url.https}")
    private String baseURL;

    @Override
    public AuthenticationResponse register(User user)  {



            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            user.setEnable(false);
            if (user.isMfaEnabled()) {
                user.setSecret(tfaService.generateNewSecret());
            }
            var savedUser = userRepository.save(user);

            sendRegistrationConfirmationEmail(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            authService.saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                    .accessToken(jwtToken)
                    .mfaEnabled(user.isMfaEnabled())
                    .refreshToken(refreshToken).build();

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
    /* verify user first register */

    public boolean verifyUser(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {

        }

        User user = userRepository.getOne(secureToken.getUser().getId());
        if (Objects.isNull(user)) {
            return false;
        }
        user.setEnable(true);
        userRepository.save(user); // let’s save user details

        // we don’t need invalid password now
        secureTokenService.removeToken(secureToken);
        return true;
    }

    /* resend verification mail before logging and for changin the email  i will create another methode*/

    public void resendToken(String email) throws IllegalArgumentException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            sendRegistrationConfirmationEmail(user);
        } else {
            // Handle the case when the user is not found in the repository
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
    }
    /*  change email*/

    /***********************************************************************************/

    /* n3mlou send verif email jdida  khtr mhich bch t3yt lnafs lapi mt3 verify user khtr hedha bch y3ml update lel email wrefresh ltoken */
    public void sendVerifNewEmail(User user) {
       SecureToken emailToken = secureTokenService.createSecureToken();
        emailToken.setUser(user);
        secureTokenRepository.save(emailToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(emailToken.getToken());
        emailContext.buildVerificationUrlNewEmail(baseURL, emailToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    /* tw lmethode mt3 lverif mt3na li bch t3ml el update*/

    public AuthenticationResponse verifyNewEmail(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
           // throw new InvalidTokenException("Invalid or expired token.");
        }

        User user = userRepository.findById(secureToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        user.setEnable(true);
        user.setEmail(newEmail);
        userRepository.save(user); // let’s save user details
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authService.revokeAllUserTokens(user);
        authService.saveUserToken(user, jwtToken);

        // we don’t need invalid password now
        secureTokenService.removeToken(secureToken);
        return AuthenticationResponse.builder()
                // .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

    public String newEmail;
    /* hdi awl haja bch namlohal i nb3thou request mt3 update request b email */

    public String changeEmail(ChangeEmailRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!request.getCurrentEmail().equals(user.getEmail())) {
            throw new IllegalStateException("Wrong email");
        }
        if (!request.getNewEmail().equals(request.getConfirmationEmail())) {
            throw new IllegalStateException("Emails are not the same");
        }
         // user.setEnable(false);
        var verifuser = user;
        verifuser.setEmail(request.getNewEmail());
        SecureToken secureToken = secureTokenService.findByUser(user);
        if(secureToken==null)
        {sendVerifNewEmail(verifuser);// bch nb3th  verif token bl email jdid ama mnich bch nsajlou lin yetverifa bch mb3d nrefreshi ltoken
       // userRepository.save(user);
        newEmail = request.getNewEmail();
        return request.getNewEmail();}
        else{
            throw new RuntimeException("an email is already sent! u can't send another email now or change ur email again");

        }
    }


    /* update password */

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }
/* forgot password */
 public String pwdEmail;
    @Override
    public boolean sendForgotPasswordRequest(String email) {

        SecureToken secureToken= secureTokenService.createSecureToken();
        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            pwdEmail=email;
            User user = userOptional.get();
            secureToken.setUser(user);
            secureTokenRepository.save(secureToken);
           ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
            emailContext.init(user);
            emailContext.setToken(secureToken.getToken());
            String base="http://localhost:4200";
            emailContext.buildForgotPasswordUrl(base, secureToken.getToken());
            try {
                emailService.sendMail(emailContext);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return true;
        }else{return false;

        }

    }

    @Override
    public boolean setForgotPassword(ForgotPasswordRequest request) {



        var userOptional = userRepository.findByEmail(pwdEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
        return true;

    } return false;
    }

    @Override
    public boolean findByEmail(String email) {
        var user= userRepository.findByEmail(email);
        if(user.isPresent())
        {return true;}
        return false;
    }
    @Override
    public User getByEmail(String email) {
        var user= userRepository.findByEmail(email)  ;

       return  user.get();
    }

    /* update personal infos */
    @Override
    public AuthenticationResponse changePersonalInfos(ChangePersonalInfosdRequest request, Principal connectedUser)
    {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        user.setClassType(request.getClassType());
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authService.revokeAllUserTokens(user);
        authService.saveUserToken(user, jwtToken);


        return AuthenticationResponse.builder()
                // .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();

    }



}
