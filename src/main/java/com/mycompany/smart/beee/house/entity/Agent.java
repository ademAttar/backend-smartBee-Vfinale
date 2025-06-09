package com.mycompany.smart.beee.house.entity;

public class Agent extends User {



    public Agent(Long id, String nom, String prenom, String email, String password) {
        super(id, nom, prenom, email, password, "agent");
    }

}
