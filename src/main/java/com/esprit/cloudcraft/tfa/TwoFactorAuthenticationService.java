package com.esprit.cloudcraft.tfa;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@Service
@Slf4j
public class TwoFactorAuthenticationService {
    /*****************  Generate a new secret key for two-factor authentication***************/
    public String generateNewSecret()
    {
        return new DefaultSecretGenerator().generate();
    }
    /****************  Generate a QR code image URI for scanning during user registration and MFA activation ******************/

    public String generateQrCodeImageUri(String secret)
    {
        QrData data = new QrData.Builder()
                .label("nour 2FA example") // Label for the QR code
                .secret(secret)// Secret key for generating the code
                .issuer("nour")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)// Number of digits in the code
                .period(60)//  60 seconds validity for each code
                .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = new byte[0];
        try {
            imageData = generator.generate(data);// Generate the QR code image
        } catch (QrGenerationException e) {
            e.printStackTrace();
            log.error("Error while generating QR-CODE");
        }
        // Convert the QR code image data to a data URI for embedding in HTML or other formats
        return getDataUriForImage(imageData, generator.getImageMimeType());
    }

    /******************** Check if the OTP(key) entered by the user is valid **************/
    public boolean isOtpValid(String secret, String code)
    {
        // Create a time provider to get the current time
        TimeProvider timeProvider = new SystemTimeProvider();
        // Create a code generator to generate OTP codes
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        // Create a verifier to check if the entered code is valid
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return verifier.isValidCode(secret, code);
    }
/******************** Check if the OTP entered by the user is not valid (inverse of isOtpValid) ***************/
    public boolean isOtpNotValid(String secret, String code)
    {
        return !this.isOtpValid(secret, code);
    }
}
