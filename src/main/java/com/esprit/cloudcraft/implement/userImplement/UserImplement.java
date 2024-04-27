package com.esprit.cloudcraft.implement.userImplement;

import com.esprit.cloudcraft.entities.userEntities.ClassType;
import com.esprit.cloudcraft.filter.JwtService;
import com.esprit.cloudcraft.dto.userdto.*;
import com.esprit.cloudcraft.entities.userEntities.SecureToken;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.userDao.SecureTokenRepository;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.services.userServices.*;
import com.esprit.cloudcraft.tfa.TwoFactorAuthenticationService;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import java.security.Principal;
import java.util.List;
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
    @Resource
    private FileStorageService fileStorageService ;

    @Value("${site.base.url.https}")
    private String baseURL;

 /************************ register user implement *****************/
    @Override
    public AuthenticationResponse register(User user, MultipartFile image)  {

            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
        if (user.getEmail() != null && user.getEmail().contains(".") && user.getEmail().contains("@")) {
            user.setEnable(false);}
        user.setPicture(fileStorageService.saveImage(image));
            if (user.isMfaEnabled())
            {
                user.setSecret(tfaService.generateNewSecret());
            }
            var savedUser = userRepository.save(user);
        if (user.getEmail() != null && user.getEmail().contains(".") && user.getEmail().contains("@")) {
            sendRegistrationConfirmationEmail(user);}
            // generate  a jwt key to authorize the rest of api
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            authService.saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                    .accessToken(jwtToken)
                    .mfaEnabled(user.isMfaEnabled())
                    .refreshToken(refreshToken).build();

    }
/**************** send activation email implement ******************/
    @Override
    public void sendRegistrationConfirmationEmail(User user) {
        // generate an activation email token
        SecureToken secureToken= secureTokenService.createSecureToken();
        // set the token to the registered user
        secureToken.setUser(user);
        // save the token
        secureTokenRepository.save(secureToken);
        // call the email context
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        // intialise the email infos by the user infos
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        // build the verification url using the api generated in the controller
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

/******************* activating the user by this methode ***********************/
/***************** this methode is inside the api that is sent to the user in anb email **********/
    public boolean verifyUser(String token)
    {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired())
        {
            throw new IllegalStateException("Invalid or expired token");
        }

        User user = userRepository.getOne(secureToken.getUser().getId());
        if (Objects.isNull(user))
        {
            return false;
        }
        user.setEnable(true);
        userRepository.save(user); // let’s save user details

        // we don’t need invalid password now
        secureTokenService.removeToken(secureToken);
        return true;
    }


