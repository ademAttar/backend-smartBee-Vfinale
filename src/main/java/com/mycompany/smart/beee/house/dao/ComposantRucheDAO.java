package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComposantRucheDAO {
    private Connection connection;
    private RucheDAO rucheDAO;

    public ComposantRucheDAO(Connection connection, RucheDAO rucheDAO) {
        this.connection = connection;
        this.rucheDAO = rucheDAO;
    }

    public void create(ComposantRuche composant) throws SQLException {
        String sql = "INSERT INTO composants_ruche (type, numero_etage, ruche_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, composant.getType().name());
            statement.setInt(2, composant.getNumeroEtage());
            statement.setLong(3, composant.getRuche().getId());
            statement.executeUpdate();
        }
    }

    public List<ComposantRuche> findByRuche(Long rucheId) throws SQLException {
        List<ComposantRuche> composants = new ArrayList<>();
        String sql = "SELECT * FROM composants_ruche WHERE ruche_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, rucheId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                composants.add(new ComposantRuche(
                        resultSet.getLong("id"),
                        TypeComposant.valueOf(resultSet.getString("type")),
                        resultSet.getInt("numero_etage")
                ));
            }
        }
        return composants;
    }

    public ComposantRuche findById(long composantId) {
        return null;
    }
}
