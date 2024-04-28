package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Club;
import com.esprit.cloudcraft.services.IClubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
