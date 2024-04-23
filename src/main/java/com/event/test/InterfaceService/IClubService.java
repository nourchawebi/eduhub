package com.event.test.InterfaceService;


import com.event.test.Entity.Club;

import java.util.List;
import java.util.Optional;

public interface IClubService {
    public List<Club> getallClubs();
    public Club createClub(Club c);
    public Club updateClub(long id, Club updatedClub);
    public void deleteClub(long id);
    public Club findByIdClub(long id);
}
