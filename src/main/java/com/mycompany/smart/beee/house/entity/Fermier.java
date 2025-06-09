package com.mycompany.smart.beee.house.entity;

import java.util.ArrayList;

public class Fermier extends User {
    private String contacts;
    private ArrayList<Ruche> ruches;

    public Fermier() {
        super();
        this.ruches = new ArrayList<>();
    }

    public Fermier(Long id, String nom, String prenom, String email, String password, String contacts) {
        super(id, nom, prenom, email, password, "fermier");
        this.contacts = contacts;
        this.ruches = new ArrayList<>();
    }

    public String getContacts() { 
        return contacts; 
    }
    
    public void setContacts(String contacts) { 
        this.contacts = contacts; 
    }

    public ArrayList<Ruche> getRuches() {
        return ruches;
    }

    public void setRuches(ArrayList<Ruche> ruches) {
        this.ruches = ruches;
    }
}
