package com.esprit.cloudcraft.implement.userImplement;

import com.esprit.cloudcraft.entities.userEntities.AbstractEmailContext;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.web.util.UriComponentsBuilder;

public class ForgotPasswordEmailContext extends AbstractEmailContext
{
    private String token;
/************** intialiting the email use infos and template used ... *******************/
    @Override
    public  <T>void init(T context)
    {
        //we can do any common configuration setup here
        // like setting up some base URL and context
        User customer = (User) context; // we pass the customer informati
        put("firstName", customer.getFirstName());
        setTemplateLocation("emailForgotPassword");
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

    /************* base url for setting new password for users who forgot their passwords *****************/
    public void buildForgotPasswordUrl(final String baseURL, final String token)
    {
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("resetpassword").queryParam("token", token).toUriString();
        put("resetPasswordUrl", url);
    }
}
