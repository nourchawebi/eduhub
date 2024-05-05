package com.esprit.cloudcraft.repository;



import com.esprit.cloudcraft.entities.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactDao extends JpaRepository<React,Long> {
    @Query("SELECT SUM(r.likes) FROM React r WHERE r.annonce.id_annonce = :idAnnonce")

    int countLikesByPostId(@Param("idAnnonce") long idAnnonce);

    @Query("SELECT SUM(r.dislikes) FROM React r WHERE r.annonce.id_annonce = :idAnnonce")
    int countDislikesByPostId(@Param("idAnnonce") long idAnnonce);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM React r WHERE r.user.id = :id_user and r.annonce.id_annonce = :id_annonce  and r.typeReact= 'LIKE'")
    boolean FindReactLikeOfUser(@Param("id_user") long id_user,@Param("id_annonce") long id_annonce);

    @Query("SELECT r FROM React r WHERE r.annonce.id_annonce = :annonceId AND r.user.id = :userId AND r.typeReact = 'LIKE'")
    React findReactLike(@Param("annonceId") long annonceId, @Param("userId") long userId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM React r WHERE r.user.id = :id_user and r.annonce.id_annonce = :id_annonce  and r.typeReact= 'DISLIKE'")
    boolean FindReactDislikeOfUser(@Param("id_user")  long idUser,@Param("id_annonce") Long idAnnonce);
    @Query("SELECT r FROM React r WHERE r.annonce.id_annonce = :annonceId AND r.user.id = :userId AND r.typeReact = 'DISLIKE'")
    React findReactDislike(@Param("annonceId")long annonceId,@Param("userId")  long userId);
}
