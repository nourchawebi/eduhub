package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.AbstractEmailContext;
import com.esprit.cloudcraft.entities.user;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbstractEmailContext {

    private String token;


    @Override
    public  <T>void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        user customer = (user) context; // we pass the customer informati
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
                .path("/user/register/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
