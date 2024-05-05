package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.AnnonceDao;
import com.esprit.cloudcraft.repository.ReactDao;
import com.esprit.cloudcraft.entities.Annonce;
import com.esprit.cloudcraft.entities.React;
import com.esprit.cloudcraft.Enum.TypeReact;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.services.IReact;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Transactional
@Service
public class ReactService implements IReact {
    @Autowired
    ReactDao reactRepository;
    @Autowired
    AnnonceDao annonceDao;
    @Autowired
    UserRepository userRepository;

    @Override
    public React addReact(React react) {

        return reactRepository.save(react);
    }



    /*@Override
    public React getReactById(long id) {
        return reactRepository.findById(id).orElse(null);
    }*/

   /* @Override

    public React updateReact(long id, React react){
        if (reactRepository.existsById((long) Math.toIntExact(id))){
           react.setId_react(id);
            return reactRepository.save(react);

        }
        return null;
    }*/
   /* @Override
    public void deleteReact(long id_react) {

        reactRepository.deleteById(id_react);
    }*/
   /* @Override
    public List<React> getAllReact() {
        return reactRepository.findAll();
    }*/

    @Override
    public void likePost(long annonceId, long userId) {
        Annonce annonce = annonceDao.findById(annonceId)
                .orElseThrow(() -> new IllegalArgumentException("Annonce not found for id: " + annonceId));
        if(verifyUserReactionLike(userId,annonceId)){
            React react= new React();
            react=reactRepository.findReactLike(annonceId,userId);
            System.out.println(react);
            deleteReact(react.getId_react());
        }else{
            React react = new React();
            react.setAnnonce(annonce);
            annonce.setLikes(annonce.getLikes()+1);
            react.setTypeReact(TypeReact.LIKE);
            react.setLikes(react.getLikes()+1);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + userId));
            react.setUser(user);
            reactRepository.save(react);
        }

    }
    @Override
    public void dislikePost(long annonceId, long userId) {
        Annonce annonce = annonceDao.findById(annonceId)
                .orElseThrow(() -> new IllegalArgumentException("Annonce not found for id: " + annonceId));
        if (verifyUserReactionDislike(userId, annonceId)) {
            React react = new React();
            react = reactRepository.findReactDislike(annonceId, userId);
            System.out.println(react);
            deleteReact(react.getId_react());
        } else {
            React react = new React();
            react.setAnnonce(annonce);
            annonce.setDislikes(annonce.getDislikes() + 1);
            react.setTypeReact(TypeReact.DISLIKE);
            react.setDislikes(react.getDislikes() + 1);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + userId));
            react.setUser(user);
            reactRepository.save(react);
        }
    }

    @Override
    public int nbrLikesParAnnonce(long annonceId) {

        return reactRepository.countLikesByPostId(annonceId);
    }

    @Override
    public int nbrDislikesParAnnonce(long annonceId) {

        return reactRepository.countDislikesByPostId(annonceId);
    }
    @Override
    public boolean verifyUserReactionLike(long id_user, Long id_annonce) {
        return reactRepository.FindReactLikeOfUser(id_user,id_annonce);    }

    @Override
    public boolean verifyUserReactionDislike(long id_user, Long id_annonce) {
        return reactRepository.FindReactDislikeOfUser(id_user,id_annonce);    }

    @Override
    public void deleteReact(long id_react) {
        React react = reactRepository.findById(id_react).orElse(null);
        if (react != null) {
            react.setAnnonce(null);
            reactRepository.delete(react);
        }
    }



}
