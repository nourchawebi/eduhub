package com.esprit.cloudcraft.implement.userImplement;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.entities.userEntities.token.Token;
import com.esprit.cloudcraft.repository.userDao.TokenRepository;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.services.userServices.AdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminImplement implements AdminService{
    @Resource
    private UserRepository userRepository;
    @Resource
    private TokenRepository tokenRepository;
    /********************* lock user methode implement **********************/
    @Override
    public boolean lockUser(String email)
    {
        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            if (user.isAccountNonLocked())
            {
                user.setNotLocker(false);
                userRepository.save(user);
                // Get the last token associated with the user
                Token lastToken = tokenRepository.findTopByUserOrderByCreatedAtDesc(user);

                if (lastToken != null)
                {
                    lastToken.setExpired(true); // Set the last token as expired
                    tokenRepository.save(lastToken);
                }

                return true; // Return true to indicate successful locking
            } else
            {
                throw new RuntimeException("User already locked");
            }
        } else
        {
            throw new RuntimeException("User not found");
        }
    }
/********************** unlock user methode implement *********************/
    @Override
    public boolean unlockUser(String email)
    {
        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            if (!user.isAccountNonLocked())
            {
                user.setNotLocker(true);
                userRepository.save(user);
                return true;
            } else
            {
                throw new RuntimeException("User already not locked");
            }
        } else
        {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
