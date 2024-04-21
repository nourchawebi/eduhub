package com.esprit.cloudcraft.implement.userImplement;

import com.esprit.cloudcraft.entities.userEntities.AbstractEmailContext;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


public class AccountVerificationEmailContext extends AbstractEmailContext
{
    private String token;

/************* initializing the user infos email *************************/
    @Override
    public  <T>void init(T context)
    {
        //we can do any common configuration setup here
        // like setting up some base URL and context
        User customer = (User) context; // we pass the customer informati
        put("firstName", customer.getFirstName());
        setTemplateLocation("emailverif");
        setSubject("Complete your registration");
        setFrom("nourelhoudachawebi@gmail.com");
        setTo(customer.getEmail());
    }
    /****************** setting the email token **************/
    public void setToken(String token)
    {
        this.token = token;
        put("token", token);
    }
/************* base url for activating the account created *****************/
    public void buildVerificationUrl(final String baseURL, final String token)
    {
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/register/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
    /********************** base url for verifying the new email set by the user *********************/
    public void buildVerificationUrlNewEmail(final String baseURL, final String token)
    {
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/user/update/email/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }

}
