package com.esprit.cloudcraft.services;


import com.esprit.cloudcraft.entities.Club;

import java.util.List;

public interface IClubService {
    public List<Club> getallClubs();
    public Club createClub(Club c);
    public Club updateClub(long id, Club updatedClub);
    public void deleteClub(long id);
    public Club findByIdClub(long id);
}
