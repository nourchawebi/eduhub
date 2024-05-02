package com.example.anoncemanag.interfaces;



import com.example.anoncemanag.entities.React;

import java.util.List;

public interface IReact {
    React addReact(React react);

   // React getReactById(long id);



   // React updateReact(long id, React react);



    //List<React> getAllReact();

    void likePost(long annonceId, long userId);

    void dislikePost(long annonceId, long userId);

    int nbrLikesParAnnonce(long annonceId);

    int nbrDislikesParAnnonce(long annonceId);

    boolean verifyUserReactionLike(long id_user, Long id_annonce);

    boolean verifyUserReactionDislike(long id_user, Long id_annonce);

    void deleteReact(long id_react);
}
