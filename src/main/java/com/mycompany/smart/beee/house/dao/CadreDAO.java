package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.entity.Cadre;
import com.mycompany.smart.beee.house.entity.TypeCadre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CadreDAO {
    private Connection connection;
    private ComposantRucheDAO composantDAO;

    public CadreDAO(Connection connection, ComposantRucheDAO composantDAO) {
        this.connection = connection;
        this.composantDAO = composantDAO;
    }

    public void create(Cadre cadre) throws SQLException {
        String sql = "INSERT INTO cadres (position, date_installation, type, composant_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cadre.getPosition());
            statement.setDate(2, new java.sql.Date(cadre.getDateInstallation().getTime()));
            statement.setString(3, cadre.getType().name());
            statement.setLong(4, cadre.getComposant().getId());
            statement.executeUpdate();
        }
    }

    public List<Cadre> findByComposant(Long composantId) throws SQLException {
        List<Cadre> cadres = new ArrayList<>();
        String sql = "SELECT * FROM cadres WHERE composant_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, composantId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cadres.add(new Cadre(
                        resultSet.getLong("id"),
                        resultSet.getInt("position"),
                        resultSet.getDate("date_installation"),
                        TypeCadre.valueOf(resultSet.getString("type"))
                ));
            }
        }
        return cadres;
    }
}
