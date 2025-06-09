package com.mycompany.smart.beee.house.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SiteApiculture {
    private Long id;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Date dateCreation;
    private Date dateCloture;
    private Ferme ferme;
    private List<Ruche> ruches;

    public SiteApiculture() {
        this.ruches = new ArrayList<>();
    }

    public SiteApiculture(Long id, double latitude, double longitude, double altitude, Date dateCreation, Date dateCloture , Ferme ferme) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.dateCreation = dateCreation;
        this.dateCloture = dateCloture;
        this.ferme = ferme;
        this.ruches = new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getAltitude() { return altitude; }
    public void setAltitude(Double altitude) { this.altitude = altitude; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    public Date getDateCloture() { return dateCloture; }
    public void setDateCloture(Date dateCloture) { this.dateCloture = dateCloture; }

    @Override
    public String toString() {
        return "SiteApiculture{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", dateCreation=" + dateCreation +
                ", dateCloture=" + dateCloture +
                '}';
    }

    public Ferme getFerme() {
        return ferme;
    }

    public void setFerme(Ferme ferme) {
        this.ferme = ferme;
    }

    public List<Ruche> getRuches() {
        return ruches;
    }

    public void setRuches(List<Ruche> ruches) {
        this.ruches = ruches;
    }
}
