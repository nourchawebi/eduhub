package com.esprit.cloudcraft.controller.userController;

import com.esprit.cloudcraft.dto.userdto.AuthenticationRequest;
import com.esprit.cloudcraft.dto.userdto.AuthenticationResponse;
import com.esprit.cloudcraft.dto.userdto.ForgotPasswordRequest;
import com.esprit.cloudcraft.dto.userdto.VerificationRequest;
import com.esprit.cloudcraft.entities.userEntities.ClassType;
import com.esprit.cloudcraft.entities.userEntities.RoleType;
import com.esprit.cloudcraft.entities.userEntities.SecureToken;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.userServices.AuthenticationService;
import com.esprit.cloudcraft.services.userServices.LogoutService;
import com.esprit.cloudcraft.services.userServices.SecureTokenService;
import com.esprit.cloudcraft.services.userServices.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequiredArgsConstructor

public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private final AuthenticationService service;
    @Resource
    private SecureTokenService secureTokenService;
    @Resource
    private LogoutService logoutService;

    @Resource
    private LogoutHandler logoutHandler;
    @PostMapping("/logouts")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        logoutService.logout(request, response, null); // Pass null for authentication as it's not used in logout

    }
    /******************* get all roleType ******************/
    @GetMapping("getroles")
    @ResponseBody
    public List<RoleType> getAllroles()
    {
        return RoleType.getAllRoleTypes();
    }
    /******************* get all classType ******************/


    @GetMapping("/user/me")
    @ResponseBody
    public User getConnectedUser()
    {

        System.out.println( userService.getConnectedUser());
        return userService.getConnectedUser();
    }

    @GetMapping("getclasstypes")
    @ResponseBody
    public List<ClassType> getAllClassTypes()
    {
        return ClassType.getAllClassTypes();
    }
    /******************* register a user ******************/

    @PostMapping(value = "register", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })

    public ResponseEntity<?> register(@RequestParam String firstName,
                                      @RequestParam String  lastName,
                                      @RequestParam String email,
                                      @RequestParam String  password,
                                      @RequestParam String mfaEnabled,

                                      @RequestParam   String birthDate,
                                      @RequestParam String classType,
                                      @RequestParam MultipartFile picture )
    {
        User request=new User();
        request.setLastName(lastName);
        request.setFirstName(firstName);
        request.setEmail(email);
        request.setPassword(password);
        boolean isMfaEnabled = Boolean.parseBoolean(mfaEnabled);
        request.setMfaEnabled(isMfaEnabled);

        Date parsedBirthDate = null;
        try {
            parsedBirthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.setBirthDate(parsedBirthDate);
        ClassType c = ClassType.valueOf(classType);
        request.setClassType(c);

        boolean test=userService.findByEmail(request.getEmail());
        if (userService.findByEmail(request.getEmail()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(test);
        }
        else
        {
            var response=userService.register(request,picture);
            if(request.isMfaEnabled())
            { return ResponseEntity.ok(response);}
            else
            {
                return ResponseEntity.accepted().build();
            }
        }
    }

/**** request email to enable the new acount created ****/
    /******** this api is sent in the mail sent and not used in the front end *******/
    @GetMapping("register/verify")
    public String verifyCustomer(@RequestParam(required = false) String token)
    {
        userService.verifyUser(token);
        return "verifSuccess";
    }



    /************************ login api ***********************/
    @PostMapping("/login")
    public ResponseEntity<?> authenticate (@RequestBody AuthenticationRequest request)
    {
        if (!userService.findByEmail(request.getEmail()))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else
        {
            User user =userService.getByEmail(request.getEmail());
            if(! user.isEnabled())
            {
                Optional<SecureToken> fintoken= secureTokenService.findByUser(user);
                if (fintoken.isEmpty())
                {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User disabled and token expired");
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User disabled");
                }
            }
            else
                return ResponseEntity.ok(service.authenticate(request));
        }
    }

    /********************* resent activation email if the token is expired and user still disabled using the email set in the login page *****************/
    @GetMapping("register/resendToken")

    public ResponseEntity<?>resendtokenToActiveAcount(@RequestParam("email") String email)
    {
        userService.resendToken(email);
        return ResponseEntity.ok().body("{\"message\": \"email sent\"}");

    }
/********* these 2 methodes are for forget password ***************/

    /**** the email used to set the new password for the required user******/

    public String sentmail;
    /**************** methode1:send email request for the forgot password ****************************/
    @GetMapping("login/forgotPassword")

    public ResponseEntity<?>sendForgotPassword(@RequestParam("email") String email)
    {
        if( userService.sendForgotPasswordRequest(email))
        { sentmail=email;

            return ResponseEntity.accepted().build();
        }
        else
            return ResponseEntity.notFound().build();
    }
    /************************ methode2:set the new password tapped by the user **************/
    @PatchMapping("login/setnewpassword")
    public  ResponseEntity<?> setNewPassword( @RequestBody ForgotPasswordRequest request)
    {
        if(userService.setForgotPassword( request))
        {   return ResponseEntity.accepted().build();}
        else
            return ResponseEntity.notFound().build();
    }
    /***************** verify the code tapped by the user in the double authentication while login *************/
    /*********** the code is linked with the qr code scanned while registring *********************/
    @PostMapping("login/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationRequest verificationRequest)
    {
        return ResponseEntity.ok(service.verifyCode(verificationRequest));
    }


    /* @PostMapping("/refresh-token")
     public void refreshToken(
             HttpServletRequest request,//the object where we can get or read the authorization header which will hold the refresh token
             HttpServletResponse response//the object that will help us to re-inject or to send back the response
     ) throws IOException {
         service.refreshToken(request,response);
     }*/

    /*********************** get user status:enabled or not *******************/
    @GetMapping("getenabled")
    @ResponseBody

    public  boolean getEnabled( @RequestParam ("email") String email)
    {     User user = userService.getByEmail(email);
        return  user.isEnable();
    }





}
