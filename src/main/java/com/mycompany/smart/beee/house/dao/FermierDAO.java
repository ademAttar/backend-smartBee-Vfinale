package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.entity.Fermier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FermierDAO {
    private final Connection connection;

    public FermierDAO(Connection connection) {
        this.connection = connection;
    }

    public Fermier findById(Long id) throws SQLException {
        String sql = "SELECT * FROM fermier WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToFermier(rs);
            }
        }
        return null;
    }

    public List<Fermier> findAll() throws SQLException {
        List<Fermier> fermiers = new ArrayList<>();
        String sql = "SELECT * FROM fermier";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                fermiers.add(mapResultSetToFermier(rs));
            }
        }
        return fermiers;
    }

    public Fermier create(Fermier fermier) throws SQLException {
        String sql = "INSERT INTO fermier (nom, prenom, email, password, contacts) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fermier.getNom());
            stmt.setString(2, fermier.getPrenom());
            stmt.setString(3, fermier.getEmail());
            stmt.setString(4, fermier.getPassword());
            stmt.setString(5, fermier.getContacts());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating fermier failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fermier.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating fermier failed, no ID obtained.");
                }
            }
            return fermier;
        }
    }

    public Fermier update(Fermier fermier) throws SQLException {
        String sql = "UPDATE fermier SET nom = ?, prenom = ?, email = ?, password = ?, contacts = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fermier.getNom());
            stmt.setString(2, fermier.getPrenom());
            stmt.setString(3, fermier.getEmail());
            stmt.setString(4, fermier.getPassword());
            stmt.setString(5, fermier.getContacts());
            stmt.setLong(6, fermier.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating fermier failed, no rows affected.");
            }
            return fermier;
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM fermier WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting fermier failed, no rows affected.");
            }
        }
    }

    private Fermier mapResultSetToFermier(ResultSet rs) throws SQLException {
        Fermier fermier = new Fermier(
            rs.getLong("id"),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("contacts")
        );
        return fermier;
    }
} 