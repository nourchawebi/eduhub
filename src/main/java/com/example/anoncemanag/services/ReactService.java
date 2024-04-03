package com.example.anoncemanag.services;

import com.example.anoncemanag.entities.React;
import com.example.anoncemanag.interfaces.IReact;
import com.example.anoncemanag.repository.ReactDao;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class ReactService implements IReact {
    @Autowired
    ReactDao reactRepository;

    @Override
    public React addReact(React react) {
        return reactRepository.save(react);
    }
    @Override
    public React getReactById(long id) {
        return reactRepository.findById(id).orElse(null);
    }

    @Override

    public React updateReact(long id, React react){
        if (reactRepository.existsById((long) Math.toIntExact(id))){
           react.setId_react(id);
            return reactRepository.save(react);

        }
        return null;
    }
    @Override
    public void deleteReact(long id) {
        reactRepository.deleteById(id);
    }
    @Override
    public List<React> getAllReact() {
        return reactRepository.findAll();
    }




}
