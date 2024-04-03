package com.example.anoncemanag.interfaces;



import com.example.anoncemanag.entities.React;

import java.util.List;

public interface IReact {
    React addReact(React react);

    React getReactById(long id);



    React updateReact(long id, React react);

    void deleteReact(long id);

    List<React> getAllReact();
}
