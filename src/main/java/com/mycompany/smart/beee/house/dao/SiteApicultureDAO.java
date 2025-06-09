package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.entity.SiteApiculture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiteApicultureDAO {
    private Connection connection;
    private FermeDAO fermeDAO;

    public SiteApicultureDAO(Connection connection, FermeDAO fermeDAO) {
        this.connection = connection;
        this.fermeDAO = fermeDAO;
    }

    public void create(SiteApiculture site) throws SQLException {
        String sql = "INSERT INTO sites_apiculture (latitude, longitude, altitude, date_creation, date_cloture, ferme_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, site.getLatitude());
            statement.setDouble(2, site.getLongitude());
            statement.setDouble(3, site.getAltitude());
            statement.setDate(4, new java.sql.Date(site.getDateCreation().getTime()));
            statement.setDate(5, site.getDateCloture() != null ?
                    new java.sql.Date(site.getDateCloture().getTime()) : null);
            statement.setLong(6, site.getFerme().getId());
            statement.executeUpdate();
        }
    }

    public SiteApiculture findById(Long id) throws SQLException {
        String sql = "SELECT * FROM sites_apiculture WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new SiteApiculture(
                        resultSet.getLong("id"),
                        resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"),
                        resultSet.getDouble("altitude"),
                        resultSet.getDate("date_creation"),
                        resultSet.getDate("date_cloture"),
                        fermeDAO.findById(resultSet.getLong("ferme_id"))
                );
            }
            return null;
        }
    }

    public List<SiteApiculture> findByFerme(Long fermeId) throws SQLException {
        List<SiteApiculture> sites = new ArrayList<>();
        String sql = "SELECT * FROM sites_apiculture WHERE ferme_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, fermeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sites.add(new SiteApiculture(
                        resultSet.getLong("id"),
                        resultSet.getDouble("latitude"),
                        resultSet.getDouble("longitude"),
                        resultSet.getDouble("altitude"),
                        resultSet.getDate("date_creation"),
                        resultSet.getDate("date_cloture"),
                        fermeDAO.findById(fermeId)
                ));
            }
        }
        return sites;
    }
}
