package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.entity.Mesure;
import com.mycompany.smart.beee.house.entity.TypeMesure;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesureDAO {
    private Connection connection;
    private RucheDAO rucheDAO;

    public MesureDAO(Connection connection, RucheDAO rucheDAO) {
        this.connection = connection;
        this.rucheDAO = rucheDAO;
    }

    public void create(Mesure mesure) throws SQLException {
        String sql = "INSERT INTO mesures (type, valeur, unite, date_mesure, ruche_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, mesure.getType().name());
            statement.setDouble(2, mesure.getValeur());
            statement.setString(3, mesure.getUnite());
            statement.setTimestamp(4, new java.sql.Timestamp(mesure.getDateMesure().getTime()));
            statement.setLong(5, mesure.getRuche().getId());
            statement.executeUpdate();
        }
    }

    public List<Mesure> findByRuche(Long rucheId) throws SQLException {
        List<Mesure> mesures = new ArrayList<>();
        String sql = "SELECT * FROM mesures WHERE ruche_id = ? ORDER BY date_mesure DESC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, rucheId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                mesures.add(new Mesure(
                        resultSet.getLong("id"),
                        TypeMesure.valueOf(resultSet.getString("type")),
                        resultSet.getDouble("valeur"),
                        resultSet.getString("unite"),
                        resultSet.getTimestamp("date_mesure")
                ));
            }
        }
        return mesures;
    }

    public List<Mesure> findPoidsByRuche(Long rucheId) throws SQLException {
        List<Mesure> mesures = new ArrayList<>();
        String sql = "SELECT * FROM mesures WHERE ruche_id = ? AND type = 'POIDS' ORDER BY date_mesure DESC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, rucheId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                mesures.add(new Mesure(
                        resultSet.getLong("id"),
                        TypeMesure.POIDS,
                        resultSet.getDouble("valeur"),
                        resultSet.getString("unite"),
                        resultSet.getTimestamp("date_mesure")
                ));
            }
        }
        return mesures;
    }
}