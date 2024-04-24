package com.example.anoncemanag.controller;

import com.example.anoncemanag.entities.React;
import com.example.anoncemanag.interfaces.IReact;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reacts")
@CrossOrigin("*")
public class ReactController {

    @Autowired
    IReact reactInterface;

    @PostMapping("/addReact")
    public React addReact (@RequestBody React react){

        return reactInterface.addReact(react);
    }

    @PutMapping("/updateReact/{id}")
    public React updateReact(@PathVariable Long id, @RequestBody React react) {
        return reactInterface.updateReact(id,react);
    }

    @DeleteMapping("/deleteReact/{id}")
    public Void deleteReact(@PathVariable long id) {
        this.reactInterface.deleteReact(id);
        return null;
    }

    @GetMapping("/getAllComments")
    public List<React> getAllReact() {

        return reactInterface.getAllReact();

    }
    @GetMapping("/getReactById{id}")
    public React getReactById(@PathVariable Long id) {

        return reactInterface.getReactById(id);
    }

}
