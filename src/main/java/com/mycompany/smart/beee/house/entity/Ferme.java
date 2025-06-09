package com.mycompany.smart.beee.house.entity;

import java.util.ArrayList;
import java.util.List;

public class Ferme {
    private Long id;
    private String nom;
    private double superficie;
    private Fermier fermier;
    private List<SiteApiculture> sitesApiculture;

    public Ferme() {}

    public Ferme(Long id, String nom, double superficie, Fermier fermier) {
        this.id = id;
        this.nom = nom;
        this.superficie = superficie;
        this.fermier = fermier;
        this.sitesApiculture = new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public double getSuperficie() { return superficie; }
    public void setSuperficie(double superficie) { this.superficie = superficie; }
    
    public Fermier getFermier() { return fermier; }
    public void setFermier(Fermier fermier) { this.fermier = fermier; }
    
    public List<SiteApiculture> getSitesApiculture() { return sitesApiculture; }
    public void setSitesApiculture(List<SiteApiculture> sitesApiculture) { this.sitesApiculture = sitesApiculture; }
}
