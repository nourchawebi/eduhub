package com.esprit.cloudcraft.controller.userController;

import com.esprit.cloudcraft.dto.userdto.ChangeEmailRequest;
import com.esprit.cloudcraft.dto.userdto.ChangePasswordRequest;
import com.esprit.cloudcraft.dto.userdto.ChangePersonalInfosdRequest;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.userDao.UserRepository;

import com.esprit.cloudcraft.services.FileStorageService;
import com.esprit.cloudcraft.services.userServices.UserService;
import com.google.zxing.NotFoundException;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:4200")
public class simpleConnectedUserController {

    @Resource
    private UserService userService;
    @Resource
    private FileStorageService fileStorageService ;
    @Resource
    private UserRepository userRepository;
    /********************* change the password in the data base ************************/

    @PatchMapping("updatepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request)
    {
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }
    private boolean emailVerified = false;
/*****************  sending a verification email the new email set by the user*********************/
    /****************** the verification mail contains the api wish will update and set the new email in the database *************/
    @PatchMapping("updateEmail")
    public ResponseEntity<?> changeEmail(@RequestBody ChangeEmailRequest request, Principal connectedUser)
    {
        userService.changeEmail(request, connectedUser);
        return ResponseEntity.ok().build();

    }
    /******************************* the api set in the email sent : to update the new email for the user *************/
    @Async
    @GetMapping("update/email/verify")
    @ResponseBody
    public  ResponseEntity<?> verifyCustomer(@RequestParam(required = false) String token)
    {
        emailVerified = true;
        return ResponseEntity.ok(userService.verifyNewEmail(token));

    }

    /******************** change user personal infos(password and email not included ******************************/
    @PatchMapping("updatePersonalData")
    public  ResponseEntity<?> updateUserInfos(@RequestBody ChangePersonalInfosdRequest request, Principal connectedUser)
    {
        return ResponseEntity.ok(userService.changePersonalInfos(request,connectedUser));
    }
    /********************** get user logged image ********************/
    @GetMapping("image")
    public ResponseEntity<byte[]> getImage( Principal connectedUser) throws IOException {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Path imagePath= Paths.get("uploads/images",user.getPicture());
        byte[] imageBytes = Files.readAllBytes(imagePath);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @PostMapping(value="update-image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> updateImage(@RequestParam("image") MultipartFile image, Principal connectedUser)  {
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Image file is required");
        }

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        String oldImageName = user.getPicture();

        // Save the new image with a random name
        String newImageName = fileStorageService.saveImage(image);

        // Update the user's image name in the database
        user.setPicture(newImageName);
        userRepository.save(user);
        // Save the updated user entity

        return ResponseEntity.ok().build();
    }


}
