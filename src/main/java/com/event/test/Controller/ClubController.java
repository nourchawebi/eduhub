package com.event.test.Controller;

import com.event.test.Entity.Club;
import com.event.test.InterfaceService.IClubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/club")
public class ClubController {
    private final IClubService clubService;

    public ClubController(IClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/getAll")
    public List<Club> getAllClubs() {
        return clubService.getallClubs();
    }

    @PostMapping("/create")
    public Club createClub(@RequestBody Club e) {
        return clubService.createClub(e);
    }

    @PutMapping("/update/{id}")
    public Club updateClub(@PathVariable long id, @RequestBody Club e) {
        return clubService.updateClub(id, e);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteClub(@PathVariable long id) {
        clubService.deleteClub(id);
    }

    @GetMapping("/getClub/{id}")
    public Club findByIdClub(@PathVariable long id) {
        return clubService.findByIdClub(id);
    }
}