/*********************** resend activation email :usecase( if the user didn't activate his account and the token expired)*******************/
    public void resendToken(String email) throws IllegalArgumentException
    {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent())
        {
            User user = optionalUser.get();
            Optional<SecureToken> fintoken= secureTokenService.findByUser(user);
                if(fintoken.isEmpty())
                { sendRegistrationConfirmationEmail(user);}
                else {
                    throw new IllegalArgumentException("email already sent and token not expired");
                }
        } else {
            // Handle the case when the user is not found in the repository
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
    }


    /********************* these 3 methodes  are dedicated to  change a new email*****************************/

    /***********methode1:send a verification email(we didn't use the same methode wish is for activating the user
     * because this methode will use another verification email wish will set the new user email
     */
    public void sendVerifNewEmail(User user)
    {
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
    /******************* methode2: set the new email and generate a new token with the new email*************/
    /************ this methode is called in api sent with the virification email and not used as a front api *********/
    public AuthenticationResponse verifyNewEmail(String token)  throws  IllegalArgumentException
    {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired())
        {
            throw new IllegalStateException("Invalid or expired token");
        }

        User user = userRepository.findById(secureToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        user.setEnable(true);
        // the newemail is set in the change email methode so he can set that new email
        user.setEnable(true);
        userRepository.save(user); // let’s save user details
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authService.revokeAllUserTokens(user);
        authService.saveUserToken(user, jwtToken);

        secureTokenService.removeToken(secureToken);
        return AuthenticationResponse.builder()
                // .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }
// the var wish will help me set the new email
public String newEmail;
    /*************methode 3: this methode will get the new email from the user  and call the methode wish will send the email**************/
    public String changeEmail(ChangeEmailRequest request, Principal connectedUser)
    {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!request.getCurrentEmail().equals(user.getEmail()))
        {
            throw new IllegalStateException("Wrong email");
        }
        if (!request.getNewEmail().equals(request.getConfirmationEmail()))
        {
            throw new IllegalStateException("Emails are not the same");
        }
         // user.setEnable(false);
        var verifuser = user;
        verifuser.setEmail(request.getNewEmail());
        user.setEmail(request.getNewEmail());
        user.setEnable(false);
       userRepository.save(user);

       Optional< SecureToken> secureToken = secureTokenService.findByUser(user);
        if(secureToken.isEmpty())
        {sendVerifNewEmail(verifuser);// bch nb3th  verif token bl email jdid ama mnich bch nsajlou lin yetverifa bch mb3d nrefreshi ltoken
       // userRepository.save(user);
        newEmail = request.getNewEmail();
        return request.getNewEmail();
        }
        else
        {
            throw new RuntimeException("an email is already sent! u can't send another email now or change ur email again");

        }
    }

    /*********************** change password methode implement *********************/
    public void changePassword(ChangePasswordRequest request) {

        var user = (User)   userRepository.findByEmail(request.getEmail()).orElseThrow();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }
    /********************** these 2 methodes are for users who forgot their passwords*******************/
/********************methode1:get the email from the user and send a forgot password email request ****************/
 public String pwdEmail;
    @Override
    public boolean sendForgotPasswordRequest(String email)
    {
        SecureToken secureToken= secureTokenService.createSecureToken();
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent())
        {
            // setting the email of the use wish  i will change his password
            pwdEmail=email;
            User user = userOptional.get();
            secureToken.setUser(user);
            secureTokenRepository.save(secureToken);
           ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
            emailContext.init(user);
            emailContext.setToken(secureToken.getToken());
            //an url will be sent with the email and this url will have the base url front end and not backend
            String base="http://localhost:4200";
            emailContext.buildForgotPasswordUrl(base, secureToken.getToken());
            try {
                emailService.sendMail(emailContext);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return true;
        }else
        {
            return false;
        }
    }
          /**********************methode2: user set the new password and saves it ***********************/
    @Override
    public boolean setForgotPassword(ForgotPasswordRequest request)
    {
        var userOptional = userRepository.findByEmail(pwdEmail);
        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            if (!request.getNewPassword().equals(request.getConfirmPassword()))
             {
             throw new IllegalStateException("Password are not the same");
             }
             // update the password
             user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            // save the new password
            userRepository.save(user);
            return true;
         }
        return false;
    }
 /********************* find by email methode implement(check if user exists or not ******************************/
    @Override
    public boolean findByEmail(String email)
    {
        var user= userRepository.findByEmail(email);
        if(user.isPresent())
        {
            return true;
        }
        return false;
    }
     /********************* get the user by his email *******************/
    @Override
    public User getByEmail(String email)
    {
        var user= userRepository.findByEmail(email)  ;
       return  user.get();
    }

    /******************* update user personal infos methode implement **********************/
    @Override
    public  AuthenticationResponse changePersonalInfos(ChangePersonalInfosdRequest request, Principal connectedUser)
    {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        ClassType classType = ClassType.valueOf(request.getClassType());
        user.setClassType(classType);
       var saveduser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authService.updateToken(saveduser, jwtToken);
        return AuthenticationResponse.builder()

                .accessToken(jwtToken)

                .refreshToken(refreshToken).build();


    }

}
