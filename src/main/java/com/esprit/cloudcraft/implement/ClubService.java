package com.esprit.cloudcraft.implement;
import com.esprit.cloudcraft.entities.Club;
import com.esprit.cloudcraft.services.IClubService;
import com.esprit.cloudcraft.repository.ClubRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ClubService implements IClubService {
    private final ClubRepository cc;
    public ClubService(ClubRepository cc) {
        this.cc = cc;
    }    public List<Club> getallClubs(){
        return cc.findAll();
    };
    public Club createClub(Club e){
        return cc.save(e);
    };
    public Club updateClub(long id, Club updatedClub){
        Optional<Club> up = cc.findById(id);
        if (up.isPresent()) {
            Club club = up.get();
            club.setDescription(updatedClub.getDescription());
            club.setName(updatedClub.getName());
            return cc.save(club);
        }
        else {
            return null;
        }
}
    public void deleteClub(long id) {
         cc.deleteById(id);
    }
    @Override
    public Club findByIdClub(long id) {
        return cc.findById(id).get();
    }
}
