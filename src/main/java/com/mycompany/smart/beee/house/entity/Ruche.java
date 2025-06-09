package com.mycompany.smart.beee.house.entity;

import java.util.Date;
import java.util.List;

public class Ruche {
    private Long id;
    private String codeIdentification;
    private Date dateMiseEnService;
    private StatutRuche statut;
    private Agent agentSup;
    private PlanningVisite planningVisite;
    private List<Mesure> mesures;
    private SiteApiculture site;

    public Ruche() {}

    public Ruche(Long id, String codeIdentification, Date dateMiseEnService, StatutRuche statut, 
                Agent agentSup, PlanningVisite planningVisite, List<Mesure> mesures, SiteApiculture site) {
        this.id = id;
        this.codeIdentification = codeIdentification;
        this.dateMiseEnService = dateMiseEnService;
        this.statut = statut;
        this.agentSup = agentSup;
        this.planningVisite = planningVisite;
        this.mesures = mesures;
        this.site = site;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodeIdentification() { return codeIdentification; }
    public void setCodeIdentification(String codeIdentification) { this.codeIdentification = codeIdentification; }
    
    public Date getDateMiseEnService() { return dateMiseEnService; }
    public void setDateMiseEnService(Date dateMiseEnService) { this.dateMiseEnService = dateMiseEnService; }
    
    public StatutRuche getStatut() { return statut; }
    public void setStatut(StatutRuche statut) { this.statut = statut; }
    
    public Agent getAgentSup() { return agentSup; }
    public void setAgentSup(Agent agentSup) { this.agentSup = agentSup; }
    
    public PlanningVisite getPlanningVisite() { return planningVisite; }
    public void setPlanningVisite(PlanningVisite planningVisite) { this.planningVisite = planningVisite; }
    
    public List<Mesure> getMesures() { return mesures; }
    public void setMesures(List<Mesure> mesures) { this.mesures = mesures; }
    
    public SiteApiculture getSite() { return site; }
    public void setSite(SiteApiculture site) { this.site = site; }
}
