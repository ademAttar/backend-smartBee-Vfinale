package com.mycompany.smart.beee.house.entity;

import java.util.Date;

public class Mesure {
    private Long id;
    private TypeMesure type;
    private Double valeur;
    private String unite;
    private Date dateMesure;
    private Ruche ruche;

    public Mesure() {}

    public Mesure(Long id, TypeMesure type, Double valeur, String unite, Date dateMesure) {
        this.id = id;
        this.type = type;
        this.valeur = valeur;
        this.unite = unite;
        this.dateMesure = dateMesure;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public TypeMesure getType() { return type; }
    public void setType(TypeMesure type) { this.type = type; }
    
    public Double getValeur() { return valeur; }
    public void setValeur(Double valeur) { this.valeur = valeur; }
    
    public String getUnite() { return unite; }
    public void setUnite(String unite) { this.unite = unite; }
    
    public Date getDateMesure() { return dateMesure; }
    public void setDateMesure(Date dateMesure) { this.dateMesure = dateMesure; }
    
    public Ruche getRuche() { return ruche; }
    public void setRuche(Ruche ruche) { this.ruche = ruche; }
}
