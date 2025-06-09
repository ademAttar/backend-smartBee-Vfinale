package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.entity.PlanningVisite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanningVisiteDAO {
    private Connection connection;
    private RucheDAO rucheDAO;
    private UserDAO userDAO;

    public PlanningVisiteDAO(Connection connection, RucheDAO rucheDAO, UserDAO userDAO) {
        this.connection = connection;
        this.rucheDAO = rucheDAO;
        this.userDAO = userDAO;
    }

    public void create(PlanningVisite planning) throws SQLException {
        String sql = "INSERT INTO planning_visites (date_visite, objectif, ruche_id, agent_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, new java.sql.Timestamp(planning.getDateVisite().getTime()));
            statement.setString(2, planning.getObjectif());
            statement.setLong(3, planning.getRuche().getId());
            statement.setLong(4, planning.getAgent().getId());
            statement.executeUpdate();
        }
    }

    public List<PlanningVisite> findByRuche(Long rucheId) throws SQLException {
        List<PlanningVisite> plannings = new ArrayList<>();
        String sql = "SELECT * FROM planning_visites WHERE ruche_id = ? ORDER BY date_visite";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, rucheId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                plannings.add(new PlanningVisite(
                        resultSet.getLong("id"),
                        resultSet.getTimestamp("date_visite"),
                        resultSet.getString("objectif")
                ));
            }
        }
        return plannings;
    }

    public List<PlanningVisite> findByAgent(Long agentId) throws SQLException {
        List<PlanningVisite> plannings = new ArrayList<>();
        String sql = "SELECT * FROM planning_visites WHERE agent_id = ? ORDER BY date_visite";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, agentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                plannings.add(new PlanningVisite(
                        resultSet.getLong("id"),
                        resultSet.getTimestamp("date_visite"),
                        resultSet.getString("objectif")
                ));
            }
        }
        return plannings;
    }

    public PlanningVisite findById(long planningVisiteId) {
        return null;
    }
}
