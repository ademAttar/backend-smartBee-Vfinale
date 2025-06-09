package com.mycompany.smart.beee.house.dao;

import com.mycompany.smart.beee.house.entity.Agent;
import com.mycompany.smart.beee.house.entity.RapportVisite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RapportVisiteDAO {
    private final Connection connection;
    private final PlanningVisiteDAO planningDAO;
    private final UserDAO userDAO;
    private final RucheDAO rucheDAO;

    public RapportVisiteDAO(Connection connection, PlanningVisiteDAO planningDAO,
                            UserDAO userDAO, RucheDAO rucheDAO) {
        this.connection = connection;
        this.planningDAO = planningDAO;
        this.userDAO = userDAO;
        this.rucheDAO = rucheDAO;
    }

    public void create(RapportVisite rapport) throws SQLException {
        String sql = "INSERT INTO rapports_visite (date_visite, contenu, duree, raison, constatations, " +
                "actions_prevues, actions_effectuees, recommandations, evaluation_effectif, " +
                "evaluation_sante, evaluation_productivite, planning_visite_id, agent_id, ruche_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setRapportParameters(statement, rapport);
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rapport.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public RapportVisite findById(Long id) throws SQLException {
        String sql = "SELECT * FROM rapports_visite WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRapportFromResultSet(resultSet);
            }
            return null;
        }
    }

    public List<RapportVisite> findByRuche(Long rucheId) throws SQLException {
        List<RapportVisite> rapports = new ArrayList<>();
        String sql = "SELECT * FROM rapports_visite WHERE ruche_id = ? ORDER BY date_visite DESC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, rucheId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rapports.add(mapRapportFromResultSet(resultSet));
            }
        }
        return rapports;
    }

    public List<RapportVisite> findByAgent(Long agentId) throws SQLException {
        List<RapportVisite> rapports = new ArrayList<>();
        String sql = "SELECT * FROM rapports_visite WHERE agent_id = ? ORDER BY date_visite DESC";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, agentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rapports.add(mapRapportFromResultSet(resultSet));
            }
        }
        return rapports;
    }

    private void setRapportParameters(PreparedStatement statement, RapportVisite rapport) throws SQLException {
        statement.setTimestamp(1, new java.sql.Timestamp(rapport.getDate().getTime()));
        statement.setString(2, rapport.getContenu());
        statement.setInt(3, rapport.getDuree());
        statement.setString(4, rapport.getRaison());
        statement.setString(5, rapport.getConstatations());
        statement.setString(6, rapport.getActionsPrevues());
        statement.setString(7, rapport.getActionsEffectuees());
        statement.setString(8, rapport.getRecommandations());
        statement.setInt(9, rapport.getEvaluationEffectif());
        statement.setInt(10, rapport.getEvaluationSante());
        statement.setInt(11, rapport.getEvaluationProductivite());
        statement.setLong(12, rapport.getPlanningVisite() != null ? rapport.getPlanningVisite().getId() : null);
        statement.setLong(13, rapport.getAgent().getId());
        statement.setLong(14, rapport.getRuche().getId());
    }

    private RapportVisite mapRapportFromResultSet(ResultSet resultSet) throws SQLException {
        RapportVisite rapport = new RapportVisite();
        rapport.setId(resultSet.getLong("id"));
        rapport.setDate(resultSet.getTimestamp("date_visite"));
        rapport.setContenu(resultSet.getString("contenu"));
        rapport.setDuree(resultSet.getInt("duree"));
        rapport.setRaison(resultSet.getString("raison"));
        rapport.setConstatations(resultSet.getString("constatations"));
        rapport.setActionsPrevues(resultSet.getString("actions_prevues"));
        rapport.setActionsEffectuees(resultSet.getString("actions_effectuees"));
        rapport.setRecommandations(resultSet.getString("recommandations"));
        rapport.setEvaluationEffectif(resultSet.getInt("evaluation_effectif"));
        rapport.setEvaluationSante(resultSet.getInt("evaluation_sante"));
        rapport.setEvaluationProductivite(resultSet.getInt("evaluation_productivite"));
        if (resultSet.getLong("planning_visite_id") > 0) {
            rapport.setPlanningVisite(planningDAO.findById(resultSet.getLong("planning_visite_id")));
        }
        rapport.setAgent((Agent) userDAO.findById(resultSet.getLong("agent_id")));
        rapport.setRuche(rucheDAO.findById(resultSet.getLong("ruche_id")));

        return rapport;
    }
}