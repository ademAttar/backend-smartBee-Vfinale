package com.mycompany.smart.beee.house.entity;

import java.util.Date;

public class RapportVisite {
    private Long id;
    private Date date;
    private String contenu;
    private int duree; // en minutes
    private String raison;
    private String constatations;
    private String actionsPrevues;
    private String actionsEffectuees;
    private String recommandations;
    private int evaluationEffectif; // 1-3
    private int evaluationSante; // 1-3
    private int evaluationProductivite; // 1-3
    private PlanningVisite planningVisite;
    private Agent agent;
    private Ruche ruche;

    public RapportVisite() {}

    public RapportVisite(Long id, Date date, String contenu, int duree, String raison,
                         String constatations, String actionsPrevues, String actionsEffectuees,
                         String recommandations, int evaluationEffectif, int evaluationSante,
                         int evaluationProductivite, PlanningVisite planningVisite,
                         Agent agent, Ruche ruche) {
        this.id = id;
        this.date = date;
        this.contenu = contenu;
        this.duree = duree;
        this.raison = raison;
        this.constatations = constatations;
        this.actionsPrevues = actionsPrevues;
        this.actionsEffectuees = actionsEffectuees;
        this.recommandations = recommandations;
        this.evaluationEffectif = evaluationEffectif;
        this.evaluationSante = evaluationSante;
        this.evaluationProductivite = evaluationProductivite;
        this.planningVisite = planningVisite;
        this.agent = agent;
        this.ruche = ruche;
    }

    // Getters et Setters pour tous les attributs
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }

    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }

    public String getConstatations() { return constatations; }
    public void setConstatations(String constatations) { this.constatations = constatations; }

    public String getActionsPrevues() { return actionsPrevues; }
    public void setActionsPrevues(String actionsPrevues) { this.actionsPrevues = actionsPrevues; }

    public String getActionsEffectuees() { return actionsEffectuees; }
    public void setActionsEffectuees(String actionsEffectuees) { this.actionsEffectuees = actionsEffectuees; }

    public String getRecommandations() { return recommandations; }
    public void setRecommandations(String recommandations) { this.recommandations = recommandations; }

    public int getEvaluationEffectif() { return evaluationEffectif; }
    public void setEvaluationEffectif(int evaluationEffectif) { this.evaluationEffectif = evaluationEffectif; }

    public int getEvaluationSante() { return evaluationSante; }
    public void setEvaluationSante(int evaluationSante) { this.evaluationSante = evaluationSante; }

    public int getEvaluationProductivite() { return evaluationProductivite; }
    public void setEvaluationProductivite(int evaluationProductivite) { this.evaluationProductivite = evaluationProductivite; }

    public PlanningVisite getPlanningVisite() { return planningVisite; }
    public void setPlanningVisite(PlanningVisite planningVisite) { this.planningVisite = planningVisite; }

    public Agent getAgent() { return agent; }
    public void setAgent(Agent agent) { this.agent = agent; }

    public Ruche getRuche() { return ruche; }
    public void setRuche(Ruche ruche) { this.ruche = ruche; }

    @Override
    public String toString() {
        return "RapportVisite{" +
                "id=" + id +
                ", date=" + date +
                ", contenu='" + contenu + '\'' +
                ", duree=" + duree +
                ", raison='" + raison + '\'' +
                '}';
    }
}
