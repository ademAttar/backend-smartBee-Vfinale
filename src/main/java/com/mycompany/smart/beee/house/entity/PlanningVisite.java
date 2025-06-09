package com.mycompany.smart.beee.house.entity;

import java.util.Date;

public class PlanningVisite {
    private Long id;
    private Date dateVisite;
    private String objectif;
    private Ruche ruche;
    private Agent agent;
    public PlanningVisite() {}

    public PlanningVisite(Long id, Date dateVisite, String objectif) {
        this.id = id;
        this.dateVisite = dateVisite;
        this.objectif = objectif;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getDateVisite() { return dateVisite; }
    public void setDateVisite(Date dateVisite) { this.dateVisite = dateVisite; }

    public String getObjectif() { return objectif; }
    public void setObjectif(String objectif) { this.objectif = objectif; }

    @Override
    public String toString() {
        return "PlanningVisite{" +
                "id=" + id +
                ", dateVisite=" + dateVisite +
                ", objectif='" + objectif + '\'' +
                '}';
    }

    public Ruche getRuche() {
        return ruche;
    }

    public void setRuche(Ruche ruche) {
        this.ruche = ruche;
    }

    public Agent getAgent() {
        return this.agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
