package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.entity.Agent;
import com.mycompany.smart.beee.house.entity.Ruche;
import com.mycompany.smart.beee.house.entity.StatutRuche;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RucheDAO {
    private Connection connection;
    private UserDAO userDAO;
    private SiteApicultureDAO siteDAO;

    public RucheDAO(Connection connection, UserDAO userDAO, SiteApicultureDAO siteDAO) {
        this.connection = connection;
        this.userDAO = userDAO;
        this.siteDAO = siteDAO;
    }

    public void create(Ruche ruche) throws SQLException {
        String sql = "INSERT INTO ruches (code_identification, date_mise_en_service, statut, agent_sup_id, site_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ruche.getCodeIdentification());
            statement.setDate(2, new java.sql.Date(ruche.getDateMiseEnService().getTime()));
            statement.setString(3, ruche.getStatut().name());
            statement.setLong(4, ruche.getAgentSup().getId());
            statement.setLong(5, ruche.getSite().getId());
            statement.executeUpdate();
        }
    }

    public Ruche findById(Long id) throws SQLException {
        String sql = "SELECT * FROM ruches WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Ruche(
                        resultSet.getLong("id"),
                        resultSet.getString("code_identification"),
                        resultSet.getDate("date_mise_en_service"),
                        StatutRuche.valueOf(resultSet.getString("statut")),
                        (Agent) userDAO.findById(resultSet.getLong("agent_sup_id")),
                        null, // planning visite à implémenter
                        null, // mesure à implémenter
                        siteDAO.findById(resultSet.getLong("site_id"))
                );
            }
            return null;
        }
    }

    public List<Ruche> findBySite(Long siteId) throws SQLException {
        List<Ruche> ruches = new ArrayList<>();
        String sql = "SELECT * FROM ruches WHERE site_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, siteId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ruches.add(new Ruche(
                        resultSet.getLong("id"),
                        resultSet.getString("code_identification"),
                        resultSet.getDate("date_mise_en_service"),
                        StatutRuche.valueOf(resultSet.getString("statut")),
                        (Agent) userDAO.findById(resultSet.getLong("agent_sup_id")),
                        null,
                        null,
                        siteDAO.findById(resultSet.getLong("site_id"))
                ));
            }
        }
        return ruches;
    }

    public List<Ruche> findByAgent(Long agentId) throws SQLException {
        List<Ruche> ruches = new ArrayList<>();
        String sql = "SELECT * FROM ruches WHERE agent_sup_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, agentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ruches.add(new Ruche(
                        resultSet.getLong("id"),
                        resultSet.getString("code_identification"),
                        resultSet.getDate("date_mise_en_service"),
                        StatutRuche.valueOf(resultSet.getString("statut")),
                        (Agent) userDAO.findById(agentId),
                        null,
                        null,
                        siteDAO.findById(resultSet.getLong("site_id"))
                ));
            }
        }
        return ruches;
    }
}
