package com.esprit.cloudcraft.implement;


import com.esprit.cloudcraft.services.IComment;
import com.esprit.cloudcraft.repository.AnnonceDao;
import com.esprit.cloudcraft.repository.CommentDao;
import com.esprit.cloudcraft.entities.Annonce;
import com.esprit.cloudcraft.entities.Comment;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@Transactional
@Service
public class CommentService implements IComment {
    @Autowired
    CommentDao commentRepository;
    @Autowired
    AnnonceDao annonceDao;

    @Autowired
    private BadwordsService badwordsService;

  /*  @Override
    public Comment addComment(CommentDto comment, long annonceId) {
        Comment comment1= new Comment();
        comment1.setComment_description(comment.getDescriptionComment());
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        Annonce existingAnnonce = annonceDao.findById(annonceId).orElseThrow(() -> new IllegalArgumentException("Annonce not found for id: " + annonceId));
        int nb=existingAnnonce.getNbr_comment()+1;
        existingAnnonce.setNbr_comment(nb);
        Comment comment2 = Comment.builder()
                .annonce(existingAnnonce)
                .comment_description(comment1.getComment_description())
                .comment_date(currentDateTime)
                .build();
        existingAnnonce.getComments().add(comment1);
        annonceDao.save(existingAnnonce);
        return comment2;
    }*/





    @Override
    public Comment getCommentById(long id_comment) {
        Comment comment = commentRepository.findById(id_comment).get();
        Comment comment1 = Comment.builder()
                .comment_description(comment.getComment_description())
                .comment_date(comment.getComment_date())

                .build();

        return comment1;
    }

    @Override

    public Comment updateComment(long id, String comment){
        Comment comment1=commentRepository.findById(id).get();

            comment1.setComment_description(comment);
            return commentRepository.save(comment1);

        }



    @Override
    public void deleteComment(long id_comment) {
        Comment comment = commentRepository.findById(id_comment).orElse(null);
        if (comment != null) {
            comment.setAnnonce(null);
            commentRepository.delete(comment);
        }
    }

    @Override
    public List<Comment> getAllComments(long id) {
        {
            Annonce annonces=annonceDao.findById(id).get();
            List<Comment>comments=annonces.getComments();
            return comments;
        }
    }
    @Override
    public List<Comment> getCommentByUser(long id_user) {
        List<Comment> comments = commentRepository.findCommentByUserId(id_user);
        return comments;
    }

    @Override
    public void addComment(long annonceId, String comment, long userId) throws ParseException {
        Annonce annonce=annonceDao.findById(annonceId).get();
        Date currentDateTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = dateFormat.format(currentDateTime);
        Date parsedDate = dateFormat.parse(formattedDate);
        String sanitizedComment = badwordsService.sanitizeText(comment);

        Comment comment2 = Comment.builder()
                .annonce(annonce)
                .comment_description(sanitizedComment) // Use the sanitized comment                .comment_date(parsedDate)
                .build();

        commentRepository.save(comment2);

    }


}
