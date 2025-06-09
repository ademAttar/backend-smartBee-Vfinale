package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.entity.Ferme;
import com.mycompany.smart.beee.house.entity.Fermier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FermeDAO {
    private final Connection connection;
    private final UserDAO userDAO;
    private SiteApicultureDAO siteApicultureDAO;

    public FermeDAO(Connection connection, UserDAO userDAO, SiteApicultureDAO siteApicultureDAO) {
        this.connection = connection;
        this.userDAO = userDAO;
        this.siteApicultureDAO = siteApicultureDAO;
    }

    public List<Ferme> findAll() throws SQLException {
        List<Ferme> fermes = new ArrayList<>();
        String sql = "SELECT * FROM fermes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                fermes.add(mapResultSetToFerme(rs));
            }
        }
        return fermes;
    }

    public Ferme findById(Long id) throws SQLException {
        String sql = "SELECT * FROM fermes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToFerme(rs);
            }
        }
        return null;
    }

    public List<Ferme> findByFermier(Long fermierId) throws SQLException {
        List<Ferme> fermes = new ArrayList<>();
        String sql = "SELECT * FROM fermes WHERE fermier_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, fermierId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fermes.add(mapResultSetToFerme(rs));
            }
        }
        return fermes;
    }

    public void create(Ferme ferme) throws SQLException {
        String sql = "INSERT INTO fermes (nom, superficie, fermier_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ferme.getNom());
            stmt.setDouble(2, ferme.getSuperficie());
            stmt.setLong(3, ferme.getFermier().getId());
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ferme.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public void update(Ferme ferme) throws SQLException {
        String sql = "UPDATE fermes SET nom = ?, superficie = ?, fermier_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ferme.getNom());
            stmt.setDouble(2, ferme.getSuperficie());
            stmt.setLong(3, ferme.getFermier().getId());
            stmt.setLong(4, ferme.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM fermes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Ferme mapResultSetToFerme(ResultSet rs) throws SQLException {
        Ferme ferme = new Ferme();
        ferme.setId(rs.getLong("id"));
        ferme.setNom(rs.getString("nom"));
        ferme.setSuperficie(rs.getDouble("superficie"));
        
        // Set fermier
        FermierDAO fermierDAO = new FermierDAO(connection);
        ferme.setFermier(fermierDAO.findById(rs.getLong("fermier_id")));
        
        // Chargement des sites d'apiculture associés
        ferme.setSitesApiculture(siteApicultureDAO.findByFerme(ferme.getId()));
        
        return ferme;
    }

    public void setSiteApicultureDAO(SiteApicultureDAO siteApicultureDAO) {
        this.siteApicultureDAO = siteApicultureDAO;
    }
}
