package com.mycompany.smart.beee.house.entity;

import java.awt.*;
import java.util.Date;

public class Cadre {
    private Long id;
    private int position;
    private Date dateInstallation;
    private TypeCadre type;
    private ComposantRuche composant;

    public Cadre() {}

    public Cadre(Long id, int position, Date dateInstallation, TypeCadre type) {
        this.id = id;
        this.position = position;
        this.dateInstallation = dateInstallation;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public Date getDateInstallation() { return dateInstallation; }
    public void setDateInstallation(Date dateInstallation) { this.dateInstallation = dateInstallation; }

    public TypeCadre getType() { return type; }
    public void setType(TypeCadre type) { this.type = type; }

    @Override
    public String toString() {
        return "Cadre{" +
                "id=" + id +
                ", position=" + position +
                ", dateInstallation=" + dateInstallation +
                ", type=" + type +
                '}';
    }

    public ComposantRuche getComposant() {
        return composant;
    }

    public void setComposant(ComposantRuche composant) {
        this.composant = composant;
    }
}
