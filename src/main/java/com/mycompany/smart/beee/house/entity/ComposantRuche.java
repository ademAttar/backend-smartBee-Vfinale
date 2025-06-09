package com.mycompany.smart.beee.house.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ComposantRuche {
    private Long id;
    private TypeComposant type;
    private int numeroEtage;
    private ArrayList<Cadre> lesCadres;
    private Ruche ruche;

    public ComposantRuche() {}

    public ComposantRuche(Long id, TypeComposant type, int numeroEtage) {
        this.id = id;
        this.type = type;
        this.numeroEtage = numeroEtage;
        lesCadres = new ArrayList<>();
        Cadre c = new Cadre(1L,10, new Date(),TypeCadre.CADRE_VIDE);
        lesCadres.add(c);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TypeComposant getType() { return type; }
    public void setType(TypeComposant type) { this.type = type; }

    public int getNumeroEtage() { return numeroEtage; }
    public void setNumeroEtage(int numeroEtage) { this.numeroEtage = numeroEtage; }

    @Override
    public String toString() {
        return "ComposantRuche{" +
                "id=" + id +
                ", type=" + type +
                ", numeroEtage=" + numeroEtage +
                '}';
    }

    public ArrayList<Cadre> getLesCadres() {
        return lesCadres;
    }

    public void setLesCadres(ArrayList<Cadre> lesCadres) {
        this.lesCadres = lesCadres;
    }

    public Ruche getRuche() {
        return ruche;
    }

    public void setRuche(Ruche ruche) {
        this.ruche = ruche;
    }
}
