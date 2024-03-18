package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.AbstractEmailContext;
import com.esprit.cloudcraft.entities.User;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbstractEmailContext {

    private String token;


    @Override
    public  <T>void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        User customer = (User) context; // we pass the customer informati
        put("firstName", customer.getFirstName());
        setTemplateLocation("emailverif");
        setSubject("Complete your registration");
        setFrom("nourelhoudachawebi@gmail.com");
        setTo(customer.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/register/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
    public void buildVerificationUrlNewEmail(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/user/update/email/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
